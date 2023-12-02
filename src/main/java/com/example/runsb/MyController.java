package com.example.runsb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("name", "World");
        return "helloWorld";
    }

    @GetMapping("/")
    public String accueil(Model model) {
        return "accueil";
    }


}
