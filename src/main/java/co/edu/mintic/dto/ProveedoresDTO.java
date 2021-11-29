package co.edu.mintic.dto;

public class ProveedoresDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;
	
	private int nit;
	private String ciudad;
	private String direccion;
	private String nombre;
	private String telefono;
	
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
	@Override
	public String toString() {
		return "{\"nitProveedor\":"+nit+",\"ciudadProveedor\":\""+ciudad+"\",\"direccionProveedor\":\""+direccion+"\",\"nombreProveedor\":\""+nombre+"\",\"telefonoProveedor\":\""+telefono+"\"}";
	}
	
}
