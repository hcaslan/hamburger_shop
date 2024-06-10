package com.kerem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "token")
public class Token {
    @MongoId
    String id;
    String code;
    String authId;
    LocalDateTime createdAt;
    LocalDateTime expiredAt;
    LocalDateTime usedAt;

}
