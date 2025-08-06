package com.client.commandes.repository;

import com.client.commandes.models.ProduitFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitFournisseurRepository extends JpaRepository<ProduitFournisseur, Long> {
    List<ProduitFournisseur> findByFournisseurId(Long fournisseurId);
}
