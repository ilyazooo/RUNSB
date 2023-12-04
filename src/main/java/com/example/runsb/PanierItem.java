package com.example.runsb;

public class PanierItem {
    private int idVariante;
    private String urlPicture;
    private String nom;
    private int pointure;
    private int quantite;
    private int prix;

    public PanierItem(int idVariante, String urlPicture, String nom, int pointure, int quantite, int prix) {
        this.idVariante = idVariante;
        this.urlPicture = urlPicture;
        this.nom = nom;
        this.pointure = pointure;
        this.quantite = quantite;
        this.prix = prix;
    }

    // Add getters and setters as needed

    public int getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(int idVariante) {
        this.idVariante = idVariante;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPointure() {
        return pointure;
    }

    public void setPointure(int pointure) {
        this.pointure = pointure;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}