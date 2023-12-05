package com.example.runsb;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {

    @GetMapping("/")
    public String accueil(@RequestParam(name = "id", required = false) String productId, Model model, HttpSession session) {
        DataBaseController controller = new DataBaseController();

        boolean isConnected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
        }
        model.addAttribute("isConnected", isConnected);

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

        cartDependencies(model, session);

        boolean isConnected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
        }
        model.addAttribute("isConnected", isConnected);

        DataBaseController controller = new DataBaseController();
        List<Produit> catalogue = controller.getFilteredProducts(marque, couleur, genre);

        model.addAttribute("catalogue", catalogue);

        return "catalogue";
    }

    @GetMapping("/produit")
    public String produit(@RequestParam(name = "id") String productId, Model model, HttpSession session) {

        boolean isConnected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
        }
        model.addAttribute("isConnected", isConnected);

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

    @GetMapping("/register")
    public String register(){


        return "register";
    }

    @GetMapping("/login")
    public String login(){


        return "login";
    }

    @GetMapping("/panier")
    public String afficherPanier(Model model, HttpSession session) {

        DataBaseController databaseController = new DataBaseController();


        cartDependencies(model, session);

        Panier panier = (Panier) session.getAttribute("panier");
        boolean isEmpty = panier == null || panier.getTailleDuPanier() == 0;


        Integer userID = (Integer) session.getAttribute("userID");
        boolean isConnected = userID != null && userID != -1;


        Client client = null;
        if (isConnected) {
            client = databaseController.getClientById(userID);
        }



        model.addAttribute("panier", panier);
        model.addAttribute("isEmpty", isEmpty);
        model.addAttribute("isConnected", isConnected);
        model.addAttribute("client", client);


        return "panier";
    }


    @PostMapping("/loginForm")
    public String loginForm(
            @RequestParam String email,
            @RequestParam String motDePasse,
            Model model,
            HttpSession session
    ) throws SQLException {
        DataBaseController databaseController = new DataBaseController();
            if (databaseController.checkLogin(email, motDePasse)) {

                int userID = databaseController.getUserIdFromDatabase(email);

                if (userID != -1) {
                    session.setAttribute("userID", userID);
                    model.addAttribute("catalogue", databaseController.getCatalogue());

                    model.addAttribute("userID", userID);
                    model.addAttribute("catalogue", databaseController.getCatalogue());


                    return "redirect:/catalogue";
                } else {
                    model.addAttribute("erreur", "L'authentification a échoué.");
                    return "login";
                }
            } else {

                model.addAttribute("erreur", "L'authentification a échoué.");

                return "login";
            }

    }

    @PostMapping("/registerForm")
    public String register(
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String motDePasse,
            @RequestParam String motDePasseConfirmation,
            Model model
    ) {
        DataBaseController databaseController = new DataBaseController();
        
        if (motDePasse.equals(motDePasseConfirmation)) {

            boolean enregistrementReussi = databaseController.register(prenom, nom, email, motDePasse);

            if (enregistrementReussi) {

                return "redirect:/login";
            } else {

                model.addAttribute("erreur", "L'enregistrement a échoué.");

                return "register";
            }
        } else {
            // Les mots de passe ne correspondent pas, renvoyez un message d'erreur
            model.addAttribute("erreur", "Les mots de passe ne correspondent pas.");
            // Restez sur la page d'inscription avec le message d'erreur
            return "register";
        }
    }

    @PostMapping("/viderPanier")
    @ResponseStatus(HttpStatus.OK)
    public void viderPanier(HttpSession session) {
        session.removeAttribute("panier");
    }

    @PostMapping("/creerCommande")
    @ResponseStatus(HttpStatus.OK)
    public String creerCommande(HttpSession session) {
        Integer idClient = (Integer) session.getAttribute("userID");

        DataBaseController databaseController = new DataBaseController();

        creerRecapCommande(session);

        Panier panier = (Panier) session.getAttribute("panier");



        if (panier != null && panier.getTailleDuPanier() != 0) {
            int idCommande = databaseController.creerCommande(idClient);

            for (Map.Entry<Integer, Integer> entry : panier.getContenu().entrySet()) {
                int idVariante = entry.getKey();
                int quantite = entry.getValue();

                int idProduit = databaseController.getProductIdFromVarianteId(idVariante);

                databaseController.creerDetailCommande(idCommande, idProduit, idVariante, quantite);
            }
        }

        session.removeAttribute("panier");

        return "redirect:/confirmationCommande";
    }

    void creerRecapCommande(HttpSession session){
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


                PanierItem panierItem = new PanierItem(idVariante, urlPicture, produit2.getNom(), pointure, quantite, prix);
                panierItems.add(panierItem);
            }
        }


        session.setAttribute("recapCommande", panierItems);
        session.setAttribute("totalCommande", total);
    }


    @GetMapping("/confirmationCommande")
    public String confirmationCommande(HttpSession session, Model model){

        List<PanierItem> panierItems = (List<PanierItem>) session.getAttribute("recapCommande");
        int totalCommande = (Integer) session.getAttribute("totalCommande");




        model.addAttribute("panierItems", panierItems);
        model.addAttribute("totalCommande", totalCommande);


        return "confirmationCommande";
    }


    @GetMapping("/profil")
    public String afficherProfil(Model model, HttpSession session) {

        DataBaseController dataBaseController = new DataBaseController();
        boolean isConnected = false;
        Client client = new Client();
        List<Commande> commandes = new ArrayList<>();
        List<DetailsCommandeProfil> detailsCommandeProfil = new ArrayList<>();


        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
            client = dataBaseController.getClientById(userID);
        }

        if (isConnected) {
            commandes = dataBaseController.getCommandesByClientId(userID);
        }

        for(Commande commande : commandes){

            int idCommande = commande.getIdCommande();
            List<DetailsCommande> detailsCommandes;
            detailsCommandes = dataBaseController.getDetailsCommandeByCommandeId(idCommande);

            for(DetailsCommande detailsCommande : detailsCommandes){

                Produit produit = dataBaseController.getProductById(detailsCommande.getIdProduit());
                int price = Integer.parseInt(produit.getPrix()) * detailsCommande.getQuantite();
                int pointure = dataBaseController.getPointureFromVarianteId(detailsCommande.getIdVariante());
                String statut = commande.getStatut();

                DetailsCommandeProfil dCommande = new DetailsCommandeProfil(produit.getUrlPicture(), pointure, price, idCommande, statut, detailsCommande.getQuantite(), produit.getNom());

                detailsCommandeProfil.add(dCommande);
            }
        }



        model.addAttribute("client", client);
        model.addAttribute("commandes", detailsCommandeProfil);
        model.addAttribute("isConnected", isConnected);

        return "profil";
    }









}
