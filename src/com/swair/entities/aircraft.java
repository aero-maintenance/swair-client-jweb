package com.swair.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class aircraft {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int ac_id;
	
	@ManyToOne
    @JoinColumn( name = "id_client" )
	
	private int user_id;
	private String modele;
	private String immatriculation;
	private float total_FH;
	private int total_FC;
	private int msn;
	private String statut;
	private Date date_kardex;
	private String remarque;
	public int getAc_id() {
		return ac_id;
	}
	public void setAc_id(int ac_id) {
		this.ac_id = ac_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public float getTotal_FH() {
		return total_FH;
	}
	public void setTotal_FH(float total_FH) {
		this.total_FH = total_FH;
	}
	public int getTotal_FC() {
		return total_FC;
	}
	public void setTotal_FC(int total_FC) {
		this.total_FC = total_FC;
	}
	public int getMsn() {
		return msn;
	}
	public void setMsn(int msn) {
		this.msn = msn;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Date getDate_kardex() {
		return date_kardex;
	}
	public void setDate_kardex(Date date_kardex) {
		this.date_kardex = date_kardex;
	}
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

}
