package co.edu.mintic.dto;

public class ProductosDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;

	private String id;
	private int codigoProducto;
	private int nitProveedor;
	private double ivaCompra;
	private String nombreProducto;
	private double precioCompra;
	private double precioVenta;
	private TokenDTO token;

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
		String res = " {\"productCode\":\"" + codigoProducto + "\",\"iva_item\":\"" + ivaCompra + "\",\"productName\":\""
				+ nombreProducto + "\",\"pricePurchase\":\"" + precioCompra + "\",\"priceToBuy\":\"" + precioVenta
				+ "\",\"nitSupplier\":\"" + nitProveedor + "\" ";
		if (id != null)
			res += "   ,\"id\":\"" + id + "\" ";
		res += " }";
		return res;
	}

}
