package com.dental.odontomed.controller;

import org.springframework.ui.Model;
import com.dental.odontomed.entity.Usuario;
import com.dental.odontomed.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute
    public void addUserToModel(Model model, Principal principal) {
        if (principal != null) {
            Usuario user = usuarioService.buscarPorEmail(principal.getName());
            model.addAttribute("usuario", user);
        }
    }
}