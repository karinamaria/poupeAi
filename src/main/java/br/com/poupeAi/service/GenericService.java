package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.model.AbstractEntity;
import br.com.poupeAi.repository.GenericRepository;

import java.util.List;

public abstract class GenericService<T extends AbstractEntity, R extends GenericRepository<T>> {
    protected R repository;

    protected String nomeEntidade;

    public void validar(T entidade) throws NegocioException {
    }

    public void validarNaRemocao(T entidade) throws NegocioException, ResourceNotFoundException {
    }

    public T salvar(T entidade) throws NegocioException {
        this.validar(entidade);
        return this.repository.save(entidade);
    }

    public T buscarPorId(Long id) {
        T entidadeEncontrada = this.repository.findById(id).orElse(null);
        if(entidadeEncontrada == null)
            throw new ResourceNotFoundException(this.nomeEntidade + " não encontrado(a).");
        return entidadeEncontrada;
    }

    public T atualizar(T entidade) throws NegocioException, ResourceNotFoundException {
        this.buscarPorId(entidade.getId());
        return this.salvar(entidade);
    }

    public void remover(Long id) throws NegocioException, ResourceNotFoundException {
        T entidadeEncontrada = this.buscarPorId(id);
        this.validarNaRemocao(entidadeEncontrada);
        this.repository.deleteById(entidadeEncontrada.getId());
    }

    public List<T> buscarTodosPorAtivo(boolean ativo) {
        List<T> entidades = this.repository.findAllByAtivo(ativo);
        if(entidades.isEmpty())
            throw new ResourceNotFoundException("Nenhum(a) " + this.nomeEntidade.toLowerCase() + " foi encontrado(a).");
        return entidades;
    }
}
