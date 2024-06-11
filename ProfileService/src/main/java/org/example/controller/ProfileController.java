package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.service.AddressService;
import org.example.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + PROFILE)
public class ProfileController {
    private final ProfileService profileService;

    @PutMapping("/addbalance")
    public ResponseEntity<String> updateProfile(@RequestParam String profileId, @RequestParam Double balance){
        profileService.updateBalance(balance,profileId);
        return ResponseEntity.ok("Balance updated successfully");
    }
}
