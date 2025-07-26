package com.client.commandes.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "facture")
@Data
@Getter
@Setter
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private LocalDate dateFacture;

    private double montantTotal;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut = StatutFacture.NON_PAYEE;

    // ✅ Lien vers la commande
    @OneToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    // ✅ Lien vers le client (facultatif mais utile)
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Facture() {
    }

    public Facture(Long id, LocalDate dateFacture, double montantTotal, StatutFacture statut, Commande commande, Client client) {
        this.id = id;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.commande = commande;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
