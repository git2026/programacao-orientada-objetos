# Sistema de Reserva de Voos
Aplicação Java desenvolvida com interface gráfica para gestão de reservas de assentos em voos. O sistema permite aos utilizadores reservar assentos em três voos diferentes, visualizar informações de reservas, e gerir (alterar ou cancelar) reservas existentes de forma intuitiva e eficiente.

## Visão Geral do Sistema
Este projeto implementa um sistema completo de reserva de assentos em voos, construído em Java com interface gráfica desenvolvida em Swing. O sistema oferece uma experiência prática e intuitiva, permitindo aos utilizadores gerir reservas de forma fluída através de uma interface visualmente agradável.

O programa suporta três voos simultâneos (PT-ES, FR-DE, IT-UK) com o mesmo layout de avião, cada um com 30 filas e 6 assentos por fila (A-F), totalizando 180 assentos por voo. Os assentos são divididos em duas classes: **Executivo** (filas 1-6) e **Económico** (filas 7-30).

---

## 1. Ferramentas e Tecnologias Utilizadas

### Linguagem e Framework
- **Java SE** - Linguagem principal do projeto
- **Java Swing** - Framework para desenvolvimento da interface gráfica
- **Java AWT** - Componentes adicionais de interface

### Bibliotecas e Recursos
- **Serialização Java** - Persistência de dados (ObjectInputStream/ObjectOutputStream)
- **Collections Framework** - Estruturas de dados (HashMap, LinkedHashMap, ArrayList)
- **Exception Handling** - Tratamento personalizado de exceções

### Paradigmas de Programação
- **Programação Orientada a Objetos (POO)** - Interfaces, herança, polimorfismo e encapsulamento
- **Padrão MVC** - Separação de lógica de negócio e interface gráfica

---

## 2. Requisitos para Execução do Sistema

### Requisitos de Sistema
- **Java Development Kit (JDK)** versão 8 ou superior
- Sistema operacional: Windows, macOS ou Linux
- Mínimo 4GB de RAM recomendados

### Passos para Execução

