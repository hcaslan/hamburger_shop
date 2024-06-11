package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddressSaveRequestDto;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + ADDRESS)
public class AddressController {
    private final AddressService addressService;

    @PostMapping(SAVE)
    public ResponseEntity<String> saveAddress(@RequestBody AddressSaveRequestDto dto){
        addressService.saveAddress(dto);
        return ResponseEntity.ok("Address saved successfully");
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateAddress(@RequestBody AddressUpdateRequestDto dto){
        addressService.updateAddress(dto);
        return ResponseEntity.ok("Address updated successfully");
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<String> deleteAddress(@RequestParam String addressId,@RequestParam String profileId){
        addressService.deleteAddress(addressId,profileId);
        return ResponseEntity.ok("Address deleted successfully");
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Address>> findAll(@RequestParam String profileId){
        return ResponseEntity.ok(addressService.findAll(profileId));
    }
}
