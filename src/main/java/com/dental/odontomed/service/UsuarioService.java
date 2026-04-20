package com.dental.odontomed.service;

import com.dental.odontomed.model.Usuario;
import com.dental.odontomed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario guardar(Usuario u){
        return repo.save(u);
    }

    public Usuario buscarPorEmail(String email){
        return repo.findByEmail(email);
    }

}