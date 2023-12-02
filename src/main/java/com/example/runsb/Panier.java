package com.example.runsb;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private Map<Integer, Integer> items = new HashMap<>();

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void ajouterVariante(int idVariante) {
        // Si l'idVariante est déjà dans le panier, augmentez la quantité de 1
        if (items.containsKey(idVariante)) {
            int quantiteActuelle = items.get(idVariante);
            items.put(idVariante, quantiteActuelle + 1);
        } else {
            // Si l'idVariante n'est pas encore dans le panier, ajoutez-le avec une quantité de 1
            items.put(idVariante, 1);
        }
    }

    public void retirerVariante(int idVariante) {
        items.remove(idVariante);
    }


    public Map<Integer, Integer> getContenu() {
        return items;
    }

    public int getTailleDuPanier() {
        return items.size();
    }
}