package br.edu.cs.poo.ac.utils;

import java.util.regex.Pattern;

public class StringUtils {
    
    public static boolean estaVazia(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static boolean tamanhoExcedido(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }
        if (str == null) {
            return tamanho > 0;
        }
        return str.length() > tamanho;
    }
    
    public static boolean tamanhoMenor(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }
        if (str == null) {
            return tamanho > 0;
        }
        return str.length() < tamanho;
    }
    
    public static boolean emailValido(String email) {
        if (estaVazia(email)) {
            return false;
        }
        
        String emailPattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        return Pattern.matches(emailPattern, email);
    }
    
    public static boolean telefoneValido(String tel) {
        if (estaVazia(tel)) {
            return false;
        }
        
        String telefonePattern8 = "^\\(\\d{2}\\)\\d{8}$";  
        String telefonePattern9 = "^\\(\\d{2}\\)\\d{9}$"; 
        
        return Pattern.matches(telefonePattern8, tel) || Pattern.matches(telefonePattern9, tel);
    }
}