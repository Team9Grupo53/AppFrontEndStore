package co.edu.mintic.dto;

import java.io.Serializable;
import java.util.List;


public class VentaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private long codVenta;
	private double ivaVenta;
	private double totalVenta;
	private double valorVenta;
	private List<DetalleVentaDTO> listDetalleVentas;
	private ClienteDTO cliente;
	private UsuariosDTO usuario;
	private TokenDTO token;

	public VentaDTO() {
	}
	
	

	public VentaDTO(long codVenta, List<DetalleVentaDTO> listDetalleVentas, UsuariosDTO usuario) {
		super();
		this.codVenta = codVenta;
		this.listDetalleVentas = listDetalleVentas;
		this.usuario = usuario;
	}



	public long getCodVenta() {
		return this.codVenta;
	}

	public void setCodVenta(long codVenta) {
		this.codVenta = codVenta;
	}

	public double getIvaVenta() {
		return this.ivaVenta;
	}

	public void setIvaVenta(double ivaVenta) {
		this.ivaVenta = ivaVenta;
	}

	public double getTotalVenta() {
		return this.totalVenta;
	}

	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public double getValorVenta() {
		return this.valorVenta;
	}

	public void setValorVenta(double valorVenta) {
		this.valorVenta = valorVenta;
	}

	public List<DetalleVentaDTO> getDetalleVentas() {
		return this.listDetalleVentas;
	}

	public void setDetalleVentas(List<DetalleVentaDTO> listDetalleVentas) {		
		this.listDetalleVentas = listDetalleVentas;
	}

	public ClienteDTO getCliente() {
		return this.cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public UsuariosDTO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuariosDTO usuario) {
		this.usuario = usuario;
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public TokenDTO getToken() {
		return token;
	}



	public void setToken(TokenDTO token) {
		this.token = token;
	}



	@Override
	public String toString() {
		String str =  "  "
				+ "{ "
					+ "\"codigoVenta\":"+codVenta+", "
					+ "\"ivaVenta\":"+ivaVenta+", "
					+ "\"totalVenta\":"+totalVenta+", "
					+ "\"valorVenta\":"+valorVenta+", "
					+ "\"detalleVentas\":[ ";
					for(DetalleVentaDTO detalle: listDetalleVentas) {
						str += detalle.toString()+",";
					}
								
		str = str.substring(0, str.length()-1);
		
		str +=	" ], "
					+ " \"cliente\": "+cliente.toString()
					+ " , \"usuario\": "+ usuario.toString()
				+ " } "
				+ "";
					return str;
	}

	
	
	
}