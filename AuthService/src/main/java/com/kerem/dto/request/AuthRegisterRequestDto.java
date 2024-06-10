package com.kerem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthRegisterRequestDto {
    @NotBlank
    @Size(min = 2, max = 50)
    String name;
    @NotBlank
    @Size(min = 2, max = 50)
    String surname;
    @NotBlank
    String phone;
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 6, max = 16)
    String password;
    @NotBlank
    @Size(min = 6, max = 16)
    String rePassword;
}
