package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.ErroValidacaoCPFCNPJ;

public class ClienteMediator {
    private static ClienteMediator instancia;
    private ClienteDAO clienteDAO;
    
    private ClienteMediator() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public static ClienteMediator getInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }
    
    public ResultadoMediator incluir(Cliente cliente) {
        ResultadoMediator validacao = validar(cliente);
        
        if (!validacao.isValidado()) {
            return validacao;
        }
        
        Cliente clienteExistente = clienteDAO.buscar(cliente.getCpfCnpj());
        if (clienteExistente != null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ já cadastrado");
            return new ResultadoMediator(false, false, erros);
        }
        
        boolean sucesso = clienteDAO.incluir(cliente);
        return new ResultadoMediator(true, sucesso, new ListaString());
    }
    
    public ResultadoMediator alterar(Cliente cliente) {
        ResultadoMediator validacao = validar(cliente);
        
        if (!validacao.isValidado()) {
            return validacao;
        }
        
        Cliente clienteExistente = clienteDAO.buscar(cliente.getCpfCnpj());
        if (clienteExistente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(false, false, erros);
        }
        
        boolean sucesso = clienteDAO.alterar(cliente);
        return new ResultadoMediator(true, sucesso, new ListaString());
    }
    
    public ResultadoMediator excluir(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ não informado");
            return new ResultadoMediator(false, false, erros);
        }
        
        Cliente clienteExistente = clienteDAO.buscar(cpfCnpj);
        if (clienteExistente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(false, false, erros);
        }
        
        boolean sucesso = clienteDAO.excluir(cpfCnpj);
        return new ResultadoMediator(true, sucesso, new ListaString());
    }
    
    public Cliente buscar(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            return null;
        }
        return clienteDAO.buscar(cpfCnpj);
    }
    
    public ResultadoMediator validar(Cliente cliente) {
        ListaString erros = new ListaString();
        
        if (cliente == null) {
            erros.adicionar("Cliente não informado");
            return new ResultadoMediator(false, false, erros);
        }
        
        validarCpfCnpj(cliente.getCpfCnpj(), erros);
        
        validarNome(cliente.getNome(), erros);
        
        if (cliente.getContato() == null) {
            erros.adicionar("Contato não informado");
        }
        
        validarDataCadastro(cliente.getDataCadastro(), erros);
        
        if (cliente.getContato() != null) {
            validarContatoEspecifico(cliente.getContato(), erros);
        }
        
        boolean validado = erros.tamanho() == 0;
        return new ResultadoMediator(validado, false, erros);
    }
    
    private void validarCpfCnpj(String cpfCnpj, ListaString erros) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            erros.adicionar("CPF/CNPJ não informado");
            return;
        }
        
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ(cpfCnpj);
        ErroValidacaoCPFCNPJ erro = resultado.getErroValidacao();
        
        if (erro != null) {
            switch (erro) {
                case CPF_CNPJ_NAO_E_CPF_NEM_CNPJ:
                    erros.adicionar("Não é CPF nem CNJP");
                    break;
                case CPF_CNPJ_COM_DV_INVALIDO:
                    erros.adicionar("CPF ou CNPJ com dígito verificador inválido");
                    break;
                default:
                    erros.adicionar(erro.getMensagem());
                    break;
            }
        }
    }
    
    private void validarNome(String nome, ListaString erros) {
        if (StringUtils.estaVazia(nome)) {
            erros.adicionar("Nome não informado");
        } else if (StringUtils.tamanhoExcedido(nome, 50)) {
            erros.adicionar("Nome tem mais de 50 caracteres");
        }
    }
    
    private void validarContatoEspecifico(Contato contato, ListaString erros) {
        String email = contato.getEmail();
        String celular = contato.getCelular();
        boolean ehZap = contato.isEhZap();
        
        boolean emailVazio = StringUtils.estaVazia(email);
        boolean celularVazio = StringUtils.estaVazia(celular);
        
        if (emailVazio && celularVazio) {
            erros.adicionar("Celular e e-mail não foram informados");
            return;
        }
        
        if (ehZap && celularVazio) {
            erros.adicionar("Celular não informado e indicador de zap ativo");
            return;
        }
        
        if (!celularVazio && !StringUtils.telefoneValido(celular)) {
            erros.adicionar("Celular está em um formato inválido");
        }
        
        if (!emailVazio && !StringUtils.emailValido(email)) {
            erros.adicionar("E-mail está em um formato inválido");
        }
    }
    
    private void validarDataCadastro(LocalDate dataCadastro, ListaString erros) {
        if (dataCadastro == null) {
            erros.adicionar("Data do cadastro não informada");
        } else if (dataCadastro.isAfter(LocalDate.now())) {
            erros.adicionar("Data do cadastro não pode ser posterior à data atual");
        }
    }
}