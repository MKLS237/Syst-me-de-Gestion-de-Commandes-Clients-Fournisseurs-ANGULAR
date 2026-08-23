package com.client.commandes.dto;

import com.client.commandes.models.Client;
import com.client.commandes.models.Commande;
import com.client.commandes.models.Facture;

public class FactureMapper {

    public static FactureDto toDto(Facture facture) {
        FactureDto dto = new FactureDto();
        dto.setId(facture.getId());
        dto.setDateFacture(facture.getDateFacture());
        dto.setMontantTotal(facture.getMontantTotal());
        dto.setStatut(facture.getStatut() != null ? facture.getStatut().name() : null);

        Commande commande = facture.getCommande();
        if (commande != null) {
            dto.setCommande(CommandeMapper.toDto(commande));
        }

        Client client = facture.getClient();
        if (client == null && commande != null) {
            client = commande.getClient();
        }
        dto.setClient(toClientDto(client));

        return dto;
    }

    private static Client toClientDto(Client client) {
        if (client == null) {
            return null;
        }

        Client dto = new Client();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setEmail(client.getEmail());
        dto.setTelephone(client.getTelephone());
        dto.setPrixAchatTotal(client.getPrixAchatTotal());
        return dto;
    }
}
