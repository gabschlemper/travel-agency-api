package com.agenciaviagem.service;

import com.agenciaviagem.model.Destino;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Camada de serviço responsável pela lógica de negócios relacionada aos destinos
 */
@Service
public class DestinoService {
    
    private final List<Destino> destinos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);
    
    /**
     * Cadastra um novo destino de viagem
     * @param destino Destino a ser cadastrado
     * @return Destino cadastrado com ID gerado
     */
    public Destino cadastrarDestino(Destino destino) {
        destino.setId(contador.getAndIncrement());
        
        // Inicializa valores padrão se não informados
        if (destino.getAvaliacaoMedia() == null) {
            destino.setAvaliacaoMedia(0.0);
        }
        if (destino.getTotalAvaliacoes() == null) {
            destino.setTotalAvaliacoes(0);
        }
        
        destinos.add(destino);
        return destino;
    }
    
    /**
     * Lista todos os destinos disponíveis
     * @return Lista de todos os destinos
     */
    public List<Destino> listarTodos() {
        return new ArrayList<>(destinos);
    }
    
    /**
     * Pesquisa destinos por nome ou localização
     * @param termo Termo de pesquisa
     * @return Lista de destinos que correspondem ao termo
     */
    public List<Destino> pesquisarDestinos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        
        String termoBusca = termo.toLowerCase();
        return destinos.stream()
                .filter(d -> d.getNome().toLowerCase().contains(termoBusca) || 
                            d.getLocalizacao().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca um destino específico por ID
     * @param id ID do destino
     * @return Optional contendo o destino, se encontrado
     */
    public Optional<Destino> buscarPorId(Long id) {
        return destinos.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }
    
    /**
     * Avalia um destino, recalculando sua média
     * @param id ID do destino
     * @param nota Nota da avaliação (1 a 10)
     * @return Destino com a avaliação atualizada
     * @throws IllegalArgumentException se a nota for inválida
     */
    public Destino avaliarDestino(Long id, Integer nota) {
        if (nota == null || nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10");
        }
        
        Destino destino = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));
        
        // Calcula a nova média
        Double somaAvaliacoes = destino.getAvaliacaoMedia() * destino.getTotalAvaliacoes();
        somaAvaliacoes += nota;
        destino.setTotalAvaliacoes(destino.getTotalAvaliacoes() + 1);
        destino.setAvaliacaoMedia(somaAvaliacoes / destino.getTotalAvaliacoes());
        
        return destino;
    }
    
    /**
     * Atualiza um destino existente
     * @param id ID do destino a ser atualizado
     * @param destinoAtualizado Dados atualizados do destino
     * @return Destino atualizado
     */
    public Destino atualizarDestino(Long id, Destino destinoAtualizado) {
        Destino destino = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));
        
        destino.setNome(destinoAtualizado.getNome());
        destino.setLocalizacao(destinoAtualizado.getLocalizacao());
        destino.setDescricao(destinoAtualizado.getDescricao());
        
        // Mantém as avaliações existentes
        if (destinoAtualizado.getAvaliacaoMedia() != null) {
            destino.setAvaliacaoMedia(destinoAtualizado.getAvaliacaoMedia());
        }
        if (destinoAtualizado.getTotalAvaliacoes() != null) {
            destino.setTotalAvaliacoes(destinoAtualizado.getTotalAvaliacoes());
        }
        
        return destino;
    }
    
    /**
     * Exclui um destino
     * @param id ID do destino a ser excluído
     * @return true se excluído com sucesso
     */
    public boolean excluirDestino(Long id) {
        return destinos.removeIf(d -> d.getId().equals(id));
    }
}
