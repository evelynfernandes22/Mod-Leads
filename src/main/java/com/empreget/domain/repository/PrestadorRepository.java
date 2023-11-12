package com.empreget.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empreget.domain.model.Cliente;
import com.empreget.domain.model.Prestador;

public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
	
	Optional<Cliente> findByNome(String nome);

	List<Cliente> findByNomeContaining(String nome);
	
	boolean existsByNome (String nome);

}
