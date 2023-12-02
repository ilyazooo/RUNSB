package com.example.runsb;

public class Moderateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private  String motDePasse;
    private boolean ajouterProduit;
    private boolean supprimerProduit;
    private boolean modifierProduit;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isAjouterProduit() {
        return ajouterProduit;
    }

    public boolean isModifierProduit() {
        return modifierProduit;
    }

    public boolean isSupprimerProduit() {
        return supprimerProduit;
    }

    public void setAjouterProduit(boolean ajouterProduit) {
        this.ajouterProduit = ajouterProduit;
    }

    public void setModifierProduit(boolean modifierProduit) {
        this.modifierProduit = modifierProduit;
    }

    public void setSupprimerProduit(boolean supprimerProduit) {
        this.supprimerProduit = supprimerProduit;
    }
}
