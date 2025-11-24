package com.agenciaviagem.model;

/**
 * Enum que representa os perfis de acesso do sistema
 */
public enum Perfil {
    /**
     * Administrador - Acesso total ao sistema
     * Pode: Criar, Ler, Atualizar e Excluir destinos
     */
    ADMIN,
    
    /**
     * Usuário comum - Acesso limitado
     * Pode: Ler e Avaliar destinos
     */
    USER
}
