/**
 * Representa um produto/serviço em uma nota fiscal
 */
public class Produto {
    private String nome;
    private int quantidade;
    private double valorUnitario;
    private String categoria; // Ex: "Eletrônicos", "Alimentos", "Serviços"
    
    // Tabela de impostos por categoria (simplificada)
    private static final double ICMS_PADRAO = 0.18;
    private static final double ICMS_ALIMENTOS = 0.12;
    private static final double ICMS_SERVICOS = 0.05;
    
    private static final double PIS_PADRAO = 0.0165;
    private static final double PIS_SERVICOS = 0.0225;
    
    private static final double COFINS_PADRAO = 0.076;
    private static final double COFINS_SERVICOS = 0.0925;
    
    // Construtor
    public Produto(String nome, int quantidade, double valorUnitario, String categoria) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
    }
    
    // Getters existentes...
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }
    public String getCategoria() { return categoria; }
    
    // Calcula valor total do item
    public double getValorTotal() {
        return quantidade * valorUnitario;
    }
    
    // Calcula ICMS por categoria
    public double getIcms() {
        double aliquota;
        switch (categoria.toLowerCase()) {
            case "alimentos":
                aliquota = ICMS_ALIMENTOS;
                break;
            case "serviços":
            case "servicos":
                aliquota = ICMS_SERVICOS;
                break;
            default:
                aliquota = ICMS_PADRAO;
        }
        return getValorTotal() * aliquota;
    }
    
    // Calcula PIS por categoria
    public double getPis() {
        if (categoria.equalsIgnoreCase("Serviços")) {
            return getValorTotal() * PIS_SERVICOS;
        }
        return getValorTotal() * PIS_PADRAO;
    }
    
    // Calcula COFINS por categoria
    public double getCofins() {
        if (categoria.equalsIgnoreCase("Serviços")) {
            return getValorTotal() * COFINS_SERVICOS;
        }
        return getValorTotal() * COFINS_PADRAO;
    }
    
    // Total de impostos do produto
    public double getTotalImpostos() {
        return getIcms() + getPis() + getCofins();
    }
    
    @Override
    public String toString() {
        return String.format("%s | Qtd: %d | Unit: R$ %.2f | Total: R$ %.2f | ICMS: R$ %.2f | PIS: R$ %.2f | COFINS: R$ %.2f",
                nome, quantidade, valorUnitario, getValorTotal(), getIcms(), getPis(), getCofins());
    }
    
    // Formato para salvar em arquivo
    public String toFileString() {
        return String.format("%s;%d;%.2f;%s", nome, quantidade, valorUnitario, categoria);
    }
}