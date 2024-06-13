package com.kerem.controller;

import com.kerem.dto.request.AuthRegisterRequestDto;
import com.kerem.dto.response.AuthResponseDto;
import com.kerem.exceptions.AuthMicroServiceException;
import com.kerem.exceptions.ErrorType;
import com.kerem.service.AuthService;
import com.kerem.utility.JwtTokenManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kerem.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT+AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<String> register(@Valid @RequestBody AuthRegisterRequestDto dto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        authService.register(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(ACTIVATEACCOUNT)
    public ResponseEntity<String> activateAccount(@RequestParam String activationCode){
        authService.activateAccount(activationCode);
        return ResponseEntity.ok("Account activated successfully");
    }

    @GetMapping(LOGIN)
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(authService.login(email, password));
    }

    @GetMapping(GETRESETPASWORDCODE)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> getResetPasswordCode(@RequestParam String email){
        authService.getResetPasswordCode(email);
        return ResponseEntity.ok().body("Reset password code sent to your email");
    }

    @PutMapping(RESETPASSWORD)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> resetPassword(@RequestParam String resetPasswordCode, @RequestParam String newPassword, @RequestParam String rePassword){
        authService.resetPassword(resetPasswordCode,newPassword, rePassword);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PutMapping(CHANGEPASSWORD)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String rePassword){
        authService.changePassword(email, oldPassword, newPassword, rePassword);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping(DELETE + "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
        authService.softDelete(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @DeleteMapping(DELETEMYACCOUNT)
    @PreAuthorize("hasRole('USER')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteMyAccount(@RequestParam String email, @RequestParam String password){
        authService.softDeleteMyAccount(email, password);
        return ResponseEntity.ok("Your account has been deleted");
    }

    @GetMapping(FINDALL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<AuthResponseDto>> findAll(){
        return ResponseEntity.ok(authService.findAllDto());
    }

    @GetMapping(FINDBYID + "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AuthResponseDto> findById(@PathVariable String id){
        return ResponseEntity.ok(authService.findByIdDto(id));
    }

}
