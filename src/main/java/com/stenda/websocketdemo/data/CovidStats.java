package com.stenda.websocketdemo.data;

public class CovidStats {

	private int totaleCasi;
	private int totaleGuariti;
	private int totaleDecessi;
	
	
	
	public CovidStats(long totaleCasi, long totaleGuariti, long totaleDecessi) {
		super();
		this.totaleCasi = (int) totaleCasi;
		this.totaleGuariti = (int) totaleGuariti;
		this.totaleDecessi = (int) totaleDecessi;
	}
	public int getTotaleCasi() {
		return totaleCasi;
	}
	public void setTotaleCasi(int totaleCasi) {
		this.totaleCasi = totaleCasi;
	}
	public int getTotaleGuariti() {
		return totaleGuariti;
	}
	public void setTotaleGuariti(int totaleGuariti) {
		this.totaleGuariti = totaleGuariti;
	}
	public int getTotaleDecessi() {
		return totaleDecessi;
	}
	public void setTotaleDecessi(int totaleDecessi) {
		this.totaleDecessi = totaleDecessi;
	}
	
	
}
