# 📄 Processador Fiscal em Java

![Java](https://img.shields.io/badge/Java-17+-orange)
![Status](https://img.shields.io/badge/Status-Concluído-green)
![Licença](https://img.shields.io/badge/Licença-MIT-blue)

Um sistema completo para processamento e análise de notas fiscais desenvolvido em Java, com foco em simular operações fiscais reais e gerar relatórios gerenciais.

## ✨ Funcionalidades Avançadas

### 📊 Processamento Fiscal
- ✅ Cadastro e gerenciamento de notas fiscais
- ✅ Múltiplos impostos por categoria (ICMS, PIS, COFINS)
- ✅ Alíquotas diferenciadas por tipo de produto/serviço
- ✅ Cálculo automático da carga tributária

### 🔍 Validações e Filtros
- ✅ **Validação completa de CNPJ** (formato e dígitos verificadores)
- ✅ Busca por número da nota
- ✅ Filtro por cliente (busca parcial)
- ✅ Filtro por valor mínimo da nota

### 📈 Relatórios e Visualizações
- ✅ **Gráficos ASCII** no console mostrando vendas por categoria
- ✅ Relatórios por período (mês/ano)
- ✅ Estatísticas por categoria de produto
- ✅ Exportação para **CSV** (compatível com Excel/Google Sheets)
- ✅ Relatório completo em arquivo .txt

### 💾 Persistência
- ✅ Salvamento automático em arquivo texto
- ✅ Carregamento automático dos dados ao iniciar
- ✅ Exportação para múltiplos formatos

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem principal
- **Programação Orientada a Objetos** - Classes, encapsulamento, composição
- **Collections Framework** - ArrayList, HashMap
- **Manipulação de Arquivos** - FileReader, FileWriter, BufferedReader, PrintWriter
- **Tratamento de Exceções** - Try-catch para operações de I/O e validações
- **Expressões Regulares** - Validação de formato de CNPJ

## 📁 Estrutura do Projeto
src/
├── Main.java # Menu principal e interação com usuário
├── Produto.java # Representa item da nota com cálculos de impostos
├── NotaFiscal.java # Agrega produtos e calcula totais
├── ProcessadorFiscal.java # Lógica de negócio e relatórios
└── ValidadorCNPJ.java # Utilitário de validação de CNPJ
## 🚀 Como Executar

### Pré-requisitos
- Java JDK 11 ou superior
- Git (opcional, para clonar)

### Passo a passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/joaodddev/processador-fiscal-java.git
   cd processador-fiscal-java
2. Compile os arquivos:
   javac *.java
3. Execute o programa:
   java Main
4. 📖 Exemplo de Uso
============================================
   📄 SISTEMA DE PROCESSAMENTO FISCAL
============================================

📌 MENU FISCAL AVANÇADO
1️⃣  Listar todas as notas fiscais
2️⃣  Buscar nota por número
3️⃣  Relatório por categoria
4️⃣  Relatório por período
5️⃣  Gerar relatório completo
6️⃣  Validar CNPJs
7️⃣  Filtrar por cliente
8️⃣  Filtrar por valor mínimo
9️⃣  📊 Gráfico de vendas
🔟  📎 Exportar para CSV
1️⃣1️⃣ Salvar dados
0️⃣  Sair

Exemplo de Gráfico ASCII
📊 GRÁFICO DE VENDAS POR CATEGORIA
========================================
Alimentos    | ████████████████████████████████████████ R$ 2.960,00
Eletrônicos  | ██████████████████████████████████████████████████ R$ 7.450,00
Serviços     | ██████████████████████████ R$ 14.800,00
========================================
Cada █ representa R$ 300

Exemplo de Validação de CNPJ
🔍 VALIDAÇÃO DE CNPJs
========================================
NF-001 - Empresa ABC Ltda: 12.345.678/0001-90 ✅ VÁLIDO
NF-002 - Comércio de Alimentos ME: 11.111.111/0001-11 ❌ INVÁLIDO
NF-003 - Consultoria Fiscal SS: 45.678.901/0001-23 ✅ VÁLIDO

📊 Arquivos Gerados
Arquivo	Descrição
notas_fiscais.txt	Banco de dados em texto (persistência)
relatorio_fiscal.txt	Relatório completo formatado
dados_exportados.csv	Planilha para Excel/Google Sheets

🧠 Conceitos Aplicados

POO: Encapsulamento, composição, métodos
Validação de dados: Algoritmo de dígitos verificadores de CNPJ
Estruturas de dados: Listas e mapas para agregação
Persistência: Leitura/escrita em arquivos texto
UX no console: Menus interativos, gráficos ASCII
Tratamento de erros: Entradas inválidas, arquivos não encontrados

👨‍💻 Autor
João Victor
https://img.shields.io/badge/GitHub-joaodddev-181717?style=flat&logo=github
https://img.shields.io/badge/LinkedIn-Perfil-0077B5?style=flat&logo=linkedin
