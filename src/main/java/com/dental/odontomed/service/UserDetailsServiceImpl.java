package com.dental.odontomed.service;

import com.dental.odontomed.model.Usuario;
import com.dental.odontomed.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Usuario user = repo.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol())
                .build();
    }
}
