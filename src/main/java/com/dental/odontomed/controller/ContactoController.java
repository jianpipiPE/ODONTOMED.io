package com.dental.odontomed.controller;

import com.dental.odontomed.entity.Contacto;
import com.dental.odontomed.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {

    @Autowired
    private ContactoService service;

    @PostMapping("/contacto")
    public String enviarMensaje(Contacto contacto) {

        service.guardarYEnviar(contacto);

        return "redirect:/?ok"; // puedes mostrar alerta
    }
}