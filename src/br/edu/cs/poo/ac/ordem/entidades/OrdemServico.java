package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class OrdemServico implements Serializable {
	private Cliente cliente;
	private PrecoBase precoBase;
	private Notebook notebook;
	private Desktop desktop;
	private LocalDateTime dataHoraAbertura;
	private int prazoEmDias;
	private double valor;

	public LocalDate getDataEstimadaEntrega() {
		return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
	}

	public String getNumero() {
		String tipoEquipamento = "";

		if (notebook != null) {
			tipoEquipamento = notebook.getIdTipo();
		} else if (desktop != null) {
			tipoEquipamento = desktop.getIdTipo();
		}

		int ano = dataHoraAbertura.getYear();
		int mes = dataHoraAbertura.getMonthValue();
		int dia = dataHoraAbertura.getDayOfMonth();
		int hora = dataHoraAbertura.getHour();
		int minuto = dataHoraAbertura.getMinute();

		String cpfCnpj = cliente.getCpfCnpj();

		if (cpfCnpj.length() == 14) { 
            return String.format("%s%04d%02d%02d%02d%02d%s", 
                    tipoEquipamento, ano, mes, dia, hora, minuto, cpfCnpj);
        } else {
            return String.format("%02d%04d%02d%02d%02d000%s", 
                    mes, ano, dia, hora, minuto, cpfCnpj);
        }
	}
}
