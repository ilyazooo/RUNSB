package com.example.runsb;

public class VarianteProduit {

    private int id;

    private int idProduit;
    private int pointure;
    private int stock;

    public VarianteProduit(int id, int idProduit, int pointure, int stock) {
        this.id = id ;
        this.idProduit = idProduit;
        this.pointure = pointure;
        this.stock = stock;
    }

    public int getId(){
        return id;
    }

    public int getIdProduit(){
        return idProduit;
    }
    public int getPointure(){
        return pointure;
    }

    public int getStock(){
        return stock;
    }

    public void setId (int id){
        this.id= id;
    }

    public void setIdProduit(int idProduit){
        this.idProduit = idProduit;
    }
    public void setPointure(int pointure){
        this.pointure = pointure;
    }

    public void setStock(int stock){
        this.stock = stock;
    }
}
