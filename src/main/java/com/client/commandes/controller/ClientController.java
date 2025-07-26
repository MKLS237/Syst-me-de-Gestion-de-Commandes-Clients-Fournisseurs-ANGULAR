package com.client.commandes.controller;

import com.client.commandes.models.Client;
import com.client.commandes.models.Commande;
import com.client.commandes.models.Facture;
import com.client.commandes.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Gestion des clients, commandes et factures")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Récupérer tous les clients")
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    @PostMapping
    @Operation(summary = "Créer un client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client created = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un client par ID")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        try {
            Client client = clientService.getClient(id);
            return ResponseEntity.ok(client);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Récupérer la liste des commandes d'un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des commandes récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content)
    })
    @GetMapping("/{id}/commandes")
    public ResponseEntity<List<Commande>> getCommandes(@PathVariable Long id) {
        List<Commande> commandes = clientService.getCommandesByClient(id);
        if (commandes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commandes);
    }

    @Operation(summary = "Récupérer la liste des factures d'un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des factures récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content)
    })
    @GetMapping("/{id}/factures")
    public ResponseEntity<List<Facture>> getFactures(@PathVariable Long id) {
        List<Facture> factures = clientService.getFacturesByClient(id);
        if (factures.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factures);
    }

    @Operation(summary = "Obtenir le prix d'achat total d'un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prix d'achat total récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content)
    })
    @GetMapping("/{id}/prix-achat-total")
    public ResponseEntity<Double> getPrixAchatTotal(@PathVariable Long id) {
        try {
            double total = clientService.getPrixAchatTotal(id);
            return ResponseEntity.ok(total);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Mettre à jour le prix d'achat total d'un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prix d'achat mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content)
    })
    @PostMapping("/{id}/update-prix-achat")
    public ResponseEntity<Void> updatePrixAchat(@PathVariable Long id) {
        try {
            clientService.updatePrixAchatTotal(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Statistiques globales du système")
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreClients", clientService.getNombreClients());
        stats.put("nombreCommandes", clientService.getNombreCommandes());
        stats.put("nombreFactures", clientService.getNombreFactures());
        stats.put("montantFacturesNonPayees", clientService.getMontantFacturesNonPayees());
        stats.put("nombreFacturesPayees", clientService.getNombreFactures());
        stats.put("nombreCommandesLivrees", clientService.getNombreCommandesLivrees());
        stats.put("nombreCommandesNonLivrees", clientService.getNombreCommandesNonLivrees());
        return ResponseEntity.ok(stats);
    }
}