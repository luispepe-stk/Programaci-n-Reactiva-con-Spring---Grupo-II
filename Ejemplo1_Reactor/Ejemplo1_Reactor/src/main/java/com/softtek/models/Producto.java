package com.softtek.models;

public class Producto {
	
	private int ID;
	private String descripcion;
	private double precio;
	
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(int iD, String descripcion, double precio) {
		super();
		ID = iD;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [ID=" + ID + ", descripcion=" + descripcion + ", precio=" + precio + "]";
	}
	
}
