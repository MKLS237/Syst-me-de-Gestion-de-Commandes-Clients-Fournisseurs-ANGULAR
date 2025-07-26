package com.client.commandes.dto;

import com.client.commandes.models.Client;
import com.client.commandes.models.Commande;
import com.client.commandes.models.StatutCommande;

import java.time.LocalDate;

public class CommandeMapper {

    public static CommandeDto toDto(Commande commande) {
        CommandeDto dto = new CommandeDto();
        dto.setId(commande.getId());
        dto.setClientId(commande.getClient().getId());
        dto.setDesignation(commande.getDesignation());
        dto.setQuantite(commande.getQuantite());
        dto.setPrixUnitaire(commande.getPrixUnitaire());
        dto.setPrixTotal(commande.getPrixTotal());
        dto.setStatut(commande.getStatut().name());
        dto.setDateCommande(commande.getDateCommande());
        dto.setDateLivraison(commande.getDateLivraison());
        return dto;
    }

    public static Commande toEntity(CommandeDto dto, Client client) {
        Commande entity = new Commande();
        entity.setId(dto.getId());
        entity.setClient(client);
        entity.setDesignation(dto.getDesignation());
        entity.setQuantite(dto.getQuantite());
        entity.setPrixUnitaire(dto.getPrixUnitaire());
        entity.setPrixTotal(dto.getPrixTotal());
        entity.setStatut(StatutCommande.valueOf(dto.getStatut()));
        entity.setDateCommande(dto.getDateCommande() != null ? dto.getDateCommande() : LocalDate.now());
        entity.setDateLivraison(dto.getDateLivraison());
        return entity;
    }
}
