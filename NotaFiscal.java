import java.util.*;

/**
 * Representa uma nota fiscal completa com vários produtos
 */
public class NotaFiscal {
    private String numeroNota;
    private String dataEmissao;
    private String cliente;
    private String cnpjCliente;
    private List<Produto> produtos;
    private boolean cnpjValido;
    
    // Construtor com validação
    public NotaFiscal(String numeroNota, String dataEmissao, String cliente, String cnpjCliente) {
        this.numeroNota = numeroNota;
        this.dataEmissao = dataEmissao;
        this.cliente = cliente;
        setCnpjCliente(cnpjCliente); // Usa setter com validação
        this.produtos = new ArrayList<>();
    }
    
    // Setter com validação
    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
        this.cnpjValido = ValidadorCNPJ.validarCNPJ(cnpjCliente);
    }
    
    // Getters existentes...
    public String getNumeroNota() { return numeroNota; }
    public String getDataEmissao() { return dataEmissao; }
    public String getCliente() { return cliente; }
    public String getCnpjCliente() { return cnpjCliente; }
    public List<Produto> getProdutos() { return produtos; }
    public boolean isCnpjValido() { return cnpjValido; }
    
    // Adicionar produto à nota
    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }
    
    // Calcula valor total da nota
    public double getValorTotalNota() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getValorTotal();
        }
        return total;
    }
    
    // Calcula total de ICMS
    public double getTotalIcms() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getIcms();
        }
        return total;
    }
    
    // Calcula total de PIS
    public double getTotalPis() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPis();
        }
        return total;
    }
    
    // Calcula total de COFINS
    public double getTotalCofins() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getCofins();
        }
        return total;
    }
    
    // Total de impostos da nota
    public double getTotalImpostos() {
        return getTotalIcms() + getTotalPis() + getTotalCofins();
    }
    
    // Resumo da nota
    public String getResumo() {
        String statusCNPJ = cnpjValido ? "✅ CNPJ válido" : "❌ CNPJ inválido";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Nota %s - %s - %s\n", numeroNota, dataEmissao, cliente));
        sb.append(String.format("CNPJ: %s %s\n", ValidadorCNPJ.formatarCNPJ(cnpjCliente), statusCNPJ));
        sb.append(String.format("Itens: %d\n", produtos.size()));
        sb.append(String.format("Total: R$ %.2f\n", getValorTotalNota()));
        sb.append(String.format("Impostos: R$ %.2f\n", getTotalImpostos()));
        return sb.toString();
    }
    
    @Override
    public String toString() {
        String statusCNPJ = cnpjValido ? "✅" : "❌";
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append(String.format("NOTA FISCAL: %s\n", numeroNota));
        sb.append(String.format("Data: %s\n", dataEmissao));
        sb.append(String.format("Cliente: %s\n", cliente));
        sb.append(String.format("CNPJ: %s %s\n", ValidadorCNPJ.formatarCNPJ(cnpjCliente), statusCNPJ));
        sb.append("=====================================\n");
        sb.append("ITENS:\n");
        for (Produto p : produtos) {
            sb.append("  - ").append(p.toString()).append("\n");
        }
        sb.append("=====================================\n");
        sb.append(String.format("TOTAL DA NOTA: R$ %.2f\n", getValorTotalNota()));
        sb.append(String.format("ICMS: R$ %.2f\n", getTotalIcms()));
        sb.append(String.format("PIS: R$ %.2f\n", getTotalPis()));
        sb.append(String.format("COFINS: R$ %.2f\n", getTotalCofins()));
        sb.append(String.format("TOTAL IMPOSTOS: R$ %.2f (%.1f%%)\n", 
            getTotalImpostos(), (getTotalImpostos()/getValorTotalNota())*100));
        sb.append("=====================================");
        return sb.toString();
    }
}