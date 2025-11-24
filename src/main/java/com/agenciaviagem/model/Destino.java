package com.agenciaviagem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um destino de viagem
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "destinos")
public class Destino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "Localização é obrigatória")
    @Column(nullable = false, length = 100)
    private String localizacao;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "avaliacao_media")
    private Double avaliacaoMedia;
    
    @Column(name = "total_avaliacoes")
    private Integer totalAvaliacoes;
    
    public Destino(Long id, String nome, String localizacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.avaliacaoMedia = 0.0;
        this.totalAvaliacoes = 0;
    }
    
    @PrePersist
    protected void onCreate() {
        if (avaliacaoMedia == null) {
            avaliacaoMedia = 0.0;
        }
        if (totalAvaliacoes == null) {
            totalAvaliacoes = 0;
        }
    }
}
