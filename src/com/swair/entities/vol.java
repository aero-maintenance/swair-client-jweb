package com.swair.entities;

import javax.persistence.Entity;

import org.joda.time.DateTime;

@Entity

public class vol {
	
	private int id_vol;
	private int ac_id;
	private DateTime date_heure;
	private float FH;
	private int FC;
	private String remarque;
	public int getId_vol() {
		return id_vol;
	}
	public void setId_vol(int id_vol) {
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
	

}
