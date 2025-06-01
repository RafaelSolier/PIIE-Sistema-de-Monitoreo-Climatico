package com.example.piie.usuario.domain;

import com.example.piie.usuario.infraestructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(usuario -> new org.springframework.security.core.userdetails.User(
                        usuario.getEmail(),
                        usuario.getPassword(),
                        new ArrayList<>())) // Aquí puedes agregar roles o permisos si es necesario
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    public UserDetailsService userDetailsService() {
        return this;
    }
}