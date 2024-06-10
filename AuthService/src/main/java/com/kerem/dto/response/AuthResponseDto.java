package com.kerem.dto.response;

import com.kerem.constant.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponseDto {
    String id;
    String name;
    String surname;
    String phone;
    String email;
    EStatus status;
}
