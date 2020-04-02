package com.stenda.websocketdemo.data;

import java.util.Set;

public class Comune {
	private String codice;
	private String codiceCatastale;
	private String nome;
	private Set<String> cap;
	private String sigla;
	private Provincia provincia;
	
	public Provincia getProvincia() {
		return provincia;
	}
	
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getCodiceCatastale() {
		return codiceCatastale;
	}
	public void setCodiceCatastale(String codiceCatastale) {
		this.codiceCatastale = codiceCatastale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Set<String> getCap() {
		return cap;
	}
	public void setCap(Set<String> cap) {
		this.cap = cap;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
