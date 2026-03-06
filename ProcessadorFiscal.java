import java.util.*;
import java.io.*;

/**
 * Classe responsável por processar arquivos de notas fiscais
 */
public class ProcessadorFiscal {
    private List<NotaFiscal> notas;
    private static final String ARQUIVO_DADOS = "notas_fiscais.txt";
    private static final String ARQUIVO_RELATORIO = "relatorio_fiscal.txt";
    private static final String ARQUIVO_CSV = "dados_exportados.csv";
    
    public ProcessadorFiscal() {
        this.notas = new ArrayList<>();
        carregarNotas();
    }
    
    // Carregar notas do arquivo (mesmo código anterior)
    private void carregarNotas() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            criarDadosExemplo();
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DADOS))) {
            String linha;
            NotaFiscal notaAtual = null;
            
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                
                String[] partes = linha.split(";");
                
                if (partes[0].equals("NOTA")) {
                    // NOTA;numero;data;cliente;cnpj
                    notaAtual = new NotaFiscal(partes[1], partes[2], partes[3], partes[4]);
                    notas.add(notaAtual);
                } 
                else if (partes.length == 4 && notaAtual != null) {
                    Produto p = new Produto(
                        partes[0],
                        Integer.parseInt(partes[1]),
                        Double.parseDouble(partes[2]),
                        partes[3]
                    );
                    notaAtual.adicionarProduto(p);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao carregar arquivo: " + e.getMessage());
        }
    }
    
    // Criar dados de exemplo com CNPJs variados
    private void criarDadosExemplo() {
        System.out.println("📁 Criando arquivo de exemplo com notas fiscais...");
        
        // Nota 1 - CNPJ válido
        NotaFiscal n1 = new NotaFiscal("NF-001", "01/03/2026", "Empresa ABC Ltda", "12.345.678/0001-90");
        n1.adicionarProduto(new Produto("Notebook", 2, 3500.00, "Eletrônicos"));
        n1.adicionarProduto(new Produto("Mouse", 5, 50.00, "Eletrônicos"));
        n1.adicionarProduto(new Produto("Serviço de Manutenção", 1, 800.00, "Serviços"));
        notas.add(n1);
        
        // Nota 2 - CNPJ inválido (para teste)
        NotaFiscal n2 = new NotaFiscal("NF-002", "02/03/2026", "Comércio de Alimentos ME", "11.111.111/0001-11");
        n2.adicionarProduto(new Produto("Arroz", 100, 25.00, "Alimentos"));
        n2.adicionarProduto(new Produto("Feijão", 80, 12.00, "Alimentos"));
        n2.adicionarProduto(new Produto("Óleo", 50, 8.00, "Alimentos"));
        notas.add(n2);
        
        // Nota 3 - CNPJ válido
        NotaFiscal n3 = new NotaFiscal("NF-003", "03/03/2026", "Consultoria Fiscal SS", "45.678.901/0001-23");
        n3.adicionarProduto(new Produto("Consultoria Tributária", 40, 250.00, "Serviços"));
        n3.adicionarProduto(new Produto("Treinamento", 20, 180.00, "Serviços"));
        notas.add(n3);
        
        salvarNotas();
    }
    
    // Salvar todas as notas no arquivo
    public void salvarNotas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (NotaFiscal nota : notas) {
                writer.printf("NOTA;%s;%s;%s;%s\n", 
                    nota.getNumeroNota(), nota.getDataEmissao(), 
                    nota.getCliente(), nota.getCnpjCliente());
                
                for (Produto p : nota.getProdutos()) {
                    writer.println(p.toFileString());
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    // Listar todas as notas
    public void listarNotas() {
        if (notas.isEmpty()) {
            System.out.println("📭 Nenhuma nota fiscal cadastrada.");
            return;
        }
        
        System.out.println("\n📋 NOTAS FISCAIS CADASTRADAS");
        System.out.println("========================================");
        for (int i = 0; i < notas.size(); i++) {
            System.out.println((i + 1) + ". " + notas.get(i).getResumo());
        }
    }
    
    // Buscar nota por número
    public void buscarNota(String numero) {
        for (NotaFiscal nota : notas) {
            if (nota.getNumeroNota().equalsIgnoreCase(numero)) {
                System.out.println(nota);
                return;
            }
        }
        System.out.println("❌ Nota " + numero + " não encontrada.");
    }
    
    // NOVO: Filtrar por cliente
    public void filtrarPorCliente(String termo) {
        List<NotaFiscal> resultados = new ArrayList<>();
        
        for (NotaFiscal nota : notas) {
            if (nota.getCliente().toLowerCase().contains(termo.toLowerCase())) {
                resultados.add(nota);
            }
        }
        
        if (resultados.isEmpty()) {
            System.out.println("🔍 Nenhuma nota encontrada para: " + termo);
        } else {
            System.out.println("\n🔍 NOTAS DE: " + termo.toUpperCase());
            System.out.println("========================================");
            for (NotaFiscal nota : resultados) {
                System.out.println(nota.getResumo());
            }
        }
    }
    
    // NOVO: Filtrar por valor mínimo
    public void filtrarPorValorMinimo(double valorMinimo) {
        List<NotaFiscal> resultados = new ArrayList<>();
        
        for (NotaFiscal nota : notas) {
            if (nota.getValorTotalNota() >= valorMinimo) {
                resultados.add(nota);
            }
        }
        
        if (resultados.isEmpty()) {
            System.out.printf("🔍 Nenhuma nota com valor >= R$ %.2f\n", valorMinimo);
        } else {
            System.out.printf("\n🔍 NOTAS COM VALOR >= R$ %.2f\n", valorMinimo);
            System.out.println("========================================");
            for (NotaFiscal nota : resultados) {
                System.out.printf("%s - R$ %.2f\n", 
                    nota.getNumeroNota(), nota.getValorTotalNota());
            }
        }
    }
    
    // NOVO: Gráfico ASCII de vendas por categoria
    public void graficoVendasPorCategoria() {
        Map<String, Double> totaisPorCategoria = new HashMap<>();
        
        for (NotaFiscal nota : notas) {
            for (Produto p : nota.getProdutos()) {
                String cat = p.getCategoria();
                totaisPorCategoria.put(cat, 
                    totaisPorCategoria.getOrDefault(cat, 0.0) + p.getValorTotal());
            }
        }
        
        if (totaisPorCategoria.isEmpty()) {
            System.out.println("📭 Sem dados para gerar gráfico.");
            return;
        }
        
        // Encontra o maior valor para escala
        double maxValor = Collections.max(totaisPorCategoria.values());
        int escala = (int) (maxValor / 50); // 50 caracteres = 100% da maior barra
        
        System.out.println("\n📊 GRÁFICO DE VENDAS POR CATEGORIA");
        System.out.println("========================================");
        
        // Ordena categorias para exibição consistente
        List<String> categorias = new ArrayList<>(totaisPorCategoria.keySet());
        Collections.sort(categorias);
        
        for (String cat : categorias) {
            double valor = totaisPorCategoria.get(cat);
            int tamanhoBarra = (int) (valor / escala);
            
            // Limita tamanho máximo
            tamanhoBarra = Math.min(tamanhoBarra, 50);
            
            String barra = "█".repeat(tamanhoBarra);
            System.out.printf("%-12s | %-50s R$ %,.2f\n", cat, barra, valor);
        }
        System.out.println("========================================");
        System.out.println("Cada █ representa R$ " + String.format("%,d", escala));
    }
    
    // NOVO: Exportar para CSV
    public void exportarParaCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CSV))) {
            // Cabeçalho do CSV
            writer.println("Número Nota;Data;Cliente;CNPJ;Produto;Quantidade;Valor Unitário;Categoria;Valor Total;ICMS;PIS;COFINS");
            
            for (NotaFiscal nota : notas) {
                for (Produto p : nota.getProdutos()) {
                    writer.printf("%s;%s;%s;%s;%s;%d;%.2f;%s;%.2f;%.2f;%.2f;%.2f\n",
                        nota.getNumeroNota(),
                        nota.getDataEmissao(),
                        nota.getCliente(),
                        nota.getCnpjCliente(),
                        p.getNome(),
                        p.getQuantidade(),
                        p.getValorUnitario(),
                        p.getCategoria(),
                        p.getValorTotal(),
                        p.getIcms(),
                        p.getPis(),
                        p.getCofins()
                    );
                }
            }
            
            // Linha de totais
            writer.println();
            writer.printf("TOTAIS GERAIS;;;;;;;%.2f;%.2f;%.2f;%.2f\n",
                getTotalVendas(), getTotalIcms(), getTotalPis(), getTotalCofins());
            
            System.out.println("✅ Arquivo CSV gerado: " + ARQUIVO_CSV);
            System.out.println("   Abra no Excel ou Google Sheets!");
            
        } catch (IOException e) {
            System.out.println("❌ Erro ao exportar CSV: " + e.getMessage());
        }
    }
    
    // Métodos auxiliares para totais
    private double getTotalVendas() {
        double total = 0;
        for (NotaFiscal nota : notas) {
            total += nota.getValorTotalNota();
        }
        return total;
    }
    
    private double getTotalIcms() {
        double total = 0;
        for (NotaFiscal nota : notas) {
            total += nota.getTotalIcms();
        }
        return total;
    }
    
    private double getTotalPis() {
        double total = 0;
        for (NotaFiscal nota : notas) {
            total += nota.getTotalPis();
        }
        return total;
    }
    
    private double getTotalCofins() {
        double total = 0;
        for (NotaFiscal nota : notas) {
            total += nota.getTotalCofins();
        }
        return total;
    }
    
    // Relatório por período
    public void relatorioPorPeriodo(String mesAno) {
        double totalVendas = 0;
        double totalIcms = 0;
        double totalPis = 0;
        double totalCofins = 0;
        int countNotas = 0;
        
        for (NotaFiscal nota : notas) {
            if (nota.getDataEmissao().contains(mesAno)) {
                totalVendas += nota.getValorTotalNota();
                totalIcms += nota.getTotalIcms();
                totalPis += nota.getTotalPis();
                totalCofins += nota.getTotalCofins();
                countNotas++;
            }
        }
        
        System.out.println("\n📊 RELATÓRIO FISCAL - " + mesAno);
        System.out.println("========================================");
        System.out.println("Notas emitidas: " + countNotas);
        System.out.printf("Total vendido: R$ %.2f\n", totalVendas);
        System.out.printf("ICMS: R$ %.2f\n", totalIcms);
        System.out.printf("PIS: R$ %.2f\n", totalPis);
        System.out.printf("COFINS: R$ %.2f\n", totalCofins);
        System.out.printf("Carga tributária média: %.1f%%\n", 
            (totalIcms + totalPis + totalCofins) / totalVendas * 100);
    }
    
    // Relatório por categoria (mantido)
    public void relatorioPorCategoria() {
        Map<String, Double> totaisPorCategoria = new HashMap<>();
        Map<String, Integer> quantidadesPorCategoria = new HashMap<>();
        
        for (NotaFiscal nota : notas) {
            for (Produto p : nota.getProdutos()) {
                String cat = p.getCategoria();
                totaisPorCategoria.put(cat, 
                    totaisPorCategoria.getOrDefault(cat, 0.0) + p.getValorTotal());
                quantidadesPorCategoria.put(cat,
                    quantidadesPorCategoria.getOrDefault(cat, 0) + p.getQuantidade());
            }
        }
        
        System.out.println("\n📊 VENDAS POR CATEGORIA");
        System.out.println("========================================");
        for (String cat : totaisPorCategoria.keySet()) {
            System.out.printf("%s: R$ %.2f (%d unidades)\n", 
                cat, totaisPorCategoria.get(cat), quantidadesPorCategoria.get(cat));
        }
    }
    
    // Gerar relatório completo em arquivo
    public void gerarRelatorioCompleto() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_RELATORIO))) {
            writer.println("RELATÓRIO FISCAL COMPLETO");
            writer.println("Gerado em: " + new Date());
            writer.println("========================================\n");
            
            for (NotaFiscal nota : notas) {
                writer.println(nota);
                writer.println();
            }
            
            writer.println("========================================");
            writer.println("RESUMO GERAL");
            writer.printf("Total de notas: %d\n", notas.size());
            writer.printf("Faturamento total: R$ %.2f\n", getTotalVendas());
            writer.printf("ICMS total: R$ %.2f\n", getTotalIcms());
            writer.printf("PIS total: R$ %.2f\n", getTotalPis());
            writer.printf("COFINS total: R$ %.2f\n", getTotalCofins());
            writer.printf("Carga tributária média: %.1f%%\n", 
                (getTotalIcms() + getTotalPis() + getTotalCofins()) / getTotalVendas() * 100);
            
            System.out.println("✅ Relatório gerado: " + ARQUIVO_RELATORIO);
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    // Validação de CNPJs
    public void validarTodosCNPJs() {
        System.out.println("\n🔍 VALIDAÇÃO DE CNPJs");
        System.out.println("========================================");
        
        for (NotaFiscal nota : notas) {
            String status = nota.isCnpjValido() ? "✅ VÁLIDO" : "❌ INVÁLIDO";
            System.out.printf("%s - %s: %s %s\n",
                nota.getNumeroNota(),
                nota.getCliente(),
                ValidadorCNPJ.formatarCNPJ(nota.getCnpjCliente()),
                status);
        }
    }
    
    // Getter
    public boolean temNotas() {
        return !notas.isEmpty();
    }
}