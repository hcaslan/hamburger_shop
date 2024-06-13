package com.kerem.service;

import com.kerem.constant.EStatus;
import com.kerem.dto.request.AuthRegisterRequestDto;
import com.kerem.dto.response.AuthResponseDto;
import com.kerem.entity.Auth;
import com.kerem.entity.Token;
import com.kerem.exceptions.AuthMicroServiceException;
import com.kerem.exceptions.ErrorType;
import com.kerem.mapper.AuthMapper;
import com.kerem.model.EmailModel;
import com.kerem.model.StatusUpdateModel;
import com.kerem.repository.AuthRepository;
import com.kerem.repository.TokenRepository;
import com.kerem.utility.CodeGenerator;
import com.kerem.utility.JwtTokenManager;
import com.kerem.utility.MailUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenManager jwtTokenManager;
    private final MailUtility mailUtility;
    private final AuthMapper authMapper;
    private final RabbitTemplate rabbitTemplate;

    public Auth findById(String id) {
        return authRepository.findById(id).orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
    }

    @Transactional
    public void register(AuthRegisterRequestDto dto) {
        Auth auth = AuthMapper.INSTANCE.dtoToEntity(dto);
        if (!dto.getPassword().equals(dto.getRePassword()))
            throw new AuthMicroServiceException(ErrorType.PASSWORDS_ARE_NOT_SAME);
        if (authRepository.findByEmail(dto.getEmail()).isPresent())
            throw new AuthMicroServiceException(ErrorType.EMAIL_TAKEN);

        authRepository.save(auth);

        Token token = generateToken(auth);

        EmailModel emailModel = EmailModel.builder()
                .toEmail(dto.getEmail())
                .subject("Activation Code")
                .text(mailUtility.activationEmail(dto.getName(), token.getCode()))
                .build();

        sendEmail(emailModel);
        rabbitTemplate.convertAndSend("exchange.direct","createProfile.Route",auth.getId());

        System.out.println("Activation code: " + token.getCode());
    }

    @Transactional
    public void activateAccount(String activationCode) {
        Token token = tokenRepository.findByCode(activationCode).orElseThrow(() -> new AuthMicroServiceException(ErrorType.INVALID_ACTIVATION_CODE));
        if (token.getUsedAt() != null)
            throw new AuthMicroServiceException(ErrorType.TOKEN_ALREADY_USED);

        token.setUsedAt(LocalDateTime.now());
        tokenRepository.save(token);

        Auth auth = authRepository.findById(token.getAuthId()).orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
        auth.setStatus(EStatus.ACTIVE);

        StatusUpdateModel statusUpdateModel = StatusUpdateModel.builder().authId(auth.getId()).status(EStatus.ACTIVE).build();

        rabbitTemplate.convertAndSend("exchange.direct","updateStatus.Route",statusUpdateModel);
        authRepository.save(auth);
    }

    public String login(String email, String password) {
        Auth auth = (Auth) loadUserByUsername(email);
        if (!auth.getPassword().equals(password))
            throw new AuthMicroServiceException(ErrorType.WRONG_PASSWORD);
        if (auth.getStatus() != EStatus.ACTIVE)
            throw new AuthMicroServiceException(ErrorType.USER_NOT_ACTIVE);

        return jwtTokenManager.createTokenWithIdAndRole(auth.getId(), auth.getRole()).orElseThrow(() -> new AuthMicroServiceException(ErrorType.TOKEN_CREATION_FAILED));
    }

    public void getResetPasswordCode(String email){
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));

        Token token = generateToken(auth);
        EmailModel emailModel = EmailModel.builder()
                .toEmail(email)
                .subject("Reset Password Code")
                .text(mailUtility.passwordChangeEmail(auth.getName(), token.getCode()))
                .build();

        sendEmail(emailModel);

        System.out.println("Reset password code: " + token.getCode());
    }

    public void resetPassword(String activationCode, String newPassword, String rePassword){
        Token token = tokenRepository.findByCode(activationCode)
                .orElseThrow(() -> new AuthMicroServiceException(ErrorType.INVALID_ACTIVATION_CODE));

        if (token.getUsedAt() != null)
            throw new AuthMicroServiceException(ErrorType.TOKEN_ALREADY_USED);
        if (!newPassword.equals(rePassword))
            throw new AuthMicroServiceException(ErrorType.PASSWORDS_ARE_NOT_SAME);

        String authId = token.getAuthId();

        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
        auth.setPassword(newPassword);
        authRepository.save(auth);
    }

    public void changePassword(String email, String oldPassword, String newPassword, String rePassword) {
        authRepository.findByEmail(email)
                .ifPresentOrElse(auth -> {
                    if (!auth.getPassword().equals(oldPassword))
                        throw new AuthMicroServiceException(ErrorType.WRONG_PASSWORD);
                    if (!newPassword.equals(rePassword))
                        throw new AuthMicroServiceException(ErrorType.PASSWORDS_ARE_NOT_SAME);
                    auth.setPassword(newPassword);
                    authRepository.save(auth);
                }, () -> {
                    throw new AuthMicroServiceException(ErrorType.USER_NOT_FOUND);
                });
    }

    @Transactional
    public String softDelete(String id) {
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
        return delete(auth);
    }

    private String delete(Auth auth) {
        if (!auth.getStatus().equals(EStatus.DELETED)) {
            auth.setStatus(EStatus.DELETED);
            authRepository.save(auth);
            try {
                StatusUpdateModel statusUpdateModel = StatusUpdateModel.builder().authId(auth.getId()).status(EStatus.DELETED).build();

                rabbitTemplate.convertAndSend("exchange.direct","updateStatus.Route",statusUpdateModel);
            } catch (Exception e){
                throw new AuthMicroServiceException(ErrorType.BAD_REQUEST);
            }
            return "User with id " + auth.getId() + " has been deleted";
        } else {
            throw new AuthMicroServiceException(ErrorType.USER_ALREADY_DELETED);
        }
    }

    @Transactional
    public void softDeleteMyAccount(String email, String password) {
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
        if (!auth.getPassword().equals(password))
            throw new AuthMicroServiceException(ErrorType.WRONG_PASSWORD);
        delete(auth);
    }

    public List<AuthResponseDto> findAllDto() {
        return authMapper.entityListToDtoList(authRepository.findAll());
    }

    public AuthResponseDto findByIdDto(String id) {
        Auth auth = authRepository.findById(id).orElseThrow(() -> new AuthMicroServiceException(ErrorType.ID_NOT_FOUND));
        return AuthMapper.INSTANCE.entityToResponseDto(auth);
    }

    private Token generateToken(Auth auth) {
        String code = CodeGenerator.generateCode();
        Token token = new Token();
        token.setAuthId(auth.getId());
        token.setCode(code);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(token);
        return token;
    }


    public void sendEmail(EmailModel emailModel) {
        rabbitTemplate.convertAndSend("getMail.Queue", emailModel);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authRepository.findByEmail(email).orElseThrow(() -> new AuthMicroServiceException(ErrorType.USER_NOT_FOUND));
    }

}

