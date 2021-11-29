package co.edu.mintic.dto;

public class ProductosDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;
	
	private int codigoProducto;
	private int  nitProveedor;
	private double ivaCompra;
	private String nombreProducto;
	private double precioCompra;
	private double precioVenta;
	
	public int getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public int getNitProveedor() {
		return nitProveedor;
	}
	public void setNitProveedor(int nitProveedor) {
		this.nitProveedor = nitProveedor;
	}
	public double getIvaCompra() {
		return ivaCompra;
	}
	public void setIvaCompra(double ivaCompra) {
		this.ivaCompra = ivaCompra;
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
	
	@Override
	public String toString() {
		return "{\"codigoProducto\":"+codigoProducto+",\"ivaCompra\":"+ivaCompra+",\"nombreProducto\":\""+nombreProducto+"\",\"precioCompra\":"+precioCompra+",\"precioVenta\":"+precioVenta+",\"proveedor\":{\"nitProveedor\":"+nitProveedor+"}}";
	}
	
	
}
