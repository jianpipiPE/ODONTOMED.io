package com.dental.odontomed.controller;

import com.dental.odontomed.model.Documento;
import com.dental.odontomed.model.Usuario;
import com.dental.odontomed.repository.DocumentosRepository;
import com.dental.odontomed.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private DocumentosRepository documentoRepo;

    @GetMapping
    public String panel(Model model){
        model.addAttribute("usuarios", usuarioRepo.findAll());
        return "admin";
    }

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/crear")
    public String crearUsuario(Usuario u){
        u.setRol("USER");

        // 🔐 ENCRIPTAR PASSWORD
        u.setPassword(encoder.encode(u.getPassword()));

        usuarioRepo.save(u);
        return "redirect:/admin";
    }

    @PostMapping("/subir")
    public String subir(@RequestParam("file") MultipartFile file,
                        @RequestParam Long usuarioId) throws Exception {

        String carpeta = "uploads/";
        File dir = new File(carpeta);
        if (!dir.exists()) dir.mkdirs();

        String ruta = carpeta + file.getOriginalFilename();
        file.transferTo(new File(ruta));

        Documento doc = new Documento();
        doc.setNombre(file.getOriginalFilename());
        doc.setRuta(ruta);

        Usuario u = usuarioRepo.findById(usuarioId).get();
        doc.setUsuario(u);

        documentoRepo.save(doc);

        return "redirect:/admin";
    }

}