package com.swair.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class utilisateur {
	
	
	private String nom_aeroclub;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long user_id;
	private String adresse;
	private String ville;
	private String code_postale;
	private String password;
	private String email;
	
	public void setnom_aeroclub(String nom_aeroclub) {
		this.nom_aeroclub = nom_aeroclub;
	}
	public String getnom_aeroclub() {
		return nom_aeroclub;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getCode_postale() {
		return code_postale;
	}
	public void setCode_postale(String code_postale) {
		this.code_postale = code_postale;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
