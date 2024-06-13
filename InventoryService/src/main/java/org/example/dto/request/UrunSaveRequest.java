package org.example.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.UrunOzellikler;
import org.example.entity.UrunSecenekler;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UrunSaveRequest {
    @Schema(description = "Name of the product", example = "Product Name")
    private String ad;

    @Schema(description = "Type of the product", example = "Product Type")
    private String tur;

    @Schema(description = "Price of the product", example = "99.99")
    private Double fiyat;

    @Schema(description = "Features of the product", example = "{\"ingredients\":[{\"ad\":\"Beef\"},{\"ad\":\"Lettuce\"},{\"ad\":\"Tomato\"}],\"removableIngredients\":[{\"ad\":\"Beef\"},{\"ad\":\"Lettuce\"},{\"ad\":\"Tomato\"}]}")
    private Map<String, List<UrunOzellikler>> ozellikler;

    @Schema(description = "Options for the product", example = "[{\"ad\":\"BBQ Sauce\",\"ekstraFiyat\":5.0},{\"ad\":\"Ranch Sauce\",\"ekstraFiyat\":5.0}]")
    private List<UrunSecenekler> secenekler;
}
