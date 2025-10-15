package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Equipamento implements Serializable {
	private String serial;
	private String descricao;
	private boolean ehNovo;
	private double valorEstimado;
	
}
