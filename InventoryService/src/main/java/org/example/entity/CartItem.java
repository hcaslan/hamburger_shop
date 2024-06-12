package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItem {
    private String productId;
    private String ad;
    private Double fiyat;
    private List<String> selectedOptions;
    private List<String> removedIngredients;
}
