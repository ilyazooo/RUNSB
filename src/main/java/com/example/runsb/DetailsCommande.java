package com.example.runsb;

public class DetailsCommande {
    private int idDetail;
    private int idCommande;
    private int idProduit;
    private int idVariante;
    private int quantite;

    // Constructeur
    public DetailsCommande(int idDetail, int idCommande, int idProduit, int idVariante, int quantite) {
        this.idDetail = idDetail;
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.idVariante = idVariante;
        this.quantite = quantite;
    }

    // Getters et Setters
    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(int idVariante) {
        this.idVariante = idVariante;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}