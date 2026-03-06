/**
 * Utilitário para validação simplificada de CNPJ
 * Implementa validação de formato e dígitos verificadores
 */
public class ValidadorCNPJ {
    
    // Validação completa com dígitos verificadores
    public static boolean validarCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");
        
        // Verifica tamanho
        if (cnpj.length() != 14) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (inválido)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        // Valida dígitos verificadores
        try {
            // Primeiro dígito
            int soma = 0;
            int peso = 2;
            
            // Calcula para os primeiros 12 dígitos
            for (int i = 11; i >= 0; i--) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * peso;
                peso++;
                if (peso == 10) peso = 2;
            }
            
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            
            // Segundo dígito
            soma = 0;
            peso = 2;
            
            for (int i = 12; i >= 0; i--) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * peso;
                peso++;
                if (peso == 10) peso = 2;
            }
            
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            
            // Compara com os dígitos informados
            int digitoInformado1 = Integer.parseInt(String.valueOf(cnpj.charAt(12)));
            int digitoInformado2 = Integer.parseInt(String.valueOf(cnpj.charAt(13)));
            
            return digito1 == digitoInformado1 && digito2 == digitoInformado2;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    // Validação simples (apenas formato)
    public static boolean validarFormato(String cnpj) {
        if (cnpj == null) return false;
        
        // Aceita formatos: 00.000.000/0001-00 ou 00000000000100
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        return cnpjLimpo.length() == 14;
    }
    
    // Formata CNPJ (adiciona máscara)
    public static String formatarCNPJ(String cnpj) {
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        if (cnpjLimpo.length() != 14) return cnpj;
        
        return String.format("%s.%s.%s/%s-%s",
            cnpjLimpo.substring(0, 2),
            cnpjLimpo.substring(2, 5),
            cnpjLimpo.substring(5, 8),
            cnpjLimpo.substring(8, 12),
            cnpjLimpo.substring(12, 14));
    }
}