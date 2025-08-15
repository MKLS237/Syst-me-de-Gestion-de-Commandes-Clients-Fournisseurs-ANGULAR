package com.client.commandes.dto;

import java.time.LocalDate;

public class CommandeJourStatsDTO {
    private LocalDate dateCommande;
    private double totalVentes;
    private double totalCout;
    private double benefice;

    public CommandeJourStatsDTO(LocalDate dateCommande, double totalVentes, double totalCout, double benefice) {
        this.dateCommande = dateCommande;
        this.totalVentes = totalVentes;
        this.totalCout = totalCout;
        this.benefice = benefice;
    }

    public CommandeJourStatsDTO(LocalDate dateCommande, double totalVentes, double totalCout) {
        this.dateCommande = dateCommande;
        this.totalVentes = totalVentes;
        this.totalCout = totalCout;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getTotalVentes() {
        return totalVentes;
    }

    public void setTotalVentes(double totalVentes) {
        this.totalVentes = totalVentes;
    }

    public double getTotalCout() {
        return totalCout;
    }

    public void setTotalCout(double totalCout) {
        this.totalCout = totalCout;
    }

    public double getBenefice() {
        return benefice;
    }

    public void setBenefice(double benefice) {
        this.benefice = benefice;
    }
}
