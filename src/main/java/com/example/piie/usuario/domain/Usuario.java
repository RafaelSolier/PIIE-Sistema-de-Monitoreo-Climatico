package com.example.piie.usuario.domain;

import com.example.piie.persona.domain.Persona;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    private Long idPersona;    // FK a Persona
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;        // e.g. "CLIENTE", "ADMIN"

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // almacenada encriptada

    private Boolean expired = false;

    private Boolean locked = false;

    private Boolean credentialsExpired = false;

    private Boolean enable = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    // getters y setters
}