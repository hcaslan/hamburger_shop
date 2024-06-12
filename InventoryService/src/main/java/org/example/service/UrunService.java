package org.example.service;

import org.example.entity.Urun;
import org.example.repository.UrunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrunService {

    @Autowired
    private UrunRepository urunRepository;

    public Urun saveUrun(Urun urun) {
        return urunRepository.save(urun);
    }

    public List<Urun> getAllUrunler() {
        return urunRepository.findAll();
    }

    public Urun getUrunById(String id) {
        return urunRepository.findById(id).orElse(null);
    }

    public void deleteUrun(String id) {
        urunRepository.deleteById(id);
    }
}

