package com.client.commandes.services;

import com.client.commandes.dto.CommandeDto;
import com.client.commandes.dto.CommandeJourStatsDTO;
import com.client.commandes.dto.CommandeMapper;
import com.client.commandes.models.Client;
import com.client.commandes.models.Commande;
import com.client.commandes.models.StatutCommande;
import com.client.commandes.repository.ClientRepository;
import com.client.commandes.repository.CommandeRepository;
import com.client.commandes.repository.FactureRepository;
import com.client.commandes.repository.ProduitFournisseurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final FactureRepository factureRepository;
    private final ProduitFournisseurRepository produitFournisseurRepository;
    private final FactureService factureService;
 

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository,ProduitFournisseurRepository produitFournisseurRepository, FactureRepository factureRepository, FactureService factureService ) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.factureRepository = factureRepository;
        this.factureService = factureService;
        this.produitFournisseurRepository = produitFournisseurRepository;
    }


    public Commande createCommande(CommandeDto dto) {
        Long clientId = dto.getClient().getId();
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

        Long clientId = dto.getClient().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));

        StatutCommande previousStatus = existing.getStatut();

        Commande updated = CommandeMapper.toEntity(dto, client);
        updated.setId(id);
        Commande saved = commandeRepository.save(updated);


        if (!previousStatus.name().contains("LIVREE") && updated.getStatut().name().contains("LIVREE")) {
            // Générer la facture
            this.factureService.generateFacture(saved.getId());
        } else if (previousStatus.name().contains("LIVREE") && updated.getStatut().equals(StatutCommande.NON_LIVREE)) {
            // Supprimer la facture existante
            this.factureService.invalidateFactureByCommandeId(saved.getId());
        }

        return saved;
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public Map<String, Object> getCommandeStats(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now().minusDays(7);
            endDate = LocalDate.now();
        }
        List<Commande> commandes = commandeRepository.findByDateCommandeBetween(startDate, endDate);
        // Grouper par date
        Map<LocalDate, List<Commande>> groupByDate = commandes.stream()
                .collect(Collectors.groupingBy(Commande::getDateCommande));

        List<CommandeJourStatsDTO> statsParJour = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Commande>> entry : groupByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Commande> commandesDuJour = entry.getValue();

            double totalVentes = commandesDuJour.stream()
                    .mapToDouble(Commande::getPrixTotal)
                    .sum();


            double totalCout = commandesDuJour.stream()
                    .mapToDouble(c -> {
                        return produitFournisseurRepository.findByNomProduitIgnoreCase(c.getDesignation())
                                .map(pf -> pf.getPrixUnitaire() * c.getQuantite())
                                .orElse(0.0);
                    })
                    .sum();

            double benefice = totalVentes - totalCout;

            statsParJour.add(new CommandeJourStatsDTO(date, totalVentes, totalCout,benefice));
        }

        statsParJour.sort(Comparator.comparing(CommandeJourStatsDTO::getDateCommande));


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
        response.put("statsParJour", statsParJour);
        response.put("totalVentes", statsParJour.stream().mapToDouble(CommandeJourStatsDTO::getTotalVentes).sum());
        response.put("totalCout", statsParJour.stream().mapToDouble(CommandeJourStatsDTO::getTotalCout).sum());
        response.put("beneficeTotal", statsParJour.stream().mapToDouble(CommandeJourStatsDTO::getBenefice).sum());

        return response;
    }

}