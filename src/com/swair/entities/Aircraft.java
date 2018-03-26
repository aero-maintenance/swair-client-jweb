package com.swair.entities;

import java.io.Serializable;
import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="aircraft")
public class Aircraft implements Serializable {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long ac_id;
	@ManyToOne
    @JoinColumn( name = "user_id" )
	private Utilisateur proprietaire;
	@Column( name= "constructeur")
	private String constructeur;
	@Column( name= "modele" )
	private String modele;
	@Column( name= "immatriculation")
	private String immatriculation;
	@Column( name= "total_FH")
	private Double total_FH;
	@Column( name= "total_FC")
	private Long total_FC;
	@Column( name= "msn")
	private Long msn;
	@Column( name= "statut")
	private String statut;
	@Column( name = "date_Kardex")
	private Date date_Kardex;
	@Column( name = "remarque")
	private String remarque;
		
	public Long getAc_id() {
		return ac_id;
	}
	public void setAc_id(Long ac_id) {
		this.ac_id = ac_id;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public String getConstructeur() {
		return constructeur;
	}
	public void setConstructeur(String constructeur) {
		this.constructeur = constructeur;
	}
	public Utilisateur getProprietaire() {
		return proprietaire;
	}
	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public Double getTotal_FH() {
		return total_FH;
	}
	public void setTotal_FH(Double total_FH) {
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
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	public Date getDate_Kardex() {
		return date_Kardex;
	}
	public void setDate_Kardex(Date date_Kardex) {
		this.date_Kardex = date_Kardex;
	}
	
}
