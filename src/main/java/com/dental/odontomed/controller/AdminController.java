package com.dental.odontomed.controller;

import com.dental.odontomed.entity.Documento;
import com.dental.odontomed.entity.Usuario;
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

    @GetMapping("")
    public String panel(Model model) {

        model.addAttribute("usuarios", usuarioRepo.findAll());
        model.addAttribute("documentos", documentoRepo.findAll());

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
    public String subirArchivo(@RequestParam("file") MultipartFile file,
                               @RequestParam("usuarioId") Long usuarioId,
                               Model model) {

        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                model.addAttribute("error", "❌ El archivo no puede estar vacío");
                model.addAttribute("usuarios", usuarioRepo.findAll());
                model.addAttribute("documentos", documentoRepo.findAll());
                return "admin";
            }

            // Validar que el usuario exista
            Usuario usuario = usuarioRepo.findById(usuarioId).orElse(null);
            if (usuario == null) {
                model.addAttribute("error", "❌ El usuario con ID " + usuarioId + " no existe");
                model.addAttribute("usuarios", usuarioRepo.findAll());
                model.addAttribute("documentos", documentoRepo.findAll());
                return "admin";
            }

            // Crear carpeta en la raíz del proyecto
            String rutaBase = System.getProperty("user.dir");
            String carpeta = rutaBase + File.separator + "uploads";
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();

            // Guardar el archivo con nombre único
            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String rutaCompleta = carpeta + File.separator + nombreArchivo;
            file.transferTo(new File(rutaCompleta));

            // Guardar en la base de datos (ruta relativa para servir)
            String rutaRelativa = "uploads/" + nombreArchivo;

            Documento doc = new Documento();
            doc.setNombre(file.getOriginalFilename());
            doc.setRuta(rutaRelativa);
            doc.setUsuario(usuario);

            documentoRepo.save(doc);

            model.addAttribute("usuarios", usuarioRepo.findAll());
            model.addAttribute("documentos", documentoRepo.findAll());

            return "redirect:/admin?success=true";

        } catch (Exception e) {
            model.addAttribute("error", "❌ Error al subir el archivo: " + e.getMessage());
            model.addAttribute("usuarios", usuarioRepo.findAll());
            model.addAttribute("documentos", documentoRepo.findAll());
            return "admin";
        }
    }


}