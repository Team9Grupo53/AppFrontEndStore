package co.edu.mintic.dto;

public class UsuariosDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 287446190869243235L;
	
	private int cedula;
	private String email;
	private String nombre;
	private String password;
	private String usuario;
	
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "{" + "\"cedulaUsuario\":\"" + cedula + "\",\"emailUsuario\": \""
				+ email + "\",\"nombreUsuario\": \"" + nombre + "\",\"password\":\""
				+ password + "\",\"usuario\":\"" + usuario + "\"}";
	}
	
	
	

}
