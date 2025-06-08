package com.example.piie.usuario.aplication;

import com.example.piie.usuario.domain.AuthenticationService;
import com.example.piie.usuario.domain.Usuario;
import com.example.piie.usuario.dto.JwtAuthenticationResponse;
import com.example.piie.usuario.dto.SignUpRequest;
import com.example.piie.usuario.dto.SigninRequest;
import com.example.piie.usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid  @RequestBody SignUpRequest request) {
        JwtAuthenticationResponse response = authenticationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    //GET /api/auth/me (Obtener datos del usuario actual)
//    @GetMapping("/me")
//    public ResponseEntity<Usuario> getCurrentUser() {
//        Usuario usuario = authenticationService.getCurrentUser();
//        return ResponseEntity.ok(usuario);
//    }
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getCurrentUser() {
        UsuarioDTO usuario = authenticationService.getCurrentUser();
        return ResponseEntity.ok(usuario);
    }

}
