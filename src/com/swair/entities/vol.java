package com.swair.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

@Entity

public class vol {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id_vol;
	
	
	@ManyToOne(targetEntity=aircraft.class)
    @JoinColumn( name = "id_ac" )
	private int ac_id;
	
	private DateTime date_heure;
	private float FH;
	private int FC;
	private String remarque;
	private int huile;
	private int carburant;
	
	public Long getId_vol() {
		return id_vol;
	}
	public void setId_vol(Long id_vol) {
		this.id_vol = id_vol;
	}
	public int getAc_id() {
		return ac_id;
	}
	public void setAc_id(int ac_id) {
		this.ac_id = ac_id;
	}
	public DateTime getDate_heure() {
		return date_heure;
	}
	public void setDate_heure(DateTime date_heure) {
		this.date_heure = date_heure;
	}
	public float getFH() {
		return FH;
	}
	public void setFH(float fH) {
		FH = fH;
	}
	public int getFC() {
		return FC;
	}
	public void setFC(int fC) {
		FC = fC;
	}
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	public int getHuile() {
		return huile;
	}
	public void setHuile(int huile) {
		this.huile = huile;
	}
	public int getCarburant() {
		return carburant;
	}
	public void setCarburant(int carburant) {
		this.carburant = carburant;
	}
	

}
