package com.empreget.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoModel {

	private String descricao;
	private BigDecimal valor;
}
