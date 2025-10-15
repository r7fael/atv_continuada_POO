package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente implements Serializable {
	private String cpfCnpj;
	private String nome;
	private Contato contato;
	private LocalDate dataCadastro;
	
	public Cliente (String cpfCnpj, String nome, Contato contato, LocalDate dataCadastro) {
		this.cpfCnpj = cpfCnpj;
		this.nome = nome;
		this.contato = contato;
		this.dataCadastro = dataCadastro;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Contato getContato() {
		return contato;
	}
	
	public void setContato (Contato contato) {
		this.contato = contato;
	}
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	
	public int getIdadeCadastro() {
		return LocalDate.now().getYear() - this.dataCadastro.getYear();
	}
}
