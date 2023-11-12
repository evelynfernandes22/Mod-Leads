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
import com.empreget.domain.model.Cliente;
import com.empreget.domain.repository.ClienteRepository;
import com.empreget.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	private final CatalogoClienteService catalogoClienteService;


	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();

	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long clienteId) {
		return clienteRepository.findById(clienteId).map(cliente -> ResponseEntity.ok(cliente))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> editar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente) {
		Optional<Cliente> clienteAtual = clienteRepository.findById(clienteId);

		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(cliente, clienteAtual.get(), "id");
		Cliente clienteSalvo = catalogoClienteService.salvar(clienteAtual.get());

		return ResponseEntity.ok(clienteSalvo);
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId) {

		try {
			if (clienteRepository.existsById(clienteId)) {
				catalogoClienteService.excluir(clienteId);
			}
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
