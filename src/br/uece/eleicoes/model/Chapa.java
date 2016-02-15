package br.uece.eleicoes.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Chapa {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty nome = new SimpleStringProperty();
	private final LongProperty matPresidente = new SimpleLongProperty();
	private final LongProperty matSecretario = new SimpleLongProperty();
	private final LongProperty matTesoureiro = new SimpleLongProperty();
	private final IntegerProperty numVotos = new SimpleIntegerProperty();
	private final FloatProperty porcentagemVotos = new SimpleFloatProperty();
	
	public Chapa() {
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public Long getMatPresidente() {
		return matPresidente.get();
	}

	public void setMatPresidente(Long matPresidente) {
		this.matPresidente.set(matPresidente);;
	}

	public Long getMatSecretario() {
		return matSecretario.get();
	}

	public void setMatSecretario(Long matSecretario) {
		this.matSecretario.set(matSecretario);;
	}

	public Long getMatTesoureiro() {
		return matTesoureiro.get();
	}

	public void setMatTesoureiro(Long matTesoureiro) {
		this.matTesoureiro.set(matTesoureiro);
	}

	public Integer getNumVotos() {
		return numVotos.get();
	}

	public void setNumVotos(Integer numVotos) {
		this.numVotos.set(numVotos);
	}

	public Integer getId() {
		return this.id.get();
	}
	
	public void setId(Integer id) {
		this.id.set(id);
	}

	public Float getPorcentagemVotos() {
		return porcentagemVotos.get();
	}
	
	public void setPorcentagemVotos(Float porcentagemVotos) {
		this.porcentagemVotos.set(porcentagemVotos);
	}

}