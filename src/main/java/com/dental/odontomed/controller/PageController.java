package com.dental.odontomed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/eventos")
    public String eventos() {
        return "eventos";
    }
}

