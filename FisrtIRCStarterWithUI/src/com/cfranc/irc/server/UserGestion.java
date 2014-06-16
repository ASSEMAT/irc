package com.cfranc.irc.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.cfranc.irc.ConMySql;

public class UserGestion extends User {


	public UserGestion(String login, String pseudo, String pwd, String prenom, String pic) {
		super(login, pseudo, pwd, prenom, pic);
	}
	

	public UserGestion(String login, String pwd) {
		super(login, pwd);
	}

	public UserGestion() {
		super();
	}

	
	public boolean addUser()

	{
		// methode pour créer un USER dans la bdd via ConMySql

		try {
			Connection con=ConMySql.getInst().getConn();

			Statement s=con.createStatement();
			s.setQueryTimeout(30); 
			ResultSet rs=s.executeQuery("SELECT NOM, PSEUDO FROM TUSERS");

			while (rs.next())
			{
				if ((this.getLogin().equals(rs.getString(1)))|| (this.getPseudo().equals(rs.getString(2))))
				{
					JOptionPane.showMessageDialog(null,"L'utilisateur '"+this.getLogin()+"' existe déjà.");
					return false;
				}
			}
			String query = "INSERT INTO tusers (nom, prenom, pseudo, pwd, picture) ";
			query += "VALUES (?, ?, ?, ?, ?)";

			System.out.println("avant");
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,this.getLogin());
			ps.setString(2,this.getPrenom());
			ps.setString(3,this.getPseudo());
			ps.setString(4,this.getPwd());	
			ps.setString(5,this.getPic());	
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null,"User enregistré.");
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void delUser()

	{
		// methode pour sucrer un USER dans la bdd via ConMySql

		try {
			Connection con=ConMySql.getInst().getConn();

			Statement s=con.createStatement();
			s.setQueryTimeout(30); 
			ResultSet rs=s.executeQuery("SELECT idTusers, NOM, PSEUDO FROM TUSERS");

			while (rs.next())
			{
				if ((rs.getString(2)==this.getLogin()) && (rs.getString(3)==this.getPseudo()))
				{
					String query = "DELETE FROM tusers WHERE idTusers = " + rs.getString(1);

					PreparedStatement ps=con.prepareStatement(query);
					ps.executeUpdate();
					ps.close();
					JOptionPane.showMessageDialog(null,"Utilisateur '"+this.getLogin()+"' supprimé.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean existeUser()
	{
		// returne TRUE si le couple LOGIN et PWD est présent dans la BDD
		
		try {
			Connection con=ConMySql.getInst().getConn();

			Statement s=con.createStatement();
			s.setQueryTimeout(30); 
			ResultSet rs=s.executeQuery("SELECT NOM, PWD FROM TUSERS");

			while (rs.next())
			{
				if ((rs.getString(1).equals(this.getLogin())) && (rs.getString(2).equals(this.getPwd())))
				{
					System.out.println(rs.getString(1)+"/"+rs.getString(2)+" : user authentifié.");
					rs.close();
					return true;				
				}
			}
			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
