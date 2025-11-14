package com.agenciaviagem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um destino de viagem
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destino {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;
    
    private String descricao;
    
    private Double avaliacaoMedia;
    
    private Integer totalAvaliacoes;
    
    public Destino(Long id, String nome, String localizacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.avaliacaoMedia = 0.0;
        this.totalAvaliacoes = 0;
    }
}
