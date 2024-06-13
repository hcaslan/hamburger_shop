package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.entity.Profile;
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

    @PutMapping(ADDBALANCE)
    public ResponseEntity<String> addBalance(@RequestParam String profileId, @RequestParam Double balance){
        profileService.addBalance(balance,profileId);
        return ResponseEntity.ok("Balance updated successfully");
    }

    @GetMapping(GETBALANCE)
    public ResponseEntity<Double> getBalance(@RequestParam String profileId){
        return ResponseEntity.ok(profileService.getBalance(profileId));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Profile>> findAll(){
        return ResponseEntity.ok(profileService.findAll());
    }

}
