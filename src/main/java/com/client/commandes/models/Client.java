package com.client.commandes.models;

import jakarta.persistence.*;
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String telephone;

    private double prixAchatTotal = 0.0;


    public Client() {}

    public Client(Long id, String nom, String prenom, String email, String telephone, double prixAchatTotal) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.prixAchatTotal = prixAchatTotal;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public double getPrixAchatTotal() { return prixAchatTotal; }
    public void setPrixAchatTotal(double prixAchatTotal) { this.prixAchatTotal = prixAchatTotal; }
}