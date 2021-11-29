package co.edu.mintic.dto;

public class ClienteDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;
	
	private int cedula;
	private String direccion;
	private String email;
	private String nombre;
	private String telefono;
	
	
	
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
	@Override
	public String toString() {
		return "{\"cedulaCliente\":"+cedula+",\"direccionCliente\":\""+direccion+"\",\"emailCliente\":\""+email+"\",\"nombreCliente\":\""+nombre+"\",\"telefonoCliente\":\""+telefono+"\"}";
	}
	
	
}

