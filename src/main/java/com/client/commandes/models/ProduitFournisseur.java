package com.client.commandes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fournisseur")
@Data
@Getter
@Setter
@Builder
public class ProduitFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomProduit;
    private Double prixUnitaire;
    private String unite; // kg, litre, sac...

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    @JsonBackReference
    private Fournisseur fournisseur;

    public ProduitFournisseur() {
    }

    public ProduitFournisseur(Long id, String nomProduit, Double prixUnitaire, String unite) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.prixUnitaire = prixUnitaire;
        this.unite = unite;
    }

    public ProduitFournisseur(Long id, String nomProduit, Double prixUnitaire, String unite, Fournisseur fournisseur) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.prixUnitaire = prixUnitaire;
        this.unite = unite;
        this.fournisseur = fournisseur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
}

