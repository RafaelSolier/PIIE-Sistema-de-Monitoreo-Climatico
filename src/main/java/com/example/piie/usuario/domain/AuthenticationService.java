package com.example.piie.usuario.domain;

import com.example.piie.exception.ResourceNotFoundException;
import com.example.piie.persona.domain.Persona;
import com.example.piie.persona.dto.PersonaDto;
import com.example.piie.persona.infraestructure.PersonaRepository;
import com.example.piie.usuario.domain.Rol;
import com.example.piie.usuario.domain.Usuario;
import com.example.piie.usuario.dto.JwtAuthenticationResponse;
import com.example.piie.usuario.dto.SignUpRequest;
import com.example.piie.usuario.dto.SigninRequest;
import com.example.piie.usuario.dto.UsuarioDTO;
import com.example.piie.usuario.infraestructure.UsuarioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación: signup y signin.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Crea primero la entidad Persona, luego enlaza la nueva Persona al Usuario, guarda Usuario
     * y finalmente genera un JWT para el usuario creado.
     *
     * @param request DTO con los datos de registro (correo, contraseña, rol, dni, nombre, etc.)
     * @return JwtAuthenticationResponse que contiene el token recién generado
     */
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new com.example.piie.exception.IllegalArgumentException("Ya existe un usuario con el correo: " + request.getEmail());
        }


        // 1) Crear y persistir la entidad Persona
        Persona persona = new Persona();
        persona.setDni(request.getDni());
        persona.setNombre(request.getNombre());
        persona.setApellidos(request.getApellidos());
        persona.setCelular(request.getCelular());
        persona.setSexo(request.getSexo());
        Persona personaGuardada = personaRepository.save(persona);
        // Ahora personaGuardada.getIdPersona() está asignado automáticamente

        // 2) Construir el Usuario enlazando la Persona recién creada (MapsId)
        Usuario usuario = new Usuario();
        usuario.setPersona(personaGuardada); // gracias a @MapsId, usuario.idPersona = personaGuardada.getIdPersona()
        usuario.setRol(request.getRol() == 0 ? Rol.CLIENTE : Rol.ADMIN);
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3) Guardar Usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4) Generar token JWT basándonos en el correo (o username) del usuario
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(usuarioGuardado.getEmail())
                        .password(usuarioGuardado.getPassword())
                        .roles(usuarioGuardado.getRol().name())
                        .build()
        );

        return new JwtAuthenticationResponse(token);
    }

    /**
     * Realiza la autenticación (signin) de un usuario existente y genera un JWT si las credenciales son válidas.
     *
     * @param request DTO con email y password del usuario que inicia sesión.
     * @return JwtAuthenticationResponse con el token generado.
     * @throws IllegalArgumentException si las credenciales son inválidas o el usuario no existe.
     */
    public JwtAuthenticationResponse signin(SigninRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario con correo " + request.getEmail() + " no encontrado")
                );

        // 1) Intentar autenticar con AuthenticationManager (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        // 2) Si pasa la autenticación, buscar el usuario en BD
//        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Usuario con correo " + request.getEmail() + " no encontrado")
//                );

        // 3) Generar token JWT
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(usuario.getEmail())
                        .password(usuario.getPassword())
                        .roles(usuario.getRol().name())
                        .build()
        );

        return new JwtAuthenticationResponse(token);
    }
//    public Usuario getCurrentUser() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        return usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
//    }
// AuthenticationService.java
public UsuarioDTO getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

    // Inicializar la relación con Persona
    Hibernate.initialize(usuario.getPersona());

    // Mapear Persona a PersonaDTO
    Persona persona = usuario.getPersona();
    PersonaDto personaDTO = new PersonaDto(
            persona.getIdPersona(),
            persona.getNombre(),
            persona.getApellidos(),
            persona.getDni(),
            persona.getCelular(),
            persona.getSexo()
    );

    // Retornar UsuarioDTO
    return new UsuarioDTO(usuario.getEmail(), usuario.getRol(), personaDTO);
}
}
