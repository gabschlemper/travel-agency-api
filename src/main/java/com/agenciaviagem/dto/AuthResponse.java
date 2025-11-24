package com.agenciaviagem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de autenticação
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    private String tipo = "Bearer";
    private String email;
    private String nome;
    private String perfil;
    
    public AuthResponse(String token, String email, String nome, String perfil) {
        this.token = token;
        this.email = email;
        this.nome = nome;
        this.perfil = perfil;
    }
}
