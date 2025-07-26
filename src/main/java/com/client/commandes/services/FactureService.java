package com.client.commandes.services;

import com.client.commandes.models.Commande;
import com.client.commandes.models.Facture;
import com.client.commandes.models.StatutFacture;
import com.client.commandes.repository.CommandeRepository;
import com.client.commandes.repository.FactureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class    FactureService {

    private final FactureRepository factureRepository;
    private final CommandeRepository commandeRepository;

    public FactureService(FactureRepository factureRepository, CommandeRepository commandeRepository) {
        this.factureRepository = factureRepository;
        this.commandeRepository = commandeRepository;
    }

    // ✅ Générer une facture pour une commande
    public Facture generateFacture(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée"));

        Facture facture = new Facture();
        facture.setCommande(commande);
        facture.setClient(commande.getClient());
        facture.setDateFacture(LocalDate.now());
        facture.setMontantTotal(commande.getPrixTotal());
        facture.setStatut(StatutFacture.NON_PAYEE);

        return factureRepository.save(facture);
    }

    // ✅ Lister toutes les factures
    public List<Facture> getAll() {
        return factureRepository.findAll();
    }

    // ✅ Récupérer une facture par ID
    public Optional<Facture> findById(Long id) {
        return factureRepository.findById(id);
    }

    // ✅ Lister les factures d'un client
    public List<Facture> findByClientId(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }

    // ✅ Mettre à jour le statut d’une facture
    public Facture updateStatut(Long id, StatutFacture statut) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Facture non trouvée"));

        facture.setStatut(statut);
        return factureRepository.save(facture);
    }

    // ✅ Supprimer une facture
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }
    public Map<String, Object> getGlobalStats() {
        List<Facture> factures = factureRepository.findAll();

        long total = factures.size();
        double totalPayees = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalNonPayees = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.NON_PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalPartielles = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.PARTIELLEMENT_PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalFactures = factures.stream()
                .mapToDouble(Facture::getMontantTotal)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreTotal", total);
        stats.put("montantTotalPayees", totalPayees);
        stats.put("montantTotalNonPayees", totalNonPayees);
        stats.put("montantTotalPartiellementPayees", totalPartielles);
        stats.put("montantTotalFactures", totalFactures);

        return stats;
    }

    public void invalidateFactureByCommandeId(Long commandeId) {
        Optional<Facture> facture = factureRepository.findByCommandeId(commandeId);
        facture.ifPresent(factureRepository::delete);
    }

    public Map<String, Object> getStatsByClient(Long clientId) {
        List<Facture> factures = factureRepository.findByClientId(clientId);

        long total = factures.size();
        double totalPayees = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalNonPayees = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.NON_PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalPartielles = factures.stream()
                .filter(f -> f.getStatut() == StatutFacture.PARTIELLEMENT_PAYEE)
                .mapToDouble(Facture::getMontantTotal)
                .sum();
        double totalFactures = factures.stream()
                .mapToDouble(Facture::getMontantTotal)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("nombreTotal", total);
        stats.put("montantTotalPayees", totalPayees);
        stats.put("montantTotalNonPayees", totalNonPayees);
        stats.put("montantTotalPartiellementPayees", totalPartielles);
        stats.put("montantTotalFactures", totalFactures);

        return stats;
    }
}
