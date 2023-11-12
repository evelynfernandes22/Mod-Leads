package com.empreget.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.empreget.api.dto.PedidoModel;
import com.empreget.domain.model.Pedido;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoAssembler {

	private ModelMapper modelMapper;
	
	
	public PedidoModel toModel (Pedido pedido) {
		return modelMapper.map(pedido, PedidoModel.class);
	}
	
	
	public List<PedidoModel> toCollectionModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
}
