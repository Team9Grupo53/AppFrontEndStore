package co.edu.mintic.dto;

import java.io.Serializable;

public class AuthoritiesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3553294466672449908L;
	
	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "{ "
				+ " \"authority\": \"+"
				+ authority+"\" "
				+ " }";
	}

}
