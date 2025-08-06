package com.client.commandes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fournisseur")
@Data
@Getter
@Setter
@Builder
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String entreprise;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProduitFournisseur> produitsFournis = new ArrayList<>();

    public Fournisseur() {
    }

    public Fournisseur(Long id, String nom, String adresse, String telephone, String email, String entreprise, List<ProduitFournisseur> produitsFournis) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.entreprise = entreprise;
        this.produitsFournis = produitsFournis;
    }

    public Fournisseur(Long id, String nom, String adresse, String telephone, String email, String entreprise) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.entreprise = entreprise;
    }

    public List<ProduitFournisseur> getProduitsFournis() {
        return produitsFournis;
    }

    public void setProduitsFournis(List<ProduitFournisseur> produitsFournis) {
        this.produitsFournis = produitsFournis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
}
