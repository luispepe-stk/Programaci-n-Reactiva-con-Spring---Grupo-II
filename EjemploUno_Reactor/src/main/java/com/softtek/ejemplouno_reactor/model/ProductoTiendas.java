package com.softtek.ejemplouno_reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoTiendas {
	private Producto producto;
	private Tiendas tiendas;
}
