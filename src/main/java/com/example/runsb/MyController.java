package com.example.runsb;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@Controller
public class MyController {


    @GetMapping("/contact")
    public String contact(Model model,
                          HttpSession session){


        DataBaseController controller = new DataBaseController();

        boolean isConnected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
        }
        model.addAttribute("isConnected", isConnected);


        cartDependencies(model, session);


        return "contact";
    }

    @RequestMapping(value = "/contactForm", method = RequestMethod.POST)
    public String contactForm(
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("numero") String numero,
            @RequestParam("message") String message,
    Model model){


        Mailing mailing = new Mailing();
        mailing.envoyerEmailContact(message, nom, email, numero);


        model.addAttribute("status", "success");
        model.addAttribute("message", "Message envoyé avec succès");
        return "resultatContact";
    }



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

        List<String> couleurs = Arrays.asList("Blanc", "Beige", "Jaune", "Rouge", "Vert", "Bleu", "Marron", "Gris", "Noir");
        model.addAttribute("couleurs", couleurs);

        List<String> marques = controller.getAllDistinctMarques();
        model.addAttribute("marques", marques);


        model.addAttribute("catalogue", catalogue);

        return "catalogue";
    }


    @GetMapping("/recherche")
    public String recherche(@RequestParam(name = "recherche", required = false) String motRecherche, Model model, HttpSession session) {

        DataBaseController controller = new DataBaseController();

        cartDependencies(model, session);

        boolean isConnected = false;
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID != null && userID != -1) {
            isConnected = true;
        }
        model.addAttribute("isConnected", isConnected);

        if (motRecherche != null && !motRecherche.isEmpty()) {

            List<Produit> resultats = controller.getProductBySearch(motRecherche);
            model.addAttribute("resultats", resultats);
            model.addAttribute("motRecherche", motRecherche);
        }

        return "recherche";
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

    @GetMapping("/AdminLogin")
    public String AdminLogin(){


        return "admin-login";
    }

    @GetMapping("/admin-interface")
    public String AdminInterface(Model model,
                                 HttpSession session){

        if (!isModConnected(session) && !isAdminConnected(session)){
            return "redirect:/AdminLogin";
        }


        Object obj = session.getAttribute("modLoggedIn");

        boolean ajouterProduit = true;
        boolean supprimerProduit = true;
        boolean modifierProduit = true;
        if (obj != null && obj instanceof Moderateur) {
            Moderateur modConnecte = (Moderateur) obj;
            ajouterProduit = modConnecte.isAjouterProduit();
            supprimerProduit = modConnecte.isSupprimerProduit();
            modifierProduit = modConnecte.isModifierProduit();
        }

        DataBaseController controller = new DataBaseController();

        List<Produit> catalogue = controller.getCatalogue();

        List<Moderateur> moderateurs = controller.getModerateurs();

        model.addAttribute("adminLoggedIn", isAdminConnected(session));
        model.addAttribute("ajouterProduit", ajouterProduit);
        model.addAttribute("supprimerProduit", supprimerProduit);
        model.addAttribute("modifierProduit", modifierProduit);
        model.addAttribute("moderateurs", moderateurs);
        model.addAttribute("catalogue", catalogue);



        return "admin-interface";
    }




    @GetMapping("/getProductDetails")
    public ResponseEntity<Produit> getProductDetails(@RequestParam("productId") String productId) {

        int productIdNotString = Integer.parseInt(productId);
        Produit produit = new DataBaseController().getProductById(productIdNotString);

        if (produit != null) {
            return new ResponseEntity<>(produit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String convertDroitsToJson(Moderateur moderateur) {
        return String.format("{\"ajouterProduit\": " + moderateur.isAjouterProduit() + ", \"supprimerProduit\": " + moderateur.isSupprimerProduit() + ", \"modifierProduit\": " + moderateur.isModifierProduit() + "}");
    }

    @GetMapping("/getModeratorDetails")
    public ResponseEntity<Moderateur> getModeratorDetails(@RequestParam("moderateurId") String moderateurId) {

        int moderateurIdNotString = Integer.parseInt(moderateurId);
        Moderateur moderateur = new DataBaseController().getModeratorById(moderateurIdNotString);

        try {
            return ResponseEntity.ok().body(moderateur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/AddProductForm")
    public String addProductForm(
            @RequestParam("nom") String nom,
            @RequestParam("prix") double prix,
            @RequestParam("marque") String marque,
            @RequestParam("description") String description,
            @RequestParam("urlPicture") String urlPicture,
            @RequestParam("motsCles") String motsCles,
            Model model) {

        boolean insertionReussie = new DataBaseController().insertProduit(nom, prix, marque, description, urlPicture, motsCles);

        if (insertionReussie) {
            model.addAttribute("status", "success");
            model.addAttribute("message", "Produit ajouté avec succès");
        } else {
            model.addAttribute("status", "error");
            model.addAttribute("message", "Erreur lors de l'ajout du produit");
        }

        return "resultat";
    }

    @PostMapping("/DeleteProductForm")
    public String deleteProductForm(@RequestParam("produitASupprimer") String productIdString, Model model) {
        try {
            int productId = Integer.parseInt(productIdString);


            if (new DataBaseController().deleteProduit(productId)) {
                model.addAttribute("status", "success");
                model.addAttribute("message", "Produit supprimé avec succès");
            } else {
                model.addAttribute("status", "error");
                model.addAttribute("message", "Erreur lors de la suppression du produit");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("status", "error");
            model.addAttribute("message", "ID de produit invalide");
        }

        return "resultat";
    }

    @PostMapping("/EditProductForm")
    public String editProductForm(@RequestParam("produitAModifier") String productIdString,
                                  @RequestParam("nomModif") String nom,
                                  @RequestParam("prixModif") String prix,
                                  @RequestParam("marqueModif") String marque,
                                  @RequestParam("descriptionModif") String description,
                                  @RequestParam("urlPictureModif") String urlPicture,
                                  Model model) {
        try {
            String message;
            int productId = Integer.parseInt(productIdString);

            Produit produit = new DataBaseController().getProductById(productId);

            if (produit != null) {
                produit.setNom(nom);
                produit.setPrix(prix);
                produit.setMarque(marque);
                produit.setDescription(description);
                produit.setUrlPicture(urlPicture);

                if (new DataBaseController().updateProduit(produit)) {
                    message = "Produit mis à jour avec succès";
                    model.addAttribute("status", "success");
                    model.addAttribute("message", message);
                } else {
                    message = "Échec de la mise à jour du produit";
                    model.addAttribute("status", "error");
                    model.addAttribute("message", message);
                }
            } else {
                message = "Produit non trouvé";
                model.addAttribute("status", "error");
                model.addAttribute("message", message);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("status", "error");
            model.addAttribute("message", "Données de formulaire invalides");
        }

        return "resultat";
    }

    @PostMapping("/AddModeratorForm")
    public String addModeratorForm(@RequestParam("nomMod") String nom,
                                   @RequestParam("prenom") String prenom,
                                   @RequestParam("email") String email,
                                   @RequestParam("motDePasse") String motDePasse,
                                   @RequestParam(value = "ajoutProduit", defaultValue = "false") boolean ajoutProduit,
                                   @RequestParam(value = "suppressionProduit", defaultValue = "false") boolean suppressionProduit,
                                   @RequestParam(value = "modificationProduit", defaultValue = "false") boolean modificationProduit,
                                   Model model) {
        try {
            DataBaseController controller = new DataBaseController();
            boolean insertionReussie = controller.insertModerator(nom, prenom, email, motDePasse, ajoutProduit, suppressionProduit, modificationProduit);

            if (insertionReussie) {
                model.addAttribute("status", "success");
                model.addAttribute("message", "Modérateur ajouté avec succès");
            } else {
                model.addAttribute("status", "error");
                model.addAttribute("message", "Erreur lors de l'ajout du modérateur");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("status", "error");
            model.addAttribute("message", "Une erreur inattendue s'est produite");
        }

        return "resultat";
    }

    @PostMapping("/DeleteModeratorForm")
    public String deleteModeratorForm(@RequestParam("moderateurASupprimer") int moderatorId, Model model) {
        try {
            // Supprimer le modérateur de la base de données
            if (new DataBaseController().deleteModerator(moderatorId)) {
                model.addAttribute("status", "success");
                model.addAttribute("message", "Modérateur supprimé avec succès");
            } else {
                model.addAttribute("status", "error");
                model.addAttribute("message", "Erreur lors de la suppression du modérateur");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("status", "error");
            model.addAttribute("message", "ID de modérateur invalide");
        }

        return "resultat";
    }

    @PostMapping("/EditModeratorForm")
    public String editModeratorForm(@RequestParam("modAModifier") int moderatorId, @RequestParam(value = "droits[]", required = false) String[] droits, Model model) {
        try {
            boolean ajoutProduitModif = false;
            boolean suppressionProduitModif = false;
            boolean modificationProduitModif = false;

            if (droits != null) {
                List<String> droitsList = Arrays.asList(droits);

                if (droitsList.size() > 0) {
                    ajoutProduitModif = "true".equals(droitsList.get(0)) || "on".equals(droitsList.get(0));
                }

                if (droitsList.size() > 1) {
                    suppressionProduitModif = "true".equals(droitsList.get(1)) || "on".equals(droitsList.get(1));
                }

                if (droitsList.size() > 2) {
                    modificationProduitModif = "true".equals(droitsList.get(2)) || "on".equals(droitsList.get(2));
                }
            }

            Moderateur moderateur = new DataBaseController().getModeratorById(moderatorId);

            if (moderateur != null) {
                moderateur.setAjouterProduit(ajoutProduitModif);
                moderateur.setSupprimerProduit(suppressionProduitModif);
                moderateur.setModifierProduit(modificationProduitModif);

                if (new DataBaseController().updateModerator(moderateur)) {
                    model.addAttribute("status", "success");
                    model.addAttribute("message", "Permissions mises à jour avec succès");
                } else {
                    model.addAttribute("status", "error");
                    model.addAttribute("message", "Échec de la mise à jour des permissions");
                }
            } else {
                model.addAttribute("status", "error");
                model.addAttribute("message", "Modérateur non trouvé");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("status", "error");
            model.addAttribute("message", "Données de formulaire invalides");
        }

        return "resultat";
    }

    @GetMapping("/AdminLogout")
    public String adminLogout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/AdminLogin";
    }


    @PostMapping("/loginAdminForm")
    public String loginAdminForm(
            @RequestParam String email,
            @RequestParam String motDePasse,
            Model model,
            HttpSession session
    ) throws SQLException {
        DataBaseController controller = new DataBaseController();

        if (controller.checkAdminCredentials(email, motDePasse)) {
            session.setAttribute("adminLoggedIn", true);
        } else if (controller.checkModeratorCredentials(email, motDePasse)) {
            Moderateur modConnecte = controller.getModeratorByEmail(email);
            session.setAttribute("modLoggedIn", modConnecte);
        } else {

            model.addAttribute("erreur", "L'authentification a échoué");
            return "admin-login";
        }

        return "redirect:/admin-interface";

    }

    private boolean isAdminConnected(HttpSession session) {
        return session.getAttribute("adminLoggedIn") != null;
    }


    private boolean isModConnected(HttpSession session) {
        return session.getAttribute("modLoggedIn") != null;
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

                Mailing mailing = new Mailing();

                mailing.envoyerEmailConfirmation(email);

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

        creerRecapCommande(session, idClient);

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

    void creerRecapCommande(HttpSession session, Integer idClient){
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

        controller2.addFidelitePoints(idClient, total/10);


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
        } else {
            return "login";
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

    @GetMapping("/paiement")
    public String afficherPaiement(Model model, HttpSession session) {
        DataBaseController dataBaseController = new DataBaseController();
        Integer idClient = (Integer) session.getAttribute("userID");
        List<PanierItem> panierItems = (List<PanierItem>) session.getAttribute("recapCommande");
        int totalCommande = (Integer) session.getAttribute("totalCommande");

        model.addAttribute("panierItems", panierItems);
        model.addAttribute("totalCommande", totalCommande);
        boolean verifsolde =  dataBaseController.getVerifsolde(idClient);

        model.addAttribute("verifsolde", verifsolde);

        return "paiement";
    }

    @GetMapping("/soldeInsuffisant")
    public String soldeInsuffisant() {
        return "soldeInsuffisant";
    }
}


