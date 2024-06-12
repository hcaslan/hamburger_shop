package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    @MongoId
    private String id;
    private String userId;
    private List<CartItem> items = new ArrayList<>();
    @Builder.Default
    private double totalPrice =0;
}