1. **Certifique-se de que o JDK está instalado:**
   ```bash
   java -version
   ```
   Se não estiver instalado, baixe em [oracle.com/java](https://www.oracle.com/java/technologies/downloads/)

2. **Clone ou faça download do projeto:**
   ```bash
   cd codigo
   ```

3. **Compile os arquivos Java:**
   ```bash
   javac -d bin src/*.java
   ```
   Ou, se estiver na pasta `src`:
   ```bash
   javac *.java
   ```

4. **Execute o programa:**
   ```bash
   java -cp bin Main
   ```
   Ou, se compilou na pasta `src`:
   ```bash
   cd src
   java Main
   ```

5. **Utilize o sistema:**
   - Selecione um voo no menu dropdown (Voo1 PT-ES, Voo2 FR-DE ou Voo3 IT-UK)
   - Escolha uma das opções: Reservar Assento, Gestão de Assentos, Gestão de Reservas ou Ajuda

6. **Ao fechar o programa:**
   - Será perguntado se deseja guardar os dados da sessão
   - Os dados são salvos em ficheiros `.txt` (PT_ES.txt, FR_DE.txt, IT_UK.txt)

---

## 3. Estrutura do Projeto

```
codigo/
├── src/
│   ├── Main.java                      # Ponto de entrada da aplicação
│   ├── JanelaPrincipal.java          # Interface gráfica principal (1054 linhas)
│   │
│   ├── GestorDeVoos.java             # Interface de gestão de voos
│   ├── GestorDeVoosClass.java        # Implementação do gestor de voos
│   │
│   ├── Voo.java                      # Interface de voo
│   ├── VooClass.java                 # Implementação da classe voo
│   │
│   ├── Passageiro.java               # Interface de passageiro
│   ├── PassageiroClass.java          # Implementação da classe passageiro
│   │
│   ├── Recibo.java                   # Interface de recibo
│   ├── ReciboClass.java              # Implementação da classe recibo
│   │
│   ├── Assento.java                  # Interface de assento
│   ├── Economico.java                # Implementação de assento económico
│   ├── Executivo.java                # Implementação de assento executivo
│   │
│   ├── NifJaRegistradoException.java # Exceção customizada para NIF duplicado
│   └── FiltroNumeros.java            # Filtro de validação de entrada numérica
│
├── aviao.jpg                          # Imagem de fundo da interface
├── PT_ES.txt                          # Dados persistidos do Voo 1 (gerado em runtime)
├── FR_DE.txt                          # Dados persistidos do Voo 2 (gerado em runtime)
├── IT_UK.txt                          # Dados persistidos do Voo 3 (gerado em runtime)
└── README.md                          # Documentação do projeto
```

---

## 4. Arquitetura do Sistema

### Diagrama de Classes

O sistema foi arquitetado utilizando princípios sólidos de Programação Orientada a Objetos:

#### Hierarquia de Interfaces e Classes

**Camada de Gestão:**
- `GestorDeVoos` (Interface) ← `GestorDeVoosClass` (Implementação)
  - Responsável por gerir múltiplos voos
  - Métodos: `reservarAssento()`, `alterarReserva()`, `cancelarReserva()`, `guardar()`, `trocarVooAtual()`

**Camada de Voo:**
- `Voo` (Interface) ← `VooClass` (Implementação)
  - Responsável por gerir passageiros e assentos de um voo específico
  - Métodos: `reservarAssento()`, `alterarReserva()`, `cancelarReserva()`, `adicionarPassageiro()`, `removerPassageiro()`
  - Utiliza `Map<JToggleButton, Assento>` para mapear botões da UI aos assentos
  - Utiliza `Map<Long, Passageiro>` para indexar passageiros por NIF

**Camada de Passageiro:**
- `Passageiro` (Interface) ← `PassageiroClass` (Implementação)
  - Atributos: nome, NIF, país, recibo, assento
  - Métodos: `getNome()`, `setNome()`, `getNIF()`, `getPais()`, `setPais()`, `getRecibo()`, `atualizarRecibo()`, `getAssento()`

**Camada de Recibo:**
- `Recibo` (Interface) ← `ReciboClass` (Implementação)
  - Atributos: valorTotal, metodoPagamento, statusPagamento, bagagemExtra, seguroViagem, checkinAutomatico
  - Métodos: `getValorTotal()`, `getMetodoPagamento()`, `getStatusPagamento()`, `hasBagagemExtra()`, `hasSeguroViagem()`, `hasCheckinAutomatico()`

**Camada de Assento:**
- `Assento` (Interface) ← `Economico` (Classe base) ← `Executivo` (Herança)
  - `Economico`: Assentos das filas 7-30 (preço base: 105€)
  - `Executivo`: Assentos das filas 1-6 (preço base: 120€)
  - Métodos: `reservarAssento()`, `libertarAssento()`, `isOcupado()`, `getNumeroDoAssento()`, `getLetraDoAssento()`, `getPassageiro()`

**Componentes de Suporte:**
- `NifJaRegistradoException`: Exceção lançada quando um NIF já está registado
- `FiltroNumeros`: DocumentFilter para validação de entrada numérica no campo NIF
- `JanelaPrincipal`: Classe principal da interface gráfica com 1054 linhas de código

### Relações entre Classes

- **Implementação (interface ← classe)**: Representada por setas verdes picotadas
- **Herança (classe base ← subclasse)**: Representada por setas azuis
- **Composição**: `PassageiroClass` possui `Recibo` e `Assento`
- **Agregação**: `VooClass` agrega múltiplos `Passageiro` e `Assento`

---

## 5. Funcionalidades Principais

### 5.1 Reservar Assento
Permite ao utilizador selecionar e reservar um assento num voo específico.

**Passos:**
1. Selecionar um voo no dropdown (Voo1 PT-ES, Voo2 FR-DE ou Voo3 IT-UK)
2. Clicar em "Reservar Assento"
3. Selecionar um assento disponível no mapa visual:
   - **Verde**: Assentos económicos (filas 7-30) - 105€
   - **Amarelo**: Assentos executivos (filas 1-6) - 120€
   - **Vermelho**: Assentos já reservados (não selecionáveis)
4. Preencher informações obrigatórias:
   - Nome completo
   - País (lista de 42 países disponíveis)
   - NIF (validação automática para NIFs portugueses de 9 dígitos)
5. Selecionar extras opcionais:
   - ☑ Seguro (+10€)
   - ☑ Bagagem Extra
   - ☑ Check-in Automático
6. Escolher método de pagamento:
   - Cartão de Crédito
   - Cripto (BTC-ETH-KAS)
   - PayPal
   - Dinheiro
7. Indicar status do pagamento (Pago / Não Pago)
8. Clicar em "Reservar"

**Validações:**
- Assento selecionado obrigatório
- Nome completo obrigatório
- País selecionado obrigatório
- NIF obrigatório e válido (mínimo 9 dígitos)
- Método de pagamento obrigatório
- NIF único por voo (exceção lançada se duplicado)

### 5.2 Gestão de Assentos
Visualização de todos os assentos e suas informações.

**Funcionalidades:**
- Mapa visual de assentos (cores indicam status)
- Seleção de assentos reservados para visualizar detalhes
- Exibição de informações do passageiro:
  - Nome completo
  - País
  - NIF
  - Extras selecionados (Seguro, Bagagem Extra, Check-in)
  - Método de pagamento
  - Status de pagamento
- Campos não editáveis (apenas visualização)

### 5.3 Gestão de Reservas
Permite alterar ou cancelar reservas existentes.

**Alterar Reserva:**
1. Selecionar um assento reservado
2. Clicar em "Alterar"
3. Modificar informações:
   - Nome completo ✓
   - País ✓
   - Extras (Seguro, Bagagem, Check-in) ✓
   - Método de pagamento ✓
   - Status de pagamento ✓
   - **NIF não pode ser alterado** (identificador único)
4. Confirmar alterações

**Remover Reserva:**
1. Selecionar um assento reservado
2. Clicar em "Remover"
3. Reserva é cancelada e assento fica disponível

### 5.4 Ajuda
Fornece informações úteis sobre o sistema.

**Conteúdo:**
- **Dicas**: 4 dicas práticas para utilizar o sistema
- **FAQ**: Perguntas frequentes com respostas detalhadas
  - Como reservar um assento?
  - Limite de bagagem
  - Como cancelar reserva
  - O que está incluído no seguro
  - Descontos para grupos

---

## 6. Interface do Usuário

### Menu Principal
- **ComboBox de Voos**: Seleção entre 3 voos disponíveis
- **Botões principais** (centralizados):
  - Reservar Assento
  - Gestão de Assentos
  - Gestão de Reservas
  - Ajuda

### Mapa de Assentos
- Layout visual com 30 filas × 6 colunas (A-F)
- Corredor central entre colunas C e D
- Código de cores:
  - 🟢 Verde: Económico disponível
  - 🟡 Amarelo: Executivo disponível
  - 🔴 Vermelho: Reservado

### Painel de Informações
- Campos de entrada com validação
- ComboBoxes para seleção de opções
- CheckBoxes para extras
- RadioButtons para status de pagamento
- Exibição dinâmica de preço total

### Tema Visual
- Cor de fundo: Azul claro (RGB: 173, 216, 230)
- Imagem de fundo: `aviao.jpg`
- Fonte padrão: Calibri
- Dimensões da janela: 1000×700 pixels (fixas)

---

## 7. Estrutura de Preços

| Tipo de Assento | Preço Base | Taxa Admin | Total Base |
|-----------------|------------|------------|------------|
| Económico (7-30)| 100€       | 5€         | 105€       |
| Executivo (1-6) | 100€       | 5€ + 15€   | 120€       |

**Extras:**
- Seguro de Viagem: +10€
- Bagagem Extra: Incluída (sem custo adicional)
- Check-in Automático: Incluído (sem custo adicional)

**Exemplo de Cálculo:**
- Assento Executivo (5A) = 120€
- + Seguro = 10€
- **Total: 130€**

---

## 8. Persistência de Dados

### Mecanismo de Armazenamento
O sistema utiliza **serialização de objetos Java** para persistir dados entre sessões.

### Ficheiros de Dados
- `PT_ES.txt`: Dados do Voo 1 (Portugal-Espanha)
- `FR_DE.txt`: Dados do Voo 2 (França-Alemanha)
- `IT_UK.txt`: Dados do Voo 3 (Itália-Reino Unido)

### Comportamento
- **Ao iniciar**: Carrega dados existentes ou cria voos vazios
- **Ao fechar**: Prompt pergunta se deseja guardar alterações
  - ✓ Sim: Serializa todos os voos para ficheiros
  - ✗ Não: Descarta alterações da sessão

### Classes Serializáveis
Todas as classes de dados implementam `Serializable`:
- `GestorDeVoos`
- `Voo`
- `Passageiro`
- `Recibo`
- `Assento`

---

## 9. Validação e Tratamento de Erros

### Validação de NIF
Implementa algoritmo de validação para NIFs portugueses:
- Mínimo 9 dígitos obrigatórios
- Validação de dígito de controlo para NIFs PT (9 dígitos)
- Aceita NIFs estrangeiros (>9 dígitos) sem validação de dígito
- Prefixos válidos para PT: 1, 2, 3, 5, 6, 7, 8, 9, 45, 50-99

### Exceções Customizadas
- `NifJaRegistradoException`: Lançada quando o NIF já existe no voo
  - Mensagem: "Numero NIF já registrado para outro passageiro"
  - Previne duplicação de NIFs no mesmo voo

### Validação de Entrada
- `FiltroNumeros`: DocumentFilter que permite apenas dígitos numéricos no campo NIF
- Validação de campos obrigatórios com mensagens de alerta
- Verificação de assento selecionado antes de operações

### Mensagens de Alerta
O sistema exibe JOptionPane com avisos para:
- Campo obrigatório não preenchido
- NIF inválido
- NIF duplicado
- Nenhum assento selecionado
- Assento não ocupado (em gestão de reservas)

---

## 10. Considerações do Projeto

### Otimização e Qualidade de Código
- Código rigorosamente testado e otimizado
- Mínimo de linhas de código possível mantendo legibilidade
- Comentários detalhados em português em todas as classes
- Tratamento preventivo de bugs (ex: bloqueio de edição em campos de visualização)

### Design de Interface
- **Layout Intuitivo**: Assentos representados visualmente como na vida real
- **Consistência Visual**: Informações organizadas em colunas à direita
- **Hierarquia de Interação**: Elementos mais importantes em posições superiores
- **Cores Significativas**: Código de cores claro para status de assentos
- **Feedback Visual**: Atualização dinâmica de preços e estados

### Arquitetura Escalável
- Uso de interfaces para facilitar manutenção e extensão
- Separação clara entre lógica de negócio e interface
- Estruturas de dados eficientes (HashMap para O(1) lookup de passageiros)
- Padrão de design orientado a objetos bem estruturado

### Segurança e Integridade
- Validação rigorosa de dados de entrada
- NIF como identificador único por voo
- Confirmação antes de operações destrutivas (fechar sem guardar)
- Campos não editáveis em modo de visualização

---

## 11. Limitações e Melhorias Futuras

### Limitações Atuais
- Layout de avião fixo (30 filas × 6 assentos)
- Apenas 3 voos simultâneos
- Sem integração com banco de dados externo
- Sem suporte multi-usuário ou rede
- Validação de NIF específica para Portugal

### Possíveis Melhorias
- **Escalabilidade**: Suporte para múltiplos layouts de avião
- **Base de Dados**: Migração para SQL (MySQL/PostgreSQL)
- **Segurança**: Sistema de autenticação de usuários
- **Relatórios**: Geração de PDFs com recibos
- **Notificações**: E-mails de confirmação de reserva
- **Integrações**: API de pagamentos reais
- **Internacionalização**: Suporte multi-idioma
- **Responsividade**: Janela redimensionável
- **Acessibilidade**: Melhorias para utilizadores com necessidades especiais

---

## 12. Equipa de Desenvolvimento

Este projeto foi desenvolvido como trabalho académico de Programação Orientada a Objetos em Java, demonstrando competências em:
- Design de interfaces gráficas com Swing
- Aplicação de princípios de POO
- Gestão de persistência de dados
- Validação e tratamento de exceções
- Desenvolvimento de software orientado ao utilizador

---

## Licença

Este projeto é distribuído sob a licença **GNU General Public License v3.0**.

### Principais termos da GNU GPLv3:
- ✓ Uso comercial permitido
- ✓ Modificação permitida
- ✓ Distribuição permitida
- ✓ Uso privado permitido
- ⚠ Deve divulgar código-fonte
- ⚠ Licença e avisos de direitos autorais devem ser preservados
- ⚠ Modificações devem ser indicadas
- ⚠ Obras derivadas devem usar a mesma licença

Para mais detalhes, consulte [GNU GPL v3.0](https://www.gnu.org/licenses/gpl-3.0.html).

---

## Contacto e Suporte

Para questões, sugestões ou reportar problemas relacionados ao sistema:
- Abra uma issue no repositório do projeto
- Entre em contacto através do e-mail do desenvolvedor

---

**Última atualização**: Outubro 2025  
**Versão**: 1.0  
**Status**: Projeto Académico Concluído

