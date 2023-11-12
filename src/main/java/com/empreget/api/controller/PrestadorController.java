package com.empreget.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empreget.domain.exception.EntidadeEmUsoException;
import com.empreget.domain.exception.EntidadeNaoEncontradaException;
import com.empreget.domain.model.Prestador;
import com.empreget.domain.repository.PrestadorRepository;
import com.empreget.domain.service.catalogoPrestadorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

	private PrestadorRepository prestadorRepository;

	private catalogoPrestadorService catalogoPrestadorService;

	@GetMapping
	public List<Prestador> listar(){
		return prestadorRepository.findAll();
	}
	
	@GetMapping("/{prestadorId}")
	public ResponseEntity<Prestador> buscar(@PathVariable Long prestadorId){
		
		return prestadorRepository.findById(prestadorId)
				.map(prestador -> ResponseEntity.ok(prestador))
				.orElse(ResponseEntity.notFound().build());
//		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Prestador adicionar(@Valid @RequestBody Prestador prestador) {
		return catalogoPrestadorService.salvar(prestador);
	}
	
	@PutMapping("/{prestadorId}")
	public ResponseEntity<Prestador> editar(@PathVariable Long prestadorId, @RequestBody Prestador prestador){
		Optional<Prestador> prestadorAtual = prestadorRepository.findById(prestadorId);
		
		if(prestadorAtual.isPresent()) {
			BeanUtils.copyProperties(prestador, prestadorAtual.get(), "id");
			
			Prestador prestadorSalvo = catalogoPrestadorService.salvar(prestadorAtual.get());
			
			return ResponseEntity.ok(prestadorSalvo);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{prestadorId}")
	public ResponseEntity<Prestador> excluir (@PathVariable Long prestadorId){
		
		try {
			if(prestadorRepository.existsById(prestadorId)) {
				catalogoPrestadorService.excluir(prestadorId);
			}
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
