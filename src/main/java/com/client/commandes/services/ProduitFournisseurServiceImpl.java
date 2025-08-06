package com.client.commandes.services;

import com.client.commandes.models.Fournisseur;
import com.client.commandes.models.ProduitFournisseur;
import com.client.commandes.repository.FournisseurRepository;
import com.client.commandes.repository.ProduitFournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitFournisseurServiceImpl implements ProduitFournisseurService {

    private final ProduitFournisseurRepository produitRepo;
    private final FournisseurRepository fournisseurRepo;

    public ProduitFournisseurServiceImpl(ProduitFournisseurRepository produitRepo, FournisseurRepository fournisseurRepo) {
        this.produitRepo = produitRepo;
        this.fournisseurRepo = fournisseurRepo;
    }

    @Override
    public List<ProduitFournisseur> getByFournisseurId(Long idFournisseur) {
        return produitRepo.findByFournisseurId(idFournisseur);
    }

    @Override
    public ProduitFournisseur save(Long idFournisseur, ProduitFournisseur produit) {
        Fournisseur fournisseur = fournisseurRepo.findById(idFournisseur)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        produit.setFournisseur(fournisseur);
        return produitRepo.save(produit);
    }

    @Override
    public ProduitFournisseur update(Long idProduit, ProduitFournisseur produit) {
        ProduitFournisseur existant = produitRepo.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        existant.setNomProduit(produit.getNomProduit());
        existant.setPrixUnitaire(produit.getPrixUnitaire());
        existant.setUnite(produit.getUnite());
        return produitRepo.save(existant);
    }

    @Override
    public void delete(Long idProduit) {
        produitRepo.deleteById(idProduit);
    }
}
