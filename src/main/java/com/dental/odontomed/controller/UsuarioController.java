package com.dental.odontomed.controller;

import com.dental.odontomed.entity.Documento;
import com.dental.odontomed.entity.Usuario;
import com.dental.odontomed.repository.DocumentosRepository;
import com.dental.odontomed.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocumentosRepository documentoRepo;

    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal) {

        Usuario user = usuarioService.buscarPorEmail(principal.getName());
        List<Documento> docs = documentoRepo.findByUsuarioId(user.getId());

        model.addAttribute("usuario", user);
        model.addAttribute("documentos", docs);
        model.addAttribute("usuario", user);

        return "perfil";
    }
}