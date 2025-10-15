package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.entidades.Equipamento;

public class DadosEquipamento {
    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;
    
    public DadosEquipamento() {
    }
    
    public DadosEquipamento(String serial, String descricao, boolean ehNovo, double valorEstimado) {
        this.serial = serial;
        this.descricao = descricao;
        this.ehNovo = ehNovo;
        this.valorEstimado = valorEstimado;
    }
    
    public DadosEquipamento(Equipamento equipamento) {
        this.serial = equipamento.getSerial();
        this.descricao = equipamento.getDescricao();
        this.ehNovo = equipamento.isEhNovo();
        this.valorEstimado = equipamento.getValorEstimado();
    }
    
    public String getSerial() {
        return serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public boolean isEhNovo() {
        return ehNovo;
    }
    
    public void setEhNovo(boolean ehNovo) {
        this.ehNovo = ehNovo;
    }
    
    public double getValorEstimado() {
        return valorEstimado;
    }
    
    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }
}