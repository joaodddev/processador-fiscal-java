import java.util.Scanner;

/**
 * Sistema de Processamento de Notas Fiscais - Versão Avançada
 * Demonstra: POO, Collections, Arquivos, Validações, Gráficos
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ProcessadorFiscal processador = new ProcessadorFiscal();
    
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   📄 SISTEMA DE PROCESSAMENTO FISCAL");
        System.out.println("============================================");
        System.out.println("Simulação de leitura e análise de notas fiscais");
        
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    processador.listarNotas();
                    break;
                case 2:
                    buscarNota();
                    break;
                case 3:
                    processador.relatorioPorCategoria();
                    break;
                case 4:
                    relatorioPorPeriodo();
                    break;
                case 5:
                    processador.gerarRelatorioCompleto();
                    break;
                case 6:
                    processador.validarTodosCNPJs();
                    break;
                case 7:
                    filtrarPorCliente();
                    break;
                case 8:
                    filtrarPorValorMinimo();
                    break;
                case 9:
                    processador.graficoVendasPorCategoria();
                    break;
                case 10:
                    processador.exportarParaCSV();
                    break;
                case 11:
                    processador.salvarNotas();
                    System.out.println("✅ Dados salvos com sucesso!");
                    break;
                case 0:
                    System.out.println("\n👋 Encerrando sistema... Até logo!");
                    processador.salvarNotas();
                    break;
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
            }
            
            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("\n📌 MENU FISCAL");
        System.out.println("============================================");
        System.out.println("1️⃣  Listar todas as notas fiscais");
        System.out.println("2️⃣  Buscar nota por número");
        System.out.println("3️⃣  Relatório por categoria de produto");
        System.out.println("4️⃣  Relatório por período (mês/ano)");
        System.out.println("5️⃣  Gerar relatório completo em arquivo");
        System.out.println("6️⃣  Validar CNPJs das notas");
        System.out.println("7️⃣  Filtrar notas por cliente");
        System.out.println("8️⃣  Filtrar notas por valor mínimo");
        System.out.println("9️⃣  📊 Gráfico de vendas por categoria");
        System.out.println("🔟  📎 Exportar dados para CSV (Excel)");
        System.out.println("1️⃣1️⃣ Salvar dados manualmente");
        System.out.println("0️⃣  Sair");
        System.out.println("============================================");
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void buscarNota() {
        if (!processador.temNotas()) {
            System.out.println("📭 Nenhuma nota cadastrada.");
            return;
        }
        
        System.out.print("Digite o número da nota (ex: NF-001): ");
        String numero = scanner.nextLine();
        processador.buscarNota(numero);
    }
    
    private static void relatorioPorPeriodo() {
        if (!processador.temNotas()) {
            System.out.println("📭 Nenhuma nota cadastrada.");
            return;
        }
        
        System.out.print("Digite o mês/ano (ex: 03/2026): ");
        String periodo = scanner.nextLine();
        processador.relatorioPorPeriodo(periodo);
    }
    
    private static void filtrarPorCliente() {
        if (!processador.temNotas()) {
            System.out.println("📭 Nenhuma nota cadastrada.");
            return;
        }
        
        System.out.print("Digite o nome do cliente (ou parte dele): ");
        String cliente = scanner.nextLine();
        processador.filtrarPorCliente(cliente);
    }
    
    private static void filtrarPorValorMinimo() {
        if (!processador.temNotas()) {
            System.out.println("📭 Nenhuma nota cadastrada.");
            return;
        }
        
        System.out.print("Digite o valor mínimo (ex: 5000): ");
        try {
            double valor = Double.parseDouble(scanner.nextLine());
            processador.filtrarPorValorMinimo(valor);
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido!");
        }
    }
}