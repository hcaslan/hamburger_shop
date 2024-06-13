package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.constant.EStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {
    @MongoId
    private String id;
    private String authId;
    @Builder.Default
    private double balance = 0.0;
    @Builder.Default
    private List<String> addressIds = new ArrayList<>();
    @Builder.Default
    EStatus status = EStatus.PENDING;
}
