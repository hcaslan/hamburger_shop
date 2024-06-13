package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.constant.Session;
import org.example.dto.request.AddressSaveRequestDto;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + ADDRESS)
public class AddressController {
    private final AddressService addressService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<String> saveAddress(@RequestBody AddressSaveRequestDto dto){
        addressService.saveAddress(dto, Session.getProfileId());
        return ResponseEntity.ok("Address saved successfully");
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<String> updateAddress(@RequestBody AddressUpdateRequestDto dto){
        addressService.updateAddress(dto, Session.getProfileId());
        return ResponseEntity.ok("Address updated successfully");
    }
    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<String> deleteAddress(@RequestParam String addressId){
        addressService.deleteAddress(addressId, Session.getProfileId());
        return ResponseEntity.ok("Address deleted successfully");
    }

    @GetMapping(FINDALL)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<List<Address>> findAllByProfileId(){
        return ResponseEntity.ok(addressService.findAllById(Session.getProfileId()));
    }
}
