package com.empreget.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class Endereco {
	
	@NotBlank
	@Column(name = "end_logradouro")
	private String logradouro;
	
	@NotBlank
	@Column(name = "end_numero")
	private int numero;
	
	@Column(name = "end_complemento")
	private String complemento;
	
	@NotBlank
	@Column(name = "end_cep")
	private String cep;
	
	@NotBlank
	@Column(name = "end_bairro")
	private String bairro;
	
	@NotBlank
	@Column(name = "end_cidade")
	private String cidade;
	
	@NotBlank
	@Column(name = "end_estado")
	private String estado;
	
	@NotBlank
	@Column(name = "end_pais")
	private String pais;
	
	public Endereco(@NotBlank String logradouro, @NotBlank int numero, String complemento, @NotBlank String cep,
			@NotBlank String bairro, @NotBlank String cidade, @NotBlank String estado, @NotBlank String pais) {
		super();
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
	}

	public Endereco() {		
		
	}
	
	
	
	

}
