package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.UrunSaveRequest;
import org.example.entity.Urun;
import org.example.service.UrunService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + URUN)
@RequiredArgsConstructor
public class UrunController {
    private final UrunService urunService;

    @PostMapping(SAVE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Urun createUrun(@RequestBody UrunSaveRequest request) {
        Urun urun = Urun.builder()
                .ad(request.getAd())
                .tur(request.getTur())
                .fiyat(request.getFiyat())
                .ozellikler(request.getOzellikler())
                .secenekler(request.getSecenekler())
                .build();
        return urunService.saveUrun(urun);
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Urun updateUrun(@RequestBody UrunSaveRequest request, @RequestParam String urunId) {
        Urun urun = Urun.builder()
                .id(urunId)
                .ad(request.getAd())
                .tur(request.getTur())
                .fiyat(request.getFiyat())
                .ozellikler(request.getOzellikler())
                .secenekler(request.getSecenekler())
                .build();
        return urunService.update(urun);
    }



    @GetMapping(FINDALL)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public List<Urun> getAllUrunler() {
        return urunService.getAllUrunler();
    }

    @GetMapping(FINDBYID)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public Urun getUrunById(@RequestParam String id) {
        return urunService.getUrunById(id);
    }

    @DeleteMapping(DELETE+"/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteUrun(@PathVariable String id) {
        urunService.deleteUrun(id);
    }
}

