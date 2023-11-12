package com.empreget.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.empreget.domain.exception.EntidadeEmUsoException;
import com.empreget.domain.exception.EntidadeNaoEncontradaException;
import com.empreget.domain.model.Prestador;
import com.empreget.domain.repository.PrestadorRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class catalogoPrestadorService {
	
	private PrestadorRepository prestadorRepository;
	
	public Prestador salvar(Prestador prestador) {
		return prestadorRepository.save(prestador);
	}
	
	public void excluir (Long prestadorId) {
		try {
			prestadorRepository.deleteById(prestadorId);
		
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de Prestador com o código %d", prestadorId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Prestador de código %d não pode ser removido, pois está em uso", prestadorId));
		}
	}

}
