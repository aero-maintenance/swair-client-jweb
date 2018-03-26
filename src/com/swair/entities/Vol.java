package com.swair.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.swair.tool.JodaDateTimeConverter;


@Entity
@Table( name = "vol")
public class Vol implements Serializable{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long flight_id;
	@ManyToOne
    @JoinColumn( name = "ac_id" )
	private Aircraft aircraft;
	@Column( name="date_heure", columnDefinition = "TIMESTAMP" )
    @Converter( name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class )
    @Convert( "dateTimeConverter" )
	private DateTime date_heure;
	@Column( name="FH" )
	private float FH;
	@Column( name="FC" )
	private int FC;
	@Column( name="huile" )
	private int huile;
	@Column( name="carburant" )
	private int carburant;
	@Column( name="remarque" )
	private String remarque;
	
	
	public Long getFlight_id() {
		return flight_id;
	}
	public void setFlight_id(Long flight_id) {
		this.flight_id = flight_id;
	}
	public Aircraft getAircraft() {
		return aircraft;
	}
	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
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
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

}
