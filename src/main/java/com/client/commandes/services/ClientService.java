package com.client.commandes.services;

import com.client.commandes.models.*;
import com.client.commandes.repository.ClientRepository;
import com.client.commandes.repository.CommandeRepository;
import com.client.commandes.repository.FactureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final CommandeRepository commandeRepository;
    private final FactureRepository factureRepository;

    public ClientService(ClientRepository clientRepository, CommandeRepository commandeRepository, FactureRepository factureRepository) {
        this.clientRepository = clientRepository;
        this.commandeRepository = commandeRepository;
        this.factureRepository = factureRepository;
    }

    // CRUD Client

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());
        client.setEmail(clientDetails.getEmail());
        client.setTelephone(clientDetails.getTelephone());
        // prixAchatTotal mis à jour à part
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client non trouvé");
        }
        clientRepository.deleteById(id);
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public List<Commande> getCommandesByClient(Long clientId) {
        return commandeRepository.findByClient_Id(clientId);
    }

    public List<Facture> getFacturesByClient(Long clientId) {
        return factureRepository.findByCommande_Client_Id(clientId);
    }

    public double getPrixAchatTotal(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        return client.getPrixAchatTotal();
    }

    public void updatePrixAchatTotal(Long clientId) {
        Client client = getClient(clientId);
        double total = commandeRepository.findByClient_Id(clientId).stream()
                .mapToDouble(l -> l.getPrixUnitaire() * l.getQuantite())
                .sum();
        client.setPrixAchatTotal(total);
        clientRepository.save(client);
    }

    // Comptage total des clients
    public long getNombreClients() {
        return clientRepository.count();
    }

    // Comptage total des commandes
    public long getNombreCommandes() {
        return commandeRepository.count();
    }


    // Statistiques globales

    public double getMontantFacturesNonPayees() {
        return factureRepository.sumMontantByStatut(StatutFacture.NON_PAYEE);
    }
    // Comptage total des factures
    public long getNombreFactures() {
        return factureRepository.count();
    }

    public long getNombreCommandesLivrees() {
        return commandeRepository.countByStatut(StatutCommande.LIVREE);
    }

    public long getNombreCommandesNonLivrees() {
        return commandeRepository.countByStatut(StatutCommande.NON_LIVREE);
    }
}
