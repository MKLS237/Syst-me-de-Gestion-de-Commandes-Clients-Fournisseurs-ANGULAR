package com.client.commandes.controller;

import com.client.commandes.models.ProduitFournisseur;
import com.client.commandes.services.ProduitFournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/produit")

public class ProduitFournisseurController {

    private final ProduitFournisseurService service;

    public ProduitFournisseurController(ProduitFournisseurService service) {
        this.service = service;
    }

    @GetMapping("/fournisseur/{id}")
    public ResponseEntity<List<ProduitFournisseur>> getByFournisseur(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByFournisseurId(id));
    }

    @PostMapping("/fournisseur/{id}")
    public ResponseEntity<ProduitFournisseur> create(@PathVariable Long id, @RequestBody ProduitFournisseur produit) {
        return ResponseEntity.ok(service.save(id, produit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitFournisseur> update(@PathVariable Long id, @RequestBody ProduitFournisseur produit) {
        return ResponseEntity.ok(service.update(id, produit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

