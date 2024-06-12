package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "receipt")
public class Receipt {
    @MongoId
    private String id;
    private String userId;
    private String cartId;
    private String date;
    private List<CartItem> items = new ArrayList<>();
    private double totalPrice;
}
