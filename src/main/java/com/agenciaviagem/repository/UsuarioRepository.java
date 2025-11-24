package com.agenciaviagem.repository;

import com.agenciaviagem.model.Perfil;
import com.agenciaviagem.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return Optional com o usuário encontrado
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica se existe um usuário com o email informado
     * @param email Email a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca usuários por perfil
     * @param perfil Perfil dos usuários
     * @return Lista de usuários com o perfil especificado
     */
    List<Usuario> findByPerfil(Perfil perfil);
    
    /**
     * Busca usuários ativos
     * @param ativo Status de ativação
     * @return Lista de usuários ativos ou inativos
     */
    List<Usuario> findByAtivo(Boolean ativo);
}
