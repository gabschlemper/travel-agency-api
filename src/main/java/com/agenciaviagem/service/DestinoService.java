package com.agenciaviagem.service;

import com.agenciaviagem.model.Destino;
import com.agenciaviagem.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável pela lógica de negócios relacionada aos destinos
 */
@Service
@Transactional
public class DestinoService {
    
    @Autowired
    private DestinoRepository destinoRepository;
    
    /**
     * Cadastra um novo destino de viagem
     * @param destino Destino a ser cadastrado
     * @return Destino cadastrado com ID gerado
     */
    public Destino cadastrarDestino(Destino destino) {
        // Inicializa valores padrão se não informados
        if (destino.getAvaliacaoMedia() == null) {
            destino.setAvaliacaoMedia(0.0);
        }
        if (destino.getTotalAvaliacoes() == null) {
            destino.setTotalAvaliacoes(0);
        }
        
        return destinoRepository.save(destino);
    }
    
    /**
     * Lista todos os destinos disponíveis
     * @return Lista de todos os destinos
     */
    @Transactional(readOnly = true)
    public List<Destino> listarTodos() {
        return destinoRepository.findAll();
    }
    
    /**
     * Pesquisa destinos por nome ou localização
     * @param termo Termo de pesquisa
     * @return Lista de destinos que correspondem ao termo
     */
    @Transactional(readOnly = true)
    public List<Destino> pesquisarDestinos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        
        return destinoRepository.pesquisarPorNomeOuLocalizacao(termo);
    }
    
    /**
     * Busca um destino específico por ID
     * @param id ID do destino
     * @return Optional contendo o destino, se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Destino> buscarPorId(Long id) {
        return destinoRepository.findById(id);
    }
    
    /**
     * Avalia um destino, recalculando sua média
     * @param id ID do destino
     * @param nota Nota da avaliação (1 a 10)
     * @return Destino com a avaliação atualizada
     * @throws IllegalArgumentException se a nota for inválida ou destino não for encontrado
     */
    public Destino avaliarDestino(Long id, Integer nota) {
        if (nota == null || nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10");
        }
        
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));
        
        // Calcula a nova média
        Double somaAvaliacoes = destino.getAvaliacaoMedia() * destino.getTotalAvaliacoes();
        somaAvaliacoes += nota;
        destino.setTotalAvaliacoes(destino.getTotalAvaliacoes() + 1);
        destino.setAvaliacaoMedia(somaAvaliacoes / destino.getTotalAvaliacoes());
        
        return destinoRepository.save(destino);
    }
    
    /**
     * Atualiza um destino existente
     * @param id ID do destino a ser atualizado
     * @param destinoAtualizado Dados atualizados do destino
     * @return Destino atualizado
     * @throws IllegalArgumentException se o destino não for encontrado
     */
    public Destino atualizarDestino(Long id, Destino destinoAtualizado) {
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));
        
        destino.setNome(destinoAtualizado.getNome());
        destino.setLocalizacao(destinoAtualizado.getLocalizacao());
        destino.setDescricao(destinoAtualizado.getDescricao());
        
        // Mantém as avaliações existentes se não forem informadas
        if (destinoAtualizado.getAvaliacaoMedia() != null) {
            destino.setAvaliacaoMedia(destinoAtualizado.getAvaliacaoMedia());
        }
        if (destinoAtualizado.getTotalAvaliacoes() != null) {
            destino.setTotalAvaliacoes(destinoAtualizado.getTotalAvaliacoes());
        }
        
        return destinoRepository.save(destino);
    }
    
    /**
     * Exclui um destino
     * @param id ID do destino a ser excluído
     * @return true se excluído com sucesso
     */
    public boolean excluirDestino(Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
