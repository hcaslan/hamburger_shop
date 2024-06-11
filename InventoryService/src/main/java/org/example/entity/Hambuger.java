package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "hamburgers")
public class Hambuger {
    @MongoId
    private String id;
    private String name;
    private List<Ingredient> ingredients;
    @Builder.Default
    private boolean isActive = true;
}