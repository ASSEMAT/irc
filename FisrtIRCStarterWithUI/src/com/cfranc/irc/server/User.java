package com.cfranc.irc.server;
//DASS

public class User {

	private String login;
	private String pwd;
	private String pseudo;
	private String prenom;
	private String pic;
	

	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
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
	/**
	 * @param login
	 * @param pwd
	 * @param pseudo
	 * @param prenom
	 * @param pic
	 */
	User(String login, String pwd, String pseudo, String prenom, String pic) {
		super();
		this.login = login;
		this.pwd = pwd;
		this.pseudo = pseudo;
		this.prenom = prenom;
		this.pic = pic;
	}

	User(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
	}
	
	User(String login, String pseudo, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
		this.pseudo = pseudo;
	}

	User() {
		super();
	}

	
}
