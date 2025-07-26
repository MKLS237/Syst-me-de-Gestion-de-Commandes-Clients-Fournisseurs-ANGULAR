package com.client.commandes.controller;

import com.client.commandes.models.Facture;
import com.client.commandes.models.StatutFacture;
import com.client.commandes.services.FactureService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "http://localhost:4200")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    // ✅ Générer une facture
    @PostMapping("/generate/{commandeId}")
    public ResponseEntity<Facture> generateFacture(@PathVariable Long commandeId) {
        try {
            Facture facture = factureService.generateFacture(commandeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(facture);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Obtenir toutes les factures
    @GetMapping
    public ResponseEntity<List<Facture>> getAll() {
        return ResponseEntity.ok(factureService.getAll());
    }

    // ✅ Obtenir une facture spécifique
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFacture(@PathVariable Long id) {
        return factureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Modifier le statut de la facture
    @PutMapping("/{id}/statut")
    public ResponseEntity<Facture> updateStatut(@PathVariable Long id, @RequestBody StatutFacture statut) {
        try {
            Facture updated = factureService.updateStatut(id, statut);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Supprimer une facture
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Lister toutes les factures d’un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Facture>> getFacturesByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(factureService.findByClientId(clientId));
    }
    @Operation(summary = "Statistiques globales sur les factures")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getGlobalStats() {
        return ResponseEntity.ok(factureService.getGlobalStats());
    }

    @Operation(summary = "Statistiques sur les factures d’un client")
    @GetMapping("/stats/client/{clientId}")
    public ResponseEntity<Map<String, Object>> getStatsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(factureService.getStatsByClient(clientId));
    }
}