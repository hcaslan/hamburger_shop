package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "urunler")
public class Urun {
    @MongoId
    private String id;
    private String ad;
    private String tur;
    private Double fiyat;
    private Map<String, Object> ozellikler; // dinamik
    private List<UrunSecenekler> secenekler;
}