package br.com.poupeAi.service;

import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.model.Usuario;

public interface UsuarioFacade {

    public Usuario criar(Usuario usuario) throws NegocioException;

    public Usuario buscar(Usuario usuario) throws ResourceNotFoundException;

    public Usuario atualizar(Usuario usuario) throws NegocioException, ResourceNotFoundException;

    public void remover(Usuario usuario) throws NegocioException, ResourceNotFoundException;

    public Usuario validar(Usuario usuario) throws NegocioException;

    public Usuario buscarPorEmail(String email) throws ResourceNotFoundException;
}
