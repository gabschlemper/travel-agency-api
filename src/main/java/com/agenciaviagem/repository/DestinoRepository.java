package com.agenciaviagem.repository;

import com.agenciaviagem.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para a entidade Destino
 */
@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    
    /**
     * Pesquisa destinos por nome ou localização
     * @param termo Termo de pesquisa
     * @return Lista de destinos encontrados
     */
    @Query("SELECT d FROM Destino d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :termo, '%')) " +
           "OR LOWER(d.localizacao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Destino> pesquisarPorNomeOuLocalizacao(@Param("termo") String termo);
    
    /**
     * Busca destinos por nome
     * @param nome Nome do destino
     * @return Lista de destinos com o nome especificado
     */
    List<Destino> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca destinos por localização
     * @param localizacao Localização do destino
     * @return Lista de destinos na localização especificada
     */
    List<Destino> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
