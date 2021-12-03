package co.edu.mintic.dto;

public class ProveedoresDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;

	private String id;
	private int nit;
	private String ciudad;
	private String direccion;
	private String nombre;
	private String telefono;
	private TokenDTO token;

	public int getNit() {
		return nit;
	}

	public void setNit(int nit) {
		this.nit = nit;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
		String res =  " {\"nit\":" + nit + ",\"city\":\"" + ciudad + "\",\"address\":\""
				+ direccion + "\",\"name\":\"" + nombre + "\",\"phoneNumber\":\"" + telefono + "\" ";
		if (id != null)
			res += "   ,\"id\":\"" + id + "\" ";
		res += " } ";
		return res;
	}

}
