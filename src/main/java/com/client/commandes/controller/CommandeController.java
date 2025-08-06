package com.client.commandes.controller;

import com.client.commandes.dto.CommandeDto;
import com.client.commandes.dto.CommandeMapper;
import com.client.commandes.models.Commande;
import com.client.commandes.services.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Commandes", description = "Gestion des commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping
    @Operation(summary = "Créer une commande pour un client")
    public ResponseEntity<CommandeDto> createCommande(@RequestBody CommandeDto dto) {
        Commande created = commandeService.createCommande(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommandeMapper.toDto(created));
    }


    @GetMapping
    public List<CommandeDto> getAll() {
        return commandeService.findAll().stream()
                .map(CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeDto> getCommande(@PathVariable Long id) {
        return commandeService.findById(id)
                .map(c -> ResponseEntity.ok(CommandeMapper.toDto(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCommandeStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, Object> stats = commandeService.getCommandeStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeDto> updateCommande(@PathVariable Long id, @RequestBody CommandeDto dto) {
        try {
            Commande updated = commandeService.updateCommande(id, dto);
            return ResponseEntity.ok(CommandeMapper.toDto(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }


}