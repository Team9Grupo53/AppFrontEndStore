package co.edu.mintic.dto;

import java.io.Serializable;
import java.util.List;



public class TokenDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3290609782978321573L;
	
	
	private String token;
	private String bearer;
	private String nombreUsuario;
	private List<AuthoritiesDTO> authorities;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getBearer() {
		return bearer;
	}
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public List<AuthoritiesDTO> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<AuthoritiesDTO> authorities) {
		this.authorities = authorities;
	}
	@Override
	public String toString() {
		return "TokenDTO [token=" + token + ", bearer=" + bearer + ", nombreUsuario=" + nombreUsuario + ", authorities="
				+ authorities + "]";
	}

	
}
