package com.client.commandes.repository;

import com.client.commandes.models.Commande;
import com.client.commandes.models.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByClient_Id(Long clientId);

    long countByStatut(StatutCommande statut);
    List<Commande> findByDateCommandeBetween(LocalDate startDate, LocalDate endDate);

}
