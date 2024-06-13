package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.constant.Session;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.entity.Profile;
import org.example.service.AddressService;
import org.example.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT + PROFILE)
public class ProfileController {
    private final ProfileService profileService;

    @PutMapping(ADDBALANCE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<String> addBalance(@RequestParam Double balance){
        profileService.addBalance(balance,Session.getProfileId());
        return ResponseEntity.ok("Balance updated successfully");
    }

    @GetMapping(GETBALANCE)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<Double> getBalance(){
        return ResponseEntity.ok(profileService.getBalance(Session.getProfileId()));
    }

    @GetMapping(FINDALL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearerAuth") })
    public ResponseEntity<List<Profile>> findAll(){
        return ResponseEntity.ok(profileService.findAll());
    }

}
