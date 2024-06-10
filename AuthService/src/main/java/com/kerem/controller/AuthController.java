package com.kerem.controller;

import com.kerem.dto.request.AuthRegisterRequestDto;
import com.kerem.dto.response.AuthResponseDto;
import com.kerem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kerem.constant.EndPoints.*;

@RestController
@RequestMapping(AUTH)
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

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(authService.login(email, password));
    }

    @GetMapping(GETRESETPASWORDCODE)
    public ResponseEntity<String> getResetPasswordCode(@RequestParam String email){
        authService.getResetPasswordCode(email);
        return ResponseEntity.ok().body("Reset password code sent to your email");
    }

    @PutMapping(RESETPASSWORD)
    public ResponseEntity<String> resetPassword(@RequestParam String newPassword, @RequestParam String resetPasswordCode, @RequestParam String rePassword){
        authService.resetPassword(newPassword, rePassword, resetPasswordCode);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PutMapping(CHANGEPASSWORD)
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String rePassword){
        authService.changePassword(email, oldPassword, newPassword, rePassword);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
        authService.softDelete(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @DeleteMapping(DELETEMYACCOUNT)
    public ResponseEntity<String> deleteMyAccount(@PathVariable String email, @RequestParam String password){
        authService.softDeleteMyAccount(email, password);
        return ResponseEntity.ok("Your account has been deleted");
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<AuthResponseDto>> findAll(){
        return ResponseEntity.ok(authService.findAllDto());
    }

    @GetMapping(FINDBYID + "/{id}")
    public ResponseEntity<AuthResponseDto> findById(@PathVariable String id){
        return ResponseEntity.ok(authService.findByIdDto(id));
    }

}
