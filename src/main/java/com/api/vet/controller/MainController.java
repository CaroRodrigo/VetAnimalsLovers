package com.api.vet.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("")
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("error", "error");
        return "index.html";
    }

    @GetMapping("/super")
    public String superSu() {
        return "panel-SuperSu";
    }

    @GetMapping("/usuario")
    public String usuario(Model model) {
        //model.addAttribute("clientes", cs.listAll());
        return "panel-Usuario";
    }

    @GetMapping("/vendedor")
    public String vendedor() {
        return "panel-Vendedor";
    }
}
