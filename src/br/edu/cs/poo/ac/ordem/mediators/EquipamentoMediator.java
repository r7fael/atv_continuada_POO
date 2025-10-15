package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class EquipamentoMediator {
    private static EquipamentoMediator instancia;
    private DesktopDAO desktopDAO;
    private NotebookDAO notebookDAO;
    
    private EquipamentoMediator() {
        this.desktopDAO = new DesktopDAO();
        this.notebookDAO = new NotebookDAO();
    }
    
    public static EquipamentoMediator getInstancia() {
        if (instancia == null) {
            instancia = new EquipamentoMediator();
        }
        return instancia;
    }
    
    public ResultadoMediator incluirDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(resultadoValidacao.isValidado(), false, resultadoValidacao.getMensagensErro());
        }
        
        boolean operacaoRealizada = desktopDAO.incluir(desk);
        ListaString mensagens = new ListaString();
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do desktop já existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public ResultadoMediator alterarDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(resultadoValidacao.isValidado(), false, resultadoValidacao.getMensagensErro());
        }
        
        boolean operacaoRealizada = desktopDAO.alterar(desk);
        ListaString mensagens = new ListaString();
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do desktop não existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public ResultadoMediator incluirNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(resultadoValidacao.isValidado(), false, resultadoValidacao.getMensagensErro());
        }
        
        boolean operacaoRealizada = notebookDAO.incluir(note);
        ListaString mensagens = new ListaString();
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do notebook já existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public ResultadoMediator alterarNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(resultadoValidacao.isValidado(), false, resultadoValidacao.getMensagensErro());
        }
        
        boolean operacaoRealizada = notebookDAO.alterar(note);
        ListaString mensagens = new ListaString();
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do notebook não existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public ResultadoMediator excluirNotebook(String idTipoSerial) {
        ListaString mensagens = new ListaString();
        
        if (StringUtils.estaVazia(idTipoSerial)) {
            mensagens.adicionar("Id do tipo + serial do notebook não informado");
            return new ResultadoMediator(false, false, mensagens);
        }
        
        boolean operacaoRealizada = notebookDAO.excluir(idTipoSerial);
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do notebook não existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public ResultadoMediator excluirDesktop(String idTipoSerial) {
        ListaString mensagens = new ListaString();
        
        if (StringUtils.estaVazia(idTipoSerial)) {
            mensagens.adicionar("Id do tipo + serial do desktop não informado");
            return new ResultadoMediator(false, false, mensagens);
        }
        
        boolean operacaoRealizada = desktopDAO.excluir(idTipoSerial);
        if (!operacaoRealizada) {
            mensagens.adicionar("Serial do desktop não existente");
        }
        
        return new ResultadoMediator(true, operacaoRealizada, mensagens);
    }
    
    public Notebook buscarNotebook(String idTipoSerial) {
        return notebookDAO.buscar(idTipoSerial);
    }
    
    public Desktop buscarDesktop(String idTipoSerial) {
        return desktopDAO.buscar(idTipoSerial);
    }
    
    public ResultadoMediator validarDesktop(Desktop desk) {
        if (desk == null) {
            ListaString mensagens = new ListaString();
            mensagens.adicionar("Desktop não informado");
            return new ResultadoMediator(false, false, mensagens);
        }
        
        DadosEquipamento dadosEquip = new DadosEquipamento(desk);
        return validar(dadosEquip);
    }
    
    public ResultadoMediator validarNotebook(Notebook note) {
        if (note == null) {
            ListaString mensagens = new ListaString();
            mensagens.adicionar("Notebook não informado");
            return new ResultadoMediator(false, false, mensagens);
        }
        
        DadosEquipamento dadosEquip = new DadosEquipamento(note);
        return validar(dadosEquip);
    }
    
    public ResultadoMediator validar(DadosEquipamento equip) {
        ListaString mensagensErro = new ListaString();
        
        if (equip == null) {
            mensagensErro.adicionar("Dados básicos do equipamento não informados");
            return new ResultadoMediator(false, false, mensagensErro);
        }
        
        if (StringUtils.estaVazia(equip.getDescricao())) {
            mensagensErro.adicionar("Descrição não informada");
        } else {
            if (equip.getDescricao().length() > 150) {
                mensagensErro.adicionar("Descrição tem mais de 150 caracteres");
            } else if (equip.getDescricao().length() < 10) {
                mensagensErro.adicionar("Descrição tem menos de 10 caracteres");
            }
        }
        
        if (StringUtils.estaVazia(equip.getSerial())) {
            mensagensErro.adicionar("Serial não informado");
        }
        
        if (equip.getValorEstimado() <= 0) {
            mensagensErro.adicionar("Valor estimado menor ou igual a zero");
        }
        
        boolean validado = mensagensErro.tamanho() == 0;
        return new ResultadoMediator(validado, false, mensagensErro);
    }
}