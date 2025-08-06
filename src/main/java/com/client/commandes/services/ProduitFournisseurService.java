package com.client.commandes.services;

import com.client.commandes.models.ProduitFournisseur;

import java.util.List;

public interface ProduitFournisseurService {
    List<ProduitFournisseur> getByFournisseurId(Long idFournisseur);
    ProduitFournisseur save(Long idFournisseur, ProduitFournisseur produit);
    ProduitFournisseur update(Long idProduit, ProduitFournisseur produit);
    void delete(Long idProduit);
}
