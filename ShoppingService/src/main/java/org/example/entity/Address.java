package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.constant.EAddressType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "addresses")
public class Address {
    @MongoId
    private String id;
    private String profileId;
    private String addressLine;
    private String city;
    private String state;
    private EAddressType addressType;
}
