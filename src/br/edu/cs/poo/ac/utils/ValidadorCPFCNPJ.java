package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {
    
    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.trim().isEmpty()) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
        
        cpfCnpj = cpfCnpj.trim();
        
        if (!cpfCnpj.matches("\\d+")) {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
        
        if (isCPF(cpfCnpj)) {
            ErroValidacaoCPFCNPJ erro = validarCPF(cpfCnpj);
            if (erro != null) {
                return new ResultadoValidacaoCPFCNPJ(false, false, erro);
            }
            return new ResultadoValidacaoCPFCNPJ(true, false, null);
        } else if (isCNPJ(cpfCnpj)) {
            ErroValidacaoCPFCNPJ erro = validarCNPJ(cpfCnpj);
            if (erro != null) {
                return new ResultadoValidacaoCPFCNPJ(false, false, erro);
            }
            return new ResultadoValidacaoCPFCNPJ(false, true, null);
        } else {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
    }
    
    public static boolean isCPF(String valor) {
        return valor != null && valor.length() == 11;
    }
    
    public static boolean isCNPJ(String valor) {
        return valor != null && valor.length() == 14;
    }
    
    public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        cpf = cpf.trim();
        
        if (cpf.length() != 11) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!cpf.matches("\\d{11}")) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!isDigitoVerificadorValidoCPF(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        
        return null;
    }
    
    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        cnpj = cnpj.trim();
        
        if (cnpj.length() != 14) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!cnpj.matches("\\d{14}")) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }
        
        if (!isDigitoVerificadorValidoCNPJ(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }
        
        return null;
    }
    
    private static boolean isDigitoVerificadorValidoCPF(String cpf) {
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }
        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }
        
        return Character.getNumericValue(cpf.charAt(9)) == primeiroDigito && 
               Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }
    
    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
        int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesosPrimeiro[i];
        }
        int primeiroDigito = soma % 11;
        if (primeiroDigito < 2) {
            primeiroDigito = 0;
        } else {
            primeiroDigito = 11 - primeiroDigito;
        }
        
        int[] pesosSegundo = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesosSegundo[i];
        }
        int segundoDigito = soma % 11;
        if (segundoDigito < 2) {
            segundoDigito = 0;
        } else {
            segundoDigito = 11 - segundoDigito;
        }
        
        return Character.getNumericValue(cnpj.charAt(12)) == primeiroDigito && 
               Character.getNumericValue(cnpj.charAt(13)) == segundoDigito;
    }
}