package com.example.runsb;

public class DetailsCommandeProfil {

    private String urlPicture;
    private int pointure;
    private int prix;
    private int idCommande;
    private String statut;
    private int quantite;
    private String nom;

    // Constructeur
    public DetailsCommandeProfil(String urlPicture, int pointure, int prix, int idCommande, String statut, int quantite, String nom) {
        this.urlPicture = urlPicture;
        this.pointure = pointure;
        this.prix = prix;
        this.idCommande = idCommande;
        this.statut = statut;
        this.quantite = quantite;
        this.nom = nom;
    }

    // Getters et Setters
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public int getPointure() {
        return pointure;
    }

    public void setPointure(int pointure) {
        this.pointure = pointure;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
