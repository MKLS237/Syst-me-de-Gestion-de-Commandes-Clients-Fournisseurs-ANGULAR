package com.client.commandes.repository;

import com.client.commandes.models.Facture;
import com.client.commandes.models.StatutFacture;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {


    List<Facture> findByCommande_Client_Id(Long clientId);

    long countByStatut(StatutFacture statut);

    @Query("SELECT COALESCE(SUM(f.montantTotal), 0) FROM Facture f WHERE f.statut = :statut")
    double sumMontantByStatut(@Param("statut") StatutFacture statut);

    List<Facture> findByClientId(Long clientId);

    Optional<Facture> findByCommandeId(Long commandeId);
}
