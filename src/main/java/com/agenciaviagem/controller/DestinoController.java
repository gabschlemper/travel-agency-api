package com.agenciaviagem.controller;

import com.agenciaviagem.dto.AvaliacaoRequest;
import com.agenciaviagem.model.Destino;
import com.agenciaviagem.service.DestinoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciamento de destinos de viagem
 */
@RestController
@RequestMapping("/api/destinos")
public class DestinoController {
    
    @Autowired
    private DestinoService destinoService;
    
    /**
     * Endpoint 1: Cadastro de Destino de Viagem
     * POST /api/destinos
     * @param destino Dados do destino a ser cadastrado
     * @return Destino cadastrado com status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Destino> cadastrarDestino(@Valid @RequestBody Destino destino) {
        Destino novoDestino = destinoService.cadastrarDestino(destino);
        return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint 2: Listagem de Destinos de Viagem
     * GET /api/destinos
     * @return Lista de todos os destinos com status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        List<Destino> destinos = destinoService.listarTodos();
        return ResponseEntity.ok(destinos);
    }
    
    /**
     * Endpoint 3: Pesquisa de Destinos por nome ou localização
     * GET /api/destinos/pesquisar?termo={termo}
     * @param termo Termo de pesquisa (nome ou localização)
     * @return Lista de destinos encontrados com status 200 (OK)
     */
    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisarDestinos(@RequestParam String termo) {
        List<Destino> destinos = destinoService.pesquisarDestinos(termo);
        return ResponseEntity.ok(destinos);
    }
    
    /**
     * Endpoint 4: Visualização de Informações Detalhadas de um Destino
     * GET /api/destinos/{id}
     * @param id ID do destino
     * @return Destino com status 200 (OK) ou 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarDestinoPorId(@PathVariable Long id) {
        Optional<Destino> destino = destinoService.buscarPorId(id);
        return destino.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Endpoint 5: Avaliação de Destino de Viagem
     * PATCH /api/destinos/{id}/avaliar
     * @param id ID do destino a ser avaliado
     * @param avaliacaoRequest Objeto contendo a nota (1 a 10)
     * @return Destino com avaliação atualizada com status 200 (OK)
     */
    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<?> avaliarDestino(
            @PathVariable Long id, 
            @RequestBody AvaliacaoRequest avaliacaoRequest) {
        try {
            Destino destinoAvaliado = destinoService.avaliarDestino(id, avaliacaoRequest.getNota());
            return ResponseEntity.ok(destinoAvaliado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Endpoint para atualização completa de um destino
     * PUT /api/destinos/{id}
     * @param id ID do destino a ser atualizado
     * @param destino Dados atualizados do destino
     * @return Destino atualizado com status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarDestino(
            @PathVariable Long id, 
            @Valid @RequestBody Destino destino) {
        try {
            Destino destinoAtualizado = destinoService.atualizarDestino(id, destino);
            return ResponseEntity.ok(destinoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Endpoint 6: Exclusão de Destino de Viagem
     * DELETE /api/destinos/{id}
     * @param id ID do destino a ser excluído
     * @return Status 204 (No Content) se excluído ou 404 (Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        boolean excluido = destinoService.excluirDestino(id);
        if (excluido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
