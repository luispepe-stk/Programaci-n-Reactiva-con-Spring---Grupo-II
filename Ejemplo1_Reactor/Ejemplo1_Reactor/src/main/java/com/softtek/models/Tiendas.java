package com.softtek.models;

import java.util.ArrayList;
import java.util.List;

public class Tiendas {

	private List<String> tiendas = new ArrayList<>();
	
	public void addTienda(String tienda) {
		tiendas.add(tienda);
	}

	@Override
	public String toString() {
		return "Tiendas [tiendas=" + tiendas + "]";
	}
	
	
}
