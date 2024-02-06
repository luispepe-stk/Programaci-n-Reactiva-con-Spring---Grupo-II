package com.softtek.ejemplouno_reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Producto {
	private Integer id;
	private String descripcion;
	private Double precio;
}
