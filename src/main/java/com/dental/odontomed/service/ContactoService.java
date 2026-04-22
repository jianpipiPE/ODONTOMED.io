package com.dental.odontomed.service;

import com.dental.odontomed.entity.Contacto;
import com.dental.odontomed.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    public void guardarYEnviar(Contacto c) {

        // Guardar en BD
        repo.save(c);

        // Enviar correo
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo("jianpierha@gmail.com");
        mensaje.setSubject("Nuevo mensaje de contacto");

        mensaje.setText(
                "Nombre: " + c.getNombre() +
                        "\nEmail: " + c.getEmail() +
                        "\nMensaje: " + c.getMensaje()
        );

        mailSender.send(mensaje);
    }
}