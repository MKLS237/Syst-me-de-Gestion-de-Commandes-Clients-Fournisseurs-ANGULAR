package com.client.commandes.services;

import com.client.commandes.dto.CommandeDto;
import com.client.commandes.dto.CommandeMapper;
import com.client.commandes.models.Client;
import com.client.commandes.models.Commande;
import com.client.commandes.models.StatutCommande;
import com.client.commandes.repository.ClientRepository;
import com.client.commandes.repository.CommandeRepository;
import com.client.commandes.repository.FactureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final FactureRepository factureRepository;
 

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository, FactureRepository factureRepository) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.factureRepository = factureRepository;
    }


    public Commande createCommande(Long clientId, CommandeDto dto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));

        Commande commande = CommandeMapper.toEntity(dto, client);
        return commandeRepository.save(commande);
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande updateCommande(Long id, CommandeDto dto) {
        Commande existing = commandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));

        StatutCommande previousStatus = existing.getStatut();

        Commande updated = CommandeMapper.toEntity(dto, client);
        updated.setId(id);
        Commande saved = commandeRepository.save(updated);

        FactureService factureService = null;
        if (!previousStatus.name().contains("LIVREE") && updated.getStatut().name().contains("LIVREE")) {
            // Générer la facture
            factureService.generateFacture(saved.getId());
        } else if (previousStatus.name().contains("LIVREE") && updated.getStatut().equals(StatutCommande.NON_LIVREE)) {
            // Supprimer la facture existante
            factureService.invalidateFactureByCommandeId(saved.getId());
        }

        return saved;
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public Map<String, Object> getCommandeStats(LocalDate startDate, LocalDate endDate) {
        List<Commande> commandes;

        if (startDate != null && endDate != null) {
            commandes = commandeRepository.findByDateCommandeBetween(startDate, endDate);
        } else {
            commandes = commandeRepository.findAll();
        }

        double montantLivrees = commandes.stream()
                .filter(c -> StatutCommande.LIVREE.equals(c.getStatut()))
                .mapToDouble(Commande::getPrixTotal)
                .sum();

        double montantNonLivrees = commandes.stream()
                .filter(c -> !StatutCommande.LIVREE.equals(c.getStatut()))
                .mapToDouble(Commande::getPrixTotal)
                .sum();

        List<Commande> nonLivreesAnciennes = commandes.stream()
                .filter(c -> !StatutCommande.LIVREE.equals(c.getStatut()))
                .filter(c -> c.getDateCommande().isBefore(LocalDate.now().minusDays(3)))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("montantLivrees", montantLivrees);
        response.put("montantNonLivrees", montantNonLivrees);
        response.put("nonLivreesAnciennes", nonLivreesAnciennes);
        return response;
    }

}