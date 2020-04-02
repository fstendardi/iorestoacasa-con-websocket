package com.stenda.websocketdemo.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CovidData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String codiceProvincia;

	private String codiceRegione;

	private String stato;

	private String denominazioneRegione;

	private String denominazioneProvincia;

	private String siglaProvincia;

	private double lat;

	@JsonProperty("long")
	private double lon;

	private int totaleCasi;
	private int totaleDecessi;
	private int totaleGuariti;

	@LastModifiedBy
	private String modificatoDa;

	@CreatedBy
	private String creatoDa;

	@CreatedDate
	private Date dataCreazione;

	@LastModifiedDate
	private Date dataModifica;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceRegione() {
		return codiceRegione;
	}

	public void setCodiceRegione(String codiceRegione) {
		this.codiceRegione = codiceRegione;
	}

	public String getCodiceProvincia() {
		return codiceProvincia;
	}

	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getDenominazioneRegione() {
		return denominazioneRegione;
	}

	public void setDenominazioneRegione(String denominazioneRegione) {
		this.denominazioneRegione = denominazioneRegione;
	}

	public String getDenominazioneProvincia() {
		return denominazioneProvincia;
	}

	public void setDenominazioneProvincia(String denominazioneProvincia) {
		this.denominazioneProvincia = denominazioneProvincia;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getTotaleCasi() {
		return totaleCasi;
	}

	public void setTotaleCasi(int totaleCasi) {
		this.totaleCasi = totaleCasi;
	}

	public int getTotaleDecessi() {
		return totaleDecessi;
	}

	public void setTotaleDecessi(int totaleDecessi) {
		this.totaleDecessi = totaleDecessi;
	}

	public String getModificatoDa() {
		return modificatoDa;
	}

	public void setModificatoDa(String modificatoDa) {
		this.modificatoDa = modificatoDa;
	}

	public String getCreatoDa() {
		return creatoDa;
	}

	public void setCreatoDa(String creatoDa) {
		this.creatoDa = creatoDa;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getTotaleGuariti() {
		return totaleGuariti;
	}

	public void setTotaleGuariti(int totaleGuariti) {
		this.totaleGuariti = totaleGuariti;
	}

}
