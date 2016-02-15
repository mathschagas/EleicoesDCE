package br.uece.eleicoes.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Aluno {
	private final StringProperty nome = new SimpleStringProperty();
	private final LongProperty matricula = new SimpleLongProperty();
	private final StringProperty curso = new SimpleStringProperty();
	private final StringProperty senha = new SimpleStringProperty();
	private final IntegerProperty idChapaVotada = new SimpleIntegerProperty();
	
	public Aluno() {}

	public String getNome() {
		return nome.get();
	}
	
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	
	public Long getMatricula() {
		return matricula.get();
	}
	
	public void setMatricula(Long matricula) {
		this.matricula.set(matricula);
	}

	public String getCurso() {
		return curso.get();
	}
	
	public void setCurso(String curso) {
		this.curso.set(curso);
	}
	
	public String getSenha() {
		return senha.get();
	}
	
	public void setSenha(String senha) {
		this.senha.set(senha);
	}

	public Integer getIdChapaVotada() {
		return idChapaVotada.get();
	}

	public void setIdChapaVotada(Integer idChapaVotada) {
		this.idChapaVotada.set(idChapaVotada);
	}
	
}
