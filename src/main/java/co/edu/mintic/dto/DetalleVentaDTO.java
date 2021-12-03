package co.edu.mintic.dto;

import java.io.Serializable;

public class DetalleVentaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private Long codDetalleVenta;
	private Long codVenta;
	private int cantProducto;
	private double valorIva;
	private double valorTotal;
	private double valorVenta;
	private ProductosDTO producto;

	public DetalleVentaDTO() {
	}

	public long getCodDetalleVenta() {
		return this.codDetalleVenta;
	}

	public void setCodDetalleVenta(long codDetalleVenta) {
		this.codDetalleVenta = codDetalleVenta;
	}

	public int getCantProducto() {
		return this.cantProducto;
	}

	public void setCantProducto(int cantProducto) {
		this.cantProducto = cantProducto;
	}

	public double getValorIva() {
		return this.valorIva;
	}

	public void setValorIva(double valorIva) {
		this.valorIva = valorIva;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public double getValorVenta() {
		return this.valorVenta;
	}

	public void setValorVenta(double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public ProductosDTO getProducto() {
		return this.producto;
	}

	public void setProducto(ProductosDTO producto) {
		this.producto = producto;
	}

	public long getCodVenta() {
		return codVenta;
	}

	public void setCodVenta(long codVenta) {
		this.codVenta = codVenta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String str = "{ ";
		if (codDetalleVenta != null) {
			str += " \"codigoDetalleVenta\":" + codDetalleVenta + ", ";
		}
		str += " \"cantidadProducto\":" + cantProducto + ", " + "\"valorIva\":" + valorIva + ", " + "\"valorTotal\":"
				+ valorTotal + ", " + "\"valorVenta\":" + valorVenta + ", " + "\"producto\": " + producto + ", "
				+ "\"venta\": {" + "              \"codigoVenta\": " + codVenta + " " + "           } ";
		if (id != null)
			str += "   ,\"id\":" + id + " ";
		str += " } ";

		return str;
	}

}