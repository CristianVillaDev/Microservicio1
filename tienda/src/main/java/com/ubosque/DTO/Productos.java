package com.ubosque.DTO;

public class Productos {
	private int codigoProducto;
	private double ivaCompra;
	private int nitProveedor;
	private String nombreProducto;
	private double precioCompra;
	private double precioVenta;
	
	public int getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public double getIvaCompra() {
		return ivaCompra;
	}
	public void setIvaCompra(double ivaCompra) {
		this.ivaCompra = ivaCompra;
	}
	public int getNitProveedor() {
		return nitProveedor;
	}
	public void setNitProveedor(int nitProveedor) {
		this.nitProveedor = nitProveedor;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public double getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}
	public double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}
	
}
