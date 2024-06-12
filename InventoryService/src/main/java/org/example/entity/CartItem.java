package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItem {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String productId;
    private String ad;
    private Double fiyat;
    private List<String> selectedOptions;
    private List<String> removedIngredients;
}
