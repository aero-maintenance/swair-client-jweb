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
	private Long ac_id;
	
	@ManyToOne(targetEntity=utilisateur.class)
    @JoinColumn( name = "user_id" )
	
	private Long user_id;
	private String modele;
	private String immatriculation;
	private float total_FH;
	private Long total_FC;
	private Long msn;
	private String statut;
	private Date date_kardex;
	private String remarque;
	
	
	public Long getAc_id() {
		return ac_id;
	}
	public void setAc_id(Long ac_id) {
		this.ac_id = ac_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
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
	public Long getTotal_FC() {
		return total_FC;
	}
	public void setTotal_FC(Long total_FC) {
		this.total_FC = total_FC;
	}
	public Long getMsn() {
		return msn;
	}
	public void setMsn(Long msn) {
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
