package com.example.runsb;


public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int soldeFidelite;

    public Client() {
    }

    // Getters et Setters pour chaque attribut
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSoldeFidelite() {
        return soldeFidelite;
    }

    public void setSoldeFidelite(int soldeFidelite) {
        this.soldeFidelite = soldeFidelite;
    }
}