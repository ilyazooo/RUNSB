package com.example.runsb;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyController {

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("name", "World");
        return "helloWorld";
    }

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
            Model model
    ) {
        DataBaseController controller = new DataBaseController();
        List<Produit> catalogue = controller.getFilteredProducts(marque, couleur, genre);

        model.addAttribute("catalogue", catalogue);

        return "catalogue";
    }


}
