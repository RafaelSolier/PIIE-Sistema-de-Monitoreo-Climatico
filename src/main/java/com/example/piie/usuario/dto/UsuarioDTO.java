package com.example.piie.usuario.dto;

import com.example.piie.persona.domain.Persona;
import com.example.piie.persona.dto.PersonaDto;
import com.example.piie.usuario.domain.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private Rol rol;
    private PersonaDto persona;

    public UsuarioDTO(String email, Rol rol, PersonaDto persona) {
        this.email = email;
        this.rol = rol;
        this.persona = persona;
    }
}