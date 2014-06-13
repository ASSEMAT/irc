package com.cfranc.irc.server;

public class User {

	private String login;
	private String pwd;
	private String pseudo;

	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @param login
	 * @param pwd
	 * @param pseudo
	 */
	User(String login, String pseudo, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
		this.pseudo = pseudo;

	}
	

	
}
