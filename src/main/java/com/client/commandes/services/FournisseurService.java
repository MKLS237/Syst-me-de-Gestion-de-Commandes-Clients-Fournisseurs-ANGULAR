package com.client.commandes.services;

import com.client.commandes.models.Fournisseur;

import java.util.List;

public interface FournisseurService {
    Fournisseur save(Fournisseur fournisseur);
    List<Fournisseur> getAll();
    Fournisseur getById(Long id);
    Fournisseur update(Long id, Fournisseur fournisseur);
    void delete(Long id);
}
