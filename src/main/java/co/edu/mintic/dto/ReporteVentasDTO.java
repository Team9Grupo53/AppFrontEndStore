package co.edu.mintic.dto;

import java.io.Serializable;

public class ReporteVentasDTO implements Serializable  {

	private static final long serialVersionUID = 1L;
//v.cliente.cedulaCliente, v.cliente.nombreCliente, SUM(v.totalVenta)
	
	private long cedulaCliente;
	private String nombreCliente;
	private double totalVenta;
	
	
	
	public ReporteVentasDTO() {
		super();
	}

	public ReporteVentasDTO(long cedulaCliente, String nombreCliente, double totalVenta) {
		super();
		this.cedulaCliente = cedulaCliente;
		this.nombreCliente = nombreCliente;
		this.totalVenta = totalVenta;
	}

	public long getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(long cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}
	
	
	
	
}
