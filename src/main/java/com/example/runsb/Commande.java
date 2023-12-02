package com.example.runsb;


import java.util.Date;

public class Commande {
    private int idCommande;
    private int idClient;
    private String dateCommande;
    private String statut;

    // Constructeur
    public Commande(int idCommande, int idClient, String dateCommande, String statut) {
        this.idCommande = idCommande;
        this.idClient = idClient;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    // Getters et setters

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}