package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constant.EAddressType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressUpdateRequestDto {
    private String addressId;
    private String profileId;
    private String addressLine;
    private String city;
    private String state;
    private EAddressType addressType;
}
