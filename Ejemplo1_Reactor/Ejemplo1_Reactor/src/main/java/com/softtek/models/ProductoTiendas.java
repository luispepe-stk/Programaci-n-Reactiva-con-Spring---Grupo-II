package com.softtek.models;

public class ProductoTiendas {
	
	private Producto producto;
	private Tiendas tiendas;
	
	public ProductoTiendas() {
		// TODO Auto-generated constructor stub
	}

	public ProductoTiendas(Producto producto, Tiendas tiendas) {
		super();
		this.producto = producto;
		this.tiendas = tiendas;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Tiendas getTiendas() {
		return tiendas;
	}

	public void setTiendas(Tiendas tiendas) {
		this.tiendas = tiendas;
	}

	@Override
	public String toString() {
		return "ProductoTiendas [producto=" + producto + ", tiendas=" + tiendas + "]";
	}
	
	

}
