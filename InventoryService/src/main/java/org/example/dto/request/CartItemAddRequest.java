package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItemAddRequest {
    private String urunId;
    private List<String> extraOptions;
    private List<String> selectedOptions;
    private List<String> removedIngredients;
}
