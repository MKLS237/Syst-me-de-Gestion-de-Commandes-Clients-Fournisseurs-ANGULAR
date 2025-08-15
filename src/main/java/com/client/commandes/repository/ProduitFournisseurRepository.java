package com.client.commandes.repository;

import com.client.commandes.models.ProduitFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitFournisseurRepository extends JpaRepository<ProduitFournisseur, Long> {
    List<ProduitFournisseur> findByFournisseurId(Long fournisseurId);

    Optional<ProduitFournisseur> findByNomProduitIgnoreCase(String designation);
}
