package co.edu.mintic.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String nick;
	private String password;
	
	
	public LoginDTO(String nick, String password) {
		super();
		this.nick = nick;
		this.password = password;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "{ "
				+ "  \"nick\": \""
				+ nick+"\", "
				+ "  \"password\": \""
				+ password+"\" "
				+ " }";
	}
	
	
	
	
}
