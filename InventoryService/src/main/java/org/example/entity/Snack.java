package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.constant.EIngredientType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "snacks")
public class Snack {
    @MongoId
    private String id;
    private String name;
    @Builder.Default
    private boolean isActive = true;
}