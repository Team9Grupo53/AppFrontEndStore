package co.edu.mintic.dto;

public class ClienteDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;

	private String id;
	private int cedula;
	private String direccion;
	private String email;
	private String nombre;
	private String telefono;
	private TokenDTO token;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(int cedula, String nombre) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		String res = "{\"documentnumber\":" + cedula + ",\"address\":\"" + direccion + "\",\"email\":\""
				+ email + "\",\"name\":\"" + nombre + "\",\"phonenumber\":\"" + telefono + "\" ";
		System.out.println("id:::>"+id);
		if (id != null)
			res += " ,\"id\":\"" + id + "\" ";
		res += " } ";
		return res;
	}

}
