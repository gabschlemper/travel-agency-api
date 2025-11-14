package com.agenciaviagem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de avaliação de destino
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoRequest {
    
    private Integer nota;
    
}
