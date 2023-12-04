package com.example.runsb;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {

    @GetMapping("/")
    public String accueil(@RequestParam(name = "id", required = false) String productId, Model model, HttpSession session) {
        DataBaseController controller = new DataBaseController();

        boolean is_Connected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            is_Connected = true;
        }
        model.addAttribute("is_Connected", is_Connected);

        if (productId != null) {
            Produit produit = controller.getProductById(Integer.parseInt(productId));
            model.addAttribute("produit", produit);
        } else {
            List<Produit> catalogue = controller.getCatalogue();
            model.addAttribute("catalogue", catalogue);
        }

        return "accueil";
    }
    @GetMapping("/catalogue")
    public String catalogue(
            @RequestParam(name = "marque", required = false) String marque,
            @RequestParam(name = "couleur", required = false) String couleur,
            @RequestParam(name = "genre", required = false) String genre,
            Model model,
            HttpSession session
    ) {

        boolean is_Connected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            is_Connected = true;
        }
        model.addAttribute("is_Connected", is_Connected);

        DataBaseController controller = new DataBaseController();
        List<Produit> catalogue = controller.getFilteredProducts(marque, couleur, genre);

        model.addAttribute("catalogue", catalogue);

        return "catalogue";
    }

    @GetMapping("/produit")
    public String produit(@RequestParam(name = "id") String productId, Model model, HttpSession session) {

        boolean is_Connected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            is_Connected = true;
        }
        model.addAttribute("is_Connected", is_Connected);

        cartDependencies(model, session);

        if (productId != null) {
            DataBaseController controller = new DataBaseController();
            Produit produit = controller.getProductById(Integer.parseInt(productId));
            List<VarianteProduit> varianteProduit = controller.getVariantesByProductId(Integer.parseInt(productId));
            model.addAttribute("produit", produit);
            model.addAttribute("varianteProduit", varianteProduit);
        }

        return "produit";
    }


    @PostMapping("/addToCart/{idVariante}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@PathVariable("idVariante") int idVariante, HttpSession session) {
        DataBaseController controller = new DataBaseController();

        Panier panier = (Panier) session.getAttribute("panier");

        if (panier == null) {
            panier = new Panier();
            session.setAttribute("panier", panier);
        }

        panier.ajouterVariante(idVariante);
    }


    @PostMapping("/removeFromCart/{idVariante}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(@PathVariable("idVariante") int idVariante, HttpSession session) {
        Panier panier = (Panier) session.getAttribute("panier");

        if (panier == null) {

            panier = new Panier();
            session.setAttribute("panier", panier);
        }


        panier.retirerVariante(idVariante);
    }


    void cartDependencies(Model model, HttpSession session){
        Panier panier1 = (Panier) session.getAttribute("panier");
        DataBaseController controller2 = new DataBaseController();

        if (panier1 == null) {
            panier1 = new Panier();
            session.setAttribute("panier", panier1);
        }

        List<PanierItem> panierItems = new ArrayList<>();

        int total = 0;

        for (Map.Entry<Integer, Integer> entry : panier1.getContenu().entrySet()) {
            int idVariante = entry.getKey();
            int quantite = entry.getValue();

            int idProduit = controller2.getProductIdFromVarianteId(idVariante);
            int pointure = controller2.getPointureFromVarianteId(idVariante);
            String urlPicture = controller2.getImageUrlFromProductId(idProduit);

            Produit produit2 = controller2.getProductById(idProduit);

            if (produit2 != null) {
                int prix = quantite * Integer.parseInt(produit2.getPrix());
                total += prix;

                // Create a PanierItem and add it to the list
                PanierItem panierItem = new PanierItem(idVariante, urlPicture, produit2.getNom(), pointure, quantite, prix);
                panierItems.add(panierItem);
            }
        }

        model.addAttribute("panierItems", panierItems);
        model.addAttribute("total", total);
        model.addAttribute("panier", panier1);
    }




}
