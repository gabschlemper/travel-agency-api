package com.agenciaviagem.controller;

import com.agenciaviagem.dto.AuthResponse;
import com.agenciaviagem.dto.LoginRequest;
import com.agenciaviagem.dto.RegistroRequest;
import com.agenciaviagem.model.Usuario;
import com.agenciaviagem.security.JwtService;
import com.agenciaviagem.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para autenticação e registro de usuários
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Endpoint para registro de novos usuários
     * POST /api/auth/registro
     * @param request Dados do usuário a ser registrado
     * @return Resposta com token JWT
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequest request) {
        try {
            // Cria o usuário
            Usuario usuario = new Usuario();
            usuario.setNome(request.getNome());
            usuario.setEmail(request.getEmail());
            usuario.setSenha(request.getSenha());
            usuario.setPerfil(request.getPerfil());
            usuario.setAtivo(true);
            
            // Cadastra o usuário
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            
            // Gera o token
            UserDetails userDetails = usuarioService.loadUserByUsername(novoUsuario.getEmail());
            String token = jwtService.generateToken(userDetails);
            
            // Retorna a resposta
            AuthResponse response = new AuthResponse(
                    token,
                    novoUsuario.getEmail(),
                    novoUsuario.getNome(),
                    novoUsuario.getPerfil().name()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao registrar usuário: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint para login de usuários
     * POST /api/auth/login
     * @param request Credenciais de login
     * @return Resposta com token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Autentica o usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getSenha()
                    )
            );
            
            // Carrega os detalhes do usuário
            Usuario usuario = usuarioService.buscarPorEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));
            
            // Gera o token
            UserDetails userDetails = usuarioService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);
            
            // Retorna a resposta
            AuthResponse response = new AuthResponse(
                    token,
                    usuario.getEmail(),
                    usuario.getNome(),
                    usuario.getPerfil().name()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer login: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint para verificar se o token é válido
     * GET /api/auth/validar
     * @return Status da validação
     */
    @GetMapping("/validar")
    public ResponseEntity<String> validar() {
        return ResponseEntity.ok("Token válido");
    }
}
