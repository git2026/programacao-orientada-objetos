// Imports usados no programa
// Classes fundamentais, e para o UI utilizando o Java Swing
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;

public class JanelaPrincipal extends JFrame implements ActionListener {
    // Dimensões e cores padrão da janela
    private static final int WIDTH = 1000; // Largura fixa da janela
    private static final int HEIGHT = 700; // Altura fixa da janela 
    private static final Color COR_BACKGROUND = new Color(173, 216, 230); // Cor azul clara do fundo do resto da interface
    private static final String IMAGEM_FUNDO = "aviao.jpg"; // Caminho da imagem de fundo

    // Gestão de voos e estado atual da interface
    private GestorDeVoos gestorVoos; // Gerenciador dos voos
    private PanelState estadoAtual; // Estado atual do painel
    private int IndexVooSelecionado = 0; // Índice do voo selecionado


    // Componentes da interface gráfica para interação com o usuário
    private JTextField campoNomeCompleto, campoNIF; // Campos de texto para nome completo e NIF
    private JLabel campoPreco, campoPais, campoMetodoPagamento; // Campos para preço, país e método de pagamento
    private JComboBox<String> comboBoxVoos, metodoPagamentoComboBox, paisComboBox; // ComboBox para voos, método de pagamento e países
    private JCheckBox seguroCheckBox, bagagemExtraCheckBox, checkinAutomaticoCheckBox; // CheckBox para seguro, bagagem extra e check-in automático
    private JRadioButton pagoBotaoRadio, naoPagoBotaoRadio; // Botões de rádio para status de pagamento
    private String[] paises, metodosPagamento; // Arrays para armazenar países e métodos de pagamento
    private Map<JToggleButton, Assento> mapaAssentos; // Mapeamento dos botões de assento
    private ButtonGroup grupoAssentos; // Grupo de botões para assentos
    enum PanelState {RESERVA_ASSENTO, GESTAO_ASSENTOS, GESTAO_RESERVAS;} // Estado da Janela


    public JanelaPrincipal() {
        // Instancia o gerenciador de voos e inicializa componentes relacionados aos assentos
        gestorVoos = new GestorDeVoosClass(); // Cria uma nova instância do gerenciador de voos
        grupoAssentos = gestorVoos.getGrupoBotoes(); // Obtém o grupo de botões de assentos do gerenciador
        mapaAssentos = gestorVoos.getMapaAssentos(); // Obtém o mapeamento de assentos e botões correspondentes
        
        // Configura opções para seleção do usuário
        // Lista de países disponíveis
        // Opções de métodos de pagamento
        paises = new String[]{"Selecione um Pais","Alemanha", "Argentina", "Austrália", "África do Sul", "Áustria","Bélgica", "Brasil", "Canadá", "Chile", "China", "Singapura", "Colômbia","Coreia do Sul", "Dinamarca","Espanha", "Estados Unidos","Filipinas","Finlândia", "França", "Grécia", "Holanda", "Indonésia", "Irlanda", "Itália","Japão", "Malásia", "Marrocos", "México", "Nigéria", "Noruega", "Nova Zelândia","Peru", "Polónia", "Portugal", "Reino Unido", "Rússia", "Suécia", "Suíça", "Tailândia","Turquia", "Índia", "Outro"};
        metodosPagamento = new String[]{"Selecione um Método", "Cartão de Crédito", "Cripto (BTC-ETH-KAS)", "PayPal", "Dinheiro"};
        
        // Configurações iniciais da janela
        configurarJanelaInicial(); // Configura as propriedades iniciais da janela
        criarPainelCentral(); // Cria e adiciona o painel central à janela

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int guardarDados = JOptionPane.showConfirmDialog(null, 
                    "Pretende guardar os dados desta sessão do programa?", 
                    "Guardar Dados", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);
                if (guardarDados == JOptionPane.YES_OPTION) {
                    gestorVoos.guardar(); // Salva os dados se a escolha for "Yes"
                    dispose(); // Fecha a janela do programa
                } else if (guardarDados == JOptionPane.NO_OPTION) { // Se o usuário fechar o pop-up de guardar, a janela principal permanece aberta
                    dispose(); // Fecha a janela se a escolha for "No"
                }
            }
        });
    }

    // Método para configurar as propriedades iniciais da janela principal
    private void configurarJanelaInicial() {
        setTitle("Sistema de Reserva de Voos"); // Define o título da janela
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Define o comportamento padrão de fechamento do programa para não fechar a janela
        setSize(WIDTH, HEIGHT); // Define o tamanho da janela com base nos valores já definidos
        setResizable(false); // Impede que a janela seja redimensionada
        setLocationRelativeTo(null); // Centra a janela na tela

        // Configuração do plano de fundo da janela
        JLabel fundoJanelaInicial = new JLabel(new ImageIcon(IMAGEM_FUNDO)); // Cria o Campo como plano de fundo com a imagem
        setContentPane(fundoJanelaInicial); // Insere o Campo na janela toda
        setLayout(new BorderLayout(10, 10)); // Define um layout de borda com espaçamento horizontal e vertical
    }

    
    // Método para criar o painel de seleção de voos
    private void criarPainelVoo() {
        // Cria um painel para os componentes com alinhamento à direita
        JPanel painelVoo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelVoo.setOpaque(false); // Define o painel como transparente
        painelVoo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10)); // Define a borda vazia do painel

        // Cria e configura o texto "Escolha um Voo"
        JLabel campoEscolhaVoo = new JLabel("Escolha um Voo: ");
        campoEscolhaVoo.setFont(new Font("Calibri", Font.BOLD, 20)); // Define a fonte e o tamanho do texto
        campoEscolhaVoo.setForeground(Color.BLACK); // Define a cor do texto

        // Cria e configura a caixa de combinação para a seleção dos voos
        comboBoxVoos = new JComboBox<>(new String[]{"Voo1 PT-ES", "Voo2 FR-DE", "Voo3 IT-UK"});
        comboBoxVoos.setFont(new Font("Calibri", Font.PLAIN, 18)); // Define a fonte e o tamanho do texto dentro da ComboBox
        comboBoxVoos.setForeground(Color.BLACK); // Define a cor do texto
        comboBoxVoos.setBackground(Color.WHITE); // Define a cor de fundo
        comboBoxVoos.setPreferredSize(new Dimension(130, 25)); // Define o tamanho predefinido
        comboBoxVoos.addActionListener(_ -> trocarVoo()); // Adiciona um foco para a ação de seleção
        painelVoo.add(campoEscolhaVoo); // Adiciona o painel do "Escolha um Voo"
        painelVoo.add(comboBoxVoos); // Adiciona a ComboBox dentro do painel do "Escolha um Voo"
        add(painelVoo, BorderLayout.NORTH); // Adiciona o painel de voos ao canto superior direito
    }

    // Método para criar o painel central com os botões principais
    private void criarPainelCentral() {
        // Cria um painel central para os botões
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS)); // Define o layout do painel como BoxLayout na vertical
        painelCentral.setOpaque(false); // Torna o painel transparente
        painelCentral.add(Box.createRigidArea(new Dimension(0, 65))); // Adiciona um espaço rígido para afastamento

        // Cria e configura o texto de inicial do programa
        JLabel campoTextoInicial = new JLabel("Bem-vindo, Escolha uma das seguintes opções:", SwingConstants.CENTER);
        campoTextoInicial.setFont(new Font("Calibri", Font.BOLD, 30)); // Define a fonte e o tamanho do texto
        campoTextoInicial.setForeground(Color.BLACK); // Define a cor do texto
        campoTextoInicial.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra o campo no eixo X
        painelCentral.add(campoTextoInicial); // Adiciona o campo ao painel

        // Configurações de espaçamento vertical entre os botões
        int espacoVertical = 20;

        // Adiciona botões ao painel central com espaçamento vertical
        painelCentral.add(Box.createRigidArea(new Dimension(0, 60))); // Espaço antes do primeiro botão
        painelCentral.add(criarbotao("Reservar Assento")); // Adiciona os botões ao painel
        painelCentral.add(Box.createVerticalStrut(espacoVertical)); // Espaço entre os botões
        painelCentral.add(criarbotao("Gestão de Assentos")); // Adiciona os botões ao painel
        painelCentral.add(Box.createVerticalStrut(espacoVertical)); // Espaço entre os botões
        painelCentral.add(criarbotao("Gestão de Reservas")); // Adiciona os botões ao painel
        painelCentral.add(Box.createVerticalStrut(espacoVertical)); // Espaço entre os botões
        painelCentral.add(criarbotao("Ajuda")); // Adiciona os botões ao painel
        add(painelCentral, BorderLayout.CENTER); // Adiciona o painel central ao centro da janela principal
        criarPainelVoo(); // Chama o método para criar o painel de seleção de voos
    }

    // Método para criar um botão para o criarPainelCentral
    private JButton criarbotao(String text) {
        JButton botaoPainelCentral = new JButton(text); // Cria um novo botão com o texto fornecido
        botaoPainelCentral.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinha o botão ao centro no eixo X

        // Define a fonte e o tamanho do texto no botão
        botaoPainelCentral.setFont(new Font("Calibri", Font.PLAIN, 26));
        botaoPainelCentral.setForeground(Color.BLACK); // Define a cor do texto
        botaoPainelCentral.setBackground(COR_BACKGROUND); // Define a cor de fundo
        botaoPainelCentral.addActionListener(this); // Adiciona um foco de ações

        // Define o tamanho máximo, mínimo e predefinido do botão
        botaoPainelCentral.setMaximumSize(new Dimension(400, 70));
        botaoPainelCentral.setMinimumSize(new Dimension(400, 70));
        botaoPainelCentral.setPreferredSize(new Dimension(400, 70));
        return botaoPainelCentral; // Retorna o botão criado
    }

    // Método para responder a eventos de ação
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtém o comando do evento para determinar qual botão foi pressionado
        String atividade = e.getActionCommand();

        // Realiza ações com base no comando recebido
        if (atividade.equals("Reservar Assento")) {
            // Se o comando for "Reservar Assento", chama o método para exibir a tela de reserva de assento
            exibirTelaReservaAssento();
        } else if (atividade.equals("Gestão de Assentos")) {
            // Se o comando for "Gestão de Assentos", chama o método para exibir a tela de gestão de assentos
            exibirTelaGestaoAssentos();
        } else if (atividade.equals("Gestão de Reservas")) {
            // Se o comando for "Gestão de Reservas", chama o método para exibir a tela de gestão de reservas
            exibirTelaGestaoReservas();
        } else if (atividade.equals("Ajuda")) {
            // Se o comando for "Ajuda", chama o método para exibir a tela de ajuda
            exibirTelaAjuda();
        }
    }

    // Método para exibir a tela de reserva de assentos
    private void exibirTelaReservaAssento() {
        estadoAtual = PanelState.RESERVA_ASSENTO; // Define o estado atual
        atualizarEstadoBotaoAssento(); // Define o estado dos botões dos assentos

        getContentPane().removeAll(); // Remove todos os componentes da janela atual
        repaint(); // Repinta
        setLayout(new BorderLayout()); // Define o layout da janela com BorderLayout

        // Cria e configura um JSplitPane dividindo a tela horizontalmente entre os painéis de assentos e reserva
        JSplitPane divisorJanela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criarPainelAssentos(), criarPainelReservaAssento());
        divisorJanela.setResizeWeight(0.5); // Configura o redimensionamento
        divisorJanela.setDividerLocation(650); // Define a localização do divisor
        divisorJanela.setDividerSize(0); // Define o tamanho do divisor
        divisorJanela.setBorder(null); // Remove a borda do divisorJanela
        add(divisorJanela, BorderLayout.CENTER); // Adiciona o divisorJanela a janela no centro

        // Cria e configura o botão "Voltar"
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(_ -> voltarJanelaPrincipal()); // Adiciona ação para voltar ao JanelaInicial
        JPanel botaoVoltarPainel = new JPanel(new BorderLayout()); // Cria um painel para o botão de voltar
        botaoVoltarPainel.add(botaoVoltar, BorderLayout.WEST); // Adiciona o botão ao painel
        botaoVoltarPainel.setBackground(COR_BACKGROUND); // Define a cor de fundo
        add(botaoVoltarPainel, BorderLayout.NORTH); // Adiciona o painel com o botão

        setSize(WIDTH, HEIGHT); // Define o tamanho da janela
        revalidate(); // Atualiza a janela para refletir as mudanças
    }

    // Método para criar o painel de reserva de assentos
    private JPanel criarPainelReservaAssento() {
        // Configuração inicial do painel usando um layout GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Configura a largura da grade
        gbc.fill = GridBagConstraints.HORIZONTAL; // Define que os componentes devem ocupar toda a largura horizontal
        gbc.insets = new Insets(2, 2, 2, 2); // Define os espaçamentos entre os componentes
        painel.setBackground(COR_BACKGROUND); // Define a cor de fundo

        // Adiciona o campo "Nome Completo"
        campoNomeCompleto = new JTextField(20); // Cria o campo de texto com um tamanho específico
        campoNomeCompleto.setEditable(true); // Permite que o campo seja editável
        campoNomeCompleto.setBackground(Color.WHITE); // Define a cor de fundo
        int espacoExtraLargura = 20, espacoExtraAltura = 8; // Define o preenchimento adicional para largura e altura
        Dimension tamanhoDefinido = campoNomeCompleto.getPreferredSize(); // Obtém o tamanho predefinido do campo

        // Define o tamanho predefinido do campo com o preenchimento
        campoNomeCompleto.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura, tamanhoDefinido.height + espacoExtraAltura));
        gbc.insets = new Insets(10, 2, 2, 2); // Atualiza os espaçamentos entre o texto "Nome Completo" e o campo
        adicionarCampoTexto(painel, "Nome Completo:", campoNomeCompleto, gbc); // Adiciona o campo de texto ao painel e o texto "Nome Completo:"

        // Adiciona uma ComboBox para "País"
        paisComboBox = new JComboBox<>(paises); // Cria a CheckBox com uma lista de países já definida
        paisComboBox.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura, tamanhoDefinido.height + espacoExtraAltura));
        adicionarCampoTexto(painel, "País:", paisComboBox, gbc);

        // Adiciona campo de texto para "NIF"
        campoNIF = campoInteiroNIF(20); // Cria um campo para NIF com tamanho específico
        campoNIF.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura, tamanhoDefinido.height + espacoExtraAltura));
        adicionarCampoTexto(painel, "NIF:", campoNIF, gbc);

        // Adiciona os "Extras" e as caixas de seleção para as opções
        JLabel campoExtras = new JLabel("Extras:");
        painel.add(campoExtras, gbc);
        seguroCheckBox = new JCheckBox("Seguro (+10€)"); // Cria CheckBox para opção de seguro
        adicionarFocoCheckBox(painel, seguroCheckBox, gbc);
        bagagemExtraCheckBox = new JCheckBox("Bagagem Extra"); // Cria CheckBox para bagagem extra
        adicionarFocoCheckBox(painel, bagagemExtraCheckBox, gbc);
        checkinAutomaticoCheckBox = new JCheckBox("Check-in Automático"); // Cria CheckBox para check-in automático
        adicionarFocoCheckBox(painel,checkinAutomaticoCheckBox, gbc);

        // Adiciona a ComboBox para "Método de Pagamento"
        metodoPagamentoComboBox = new JComboBox<>(metodosPagamento); // Cria a CheckBox com os métodos de pagamento já definidos
        adicionarCampoTexto(painel, "Método de Pagamento:", metodoPagamentoComboBox, gbc);

        // Adiciona o campo para "Preço"
        campoPreco = new JLabel("Preço: 0 €"); // Cria e configura o texto e o campo para exibir o preço
        painel.add(campoPreco, gbc);

        // Adiciona o painel para "Status do Pagamento"
        JPanel painelStatusPagamento = criarPainelStatusPagamento(); // Cria o painel de status do pagamento
        painel.add(new JLabel("Status do Pagamento:"), gbc); // Adiciona o texto para o painel de status
        painel.add(painelStatusPagamento, gbc); // Adiciona o painel de status ao painel principal

        // Adiciona o botão "Reservar"
        JButton botaoReservar = new JButton("Reservar"); // Cria o botão de reserva
        botaoReservar.addActionListener(_ -> processarReserva()); // Adiciona um foco de ação para processar a reserva
        botaoReservar.setHorizontalAlignment(SwingConstants.CENTER); // Centra horizontalmente o botão
        botaoReservar.setVerticalAlignment(SwingConstants.CENTER); // Centra verticalmente o botão
        botaoReservar.setPreferredSize(new Dimension(150, 30)); // Define o tamanho predefinido do botão
        botaoReservar.setFont(new Font("Calibri", Font.BOLD, 14)); // Define a fonte e o tamanho do botão
        painel.add(botaoReservar, gbc); // Adiciona o botão ao painel
        return painel; // Retorna o painel configurado
    }


    // Método para exibir o painel de gestão de assentos
    private void exibirTelaGestaoAssentos() {
        estadoAtual = PanelState.GESTAO_ASSENTOS; // Define o estado atual
        atualizarEstadoBotaoAssento(); // Atualiza o estado desabilitado dos botões de assento

        // Limpa o conteúdo atual do painel e prepara para novos componentes
        getContentPane().removeAll(); // Remove todos os componentes
        repaint(); // Repinta a janela
        setLayout(new BorderLayout()); // Define o layout da janela como BorderLayout

        // Cria um painel dividido horizontalmente com os painéis de assentos e de gestão do assento
        JSplitPane divisorJanela = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            criarPainelAssentos(), // Cria o painel dos assentos na esquerda
            criarPainelGestaoAssento()); // Cria o painel de gestão na direita
        divisorJanela.setResizeWeight(0.5); // Define a proporção da divisão da janela
        divisorJanela.setDividerLocation(650); // Define a localização do divisor entre os painéis
        divisorJanela.setDividerSize(0); // Define o tamanho do divisor
        divisorJanela.setBorder(null); // Remove a borda do divisor
        add(divisorJanela, BorderLayout.CENTER); // Adiciona o painel dividido ao centro do layout

        // Cria e configura o botão "Voltar"
        JButton botaoVoltar = new JButton("Voltar"); // Cria o botão "Voltar"
        botaoVoltar.addActionListener(_ -> voltarJanelaPrincipal()); // Adiciona ação para voltar à JanelaInicial
        JPanel botaoVoltarPainel = new JPanel(new BorderLayout()); // Cria um painel para o botão
        botaoVoltarPainel.add(botaoVoltar, BorderLayout.WEST); // Adiciona o botão na esquerda do painel
        botaoVoltarPainel.setBackground(COR_BACKGROUND); // Define a cor de fundo
        add(botaoVoltarPainel, BorderLayout.NORTH); // Adiciona o painel do botão ao topo do layout
        setSize(WIDTH, HEIGHT); // Define as dimensões da janela
        revalidate(); // Atualiza a janela com as novas mudanças
    }

    // Método para criar o painel de gestão de assentos
    private JPanel criarPainelGestaoAssento() {
        // Configuração principal do Painel de Gestão de Assento
        JPanel painel = new JPanel(new GridBagLayout()); // Define o layout da janela com GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Define restrições para componentes no layout
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Configura para que cada componente a sua ocupação
        gbc.fill = GridBagConstraints.HORIZONTAL; // Componentes expandem para preencher espaço horizontal
        gbc.insets = new Insets(2, 2, 2, 2);  // Define espaçamentos entre componentes
        painel.setBackground(COR_BACKGROUND); // Define a cor de fundo
    
        // Adicionar o campo "Passageiro"
        JLabel campoPassageiro = new JLabel("Passageiro"); // Cria um texto para o campo
        campoPassageiro.setHorizontalAlignment(SwingConstants.CENTER); // Centra o texto
        campoPassageiro.setFont(new Font("Calibri", Font.BOLD, 24)); // Define a fonte e o tamanho 
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Define a sua ocupação
        gbc.insets = new Insets(10, 0, 20, 20); // Define espaçamentos específicos para o campo
        painel.add(campoPassageiro, gbc); // Adiciona o campo ao painel
    
        // Adicionar o campo "Nome Completo:"
        gbc.insets = new Insets(10, 2, 2, 2); // Define espaçamentos do campo
        campoNomeCompleto = new JTextField(20); // Cria um campo de texto para o nome completo
        campoNomeCompleto.setEditable(false); // Define o campo para não editável
        campoNomeCompleto.setBackground(Color.WHITE); // Define a cor de fundo

        // Configuração do tamanho do campo de "Nome Completo:"
        int espacoExtraLargura = 20, espacoExtraAltura = 8; // Define espaços adicionais de largura e altura
        Dimension tamanhoDefinido = campoNomeCompleto.getPreferredSize(); // Obtém tamanho predefinido do campo
        campoNomeCompleto.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura, tamanhoDefinido.height + espacoExtraAltura));
        adicionarCampoTexto(painel, "Nome Completo:", campoNomeCompleto, gbc); // Adiciona o campo de nome ao painel com o texto "Nome Completo:"

        // Adicionar o campo "País"
        gbc.insets = new Insets(10, 2, 2, 2); // Define o espaçamento
        String[] paisesModificado = paises.clone(); // Cria uma cópia da lista de países
        paisesModificado[0] = ""; // Define o primeiro item da lista como vazio
        JComboBox<String> paisComboBox = new JComboBox<>(paisesModificado); // Cria uma CheckBox com a lista de países modificada
        gbc.insets = new Insets(10, 2, 2, 2); // Atualiza o espaçamento no campo
        campoPais = new JLabel(paisesModificado[0]); // Cria um campo para exibir o país selecionado
        campoPais.setOpaque(true); // Torna opaco
        campoPais.setBackground(Color.WHITE); // Define a cor de fundo
        campoPais.setForeground(Color.BLACK); // Define a cor do texto
        campoPais.setFont(new Font("Calibri", Font.PLAIN, 14)); // Define a fonte e o tamanho
        Border bordaPais = BorderFactory.createLineBorder(Color.GRAY); // Cria uma borda para o campo
        Border margemPais = BorderFactory.createEmptyBorder(10, 2, 2, 2); // Cria uma margem de espaços vazios
        Border componentePais = BorderFactory.createCompoundBorder(bordaPais, margemPais); // Combina a borda e a margem
        campoPais.setBorder(componentePais); // Aplica a borda e a margem combinadas
        campoPais.setPreferredSize(new Dimension(paisComboBox.getPreferredSize().width, paisComboBox.getPreferredSize().height + 4));
        adicionarCampoTexto(painel, "País:", campoPais, gbc); // Adiciona o texto "País" e o campo ao painel

        // Adicionar o campo "NIF:"
        gbc.insets = new Insets(10, 2, 2, 2); // Define o espaçamento
        campoNIF = new JTextField(); // Cria um campo de texto para o NIF
        campoNIF.setEditable(false); // Define o campo como não editável
        campoNIF.setBackground(Color.WHITE); // Define a cor de fundo
        int espacoExtraLarguraNIF = 20, espacoExtraAlturaNIF = 8; // Define espaços adicionais de largura e altura
        Dimension tamanhoDefinidoNIF = campoNIF.getPreferredSize(); // Obtém o tamanho predefinido
        campoNIF.setPreferredSize(new Dimension(tamanhoDefinidoNIF.width + espacoExtraLarguraNIF, tamanhoDefinidoNIF.height + espacoExtraAlturaNIF));
        adicionarCampoTexto(painel, "NIF:", campoNIF, gbc); // Adiciona o texto "NIF" e o campo ao painel

        // Adicionar opções "Extras:", "Seguro", "Bagagem Extra", "Check-in Automático"
        gbc.insets = new Insets(10, 2, 2, 2); // Define o espaçamento para o texto "Extras:"
        JLabel campoExtras = new JLabel("Extras:"); // Cria um campo para as opções extras
        painel.add(campoExtras, gbc); // Adiciona o campo ao painel

        // Inicialização dos estados das opções extras
        boolean estadoRealDoCheckin = false; // Estado inicial para Check-in Automático
        boolean estadoRealDaBagagemExtra = false; // Estado inicial para Bagagem Extra
        boolean estadoRealDoSeguro = false; // Estado inicial para Seguro

        // Adicionar a CheckBox "Seguro (+10€)"
        seguroCheckBox = new JCheckBox("Seguro (+10€)"); // Cria a CheckBox do Seguro
        checkboxGestaoAssento(seguroCheckBox, estadoRealDoSeguro); // Configura a CheckBox com o estado inicial
        seguroCheckBox.setOpaque(false); // Define a CheckBox como transparente
        painel.add(seguroCheckBox, gbc); // Adiciona a CheckBox ao painel

        // Adicionar a CheckBox "Bagagem Extra"
        bagagemExtraCheckBox = new JCheckBox("Bagagem Extra"); // Cria a CheckBox para Bagagem Extra
        checkboxGestaoAssento(bagagemExtraCheckBox, estadoRealDaBagagemExtra); // Configura a CheckBox com o estado inicial
        bagagemExtraCheckBox.setOpaque(false); // Define a CheckBox como transparente
        painel.add(bagagemExtraCheckBox, gbc); // Adiciona a CheckBox ao painel

        // Adicionar a CheckBox "Check-in Automático"
        checkinAutomaticoCheckBox = new JCheckBox("Check-in Automático"); // Cria a CheckBox para Check-in Automático
        checkboxGestaoAssento(checkinAutomaticoCheckBox, estadoRealDoCheckin); // Configura a CheckBox com o estado inicial
        checkinAutomaticoCheckBox.setOpaque(false); // Define a CheckBox como transparente
        painel.add(checkinAutomaticoCheckBox, gbc); // Adiciona a CheckBox ao painel

        // Adicionar o campo "Método de Pagamento"
        String[] metodospagamentoeditado = metodosPagamento.clone(); // Clona o array de métodos de pagamentos já definido
        metodospagamentoeditado[0] = ""; // Define o primeiro item como vazio
        metodoPagamentoComboBox = new JComboBox<>(metodospagamentoeditado); // Cria uma CheckBox com os métodos de pagamento editados
        metodoPagamentoComboBox.setVisible(false); // Torna a CheckBox invisível
        campoMetodoPagamento = new JLabel(metodospagamentoeditado[0]); // Cria um campo para exibir o método de pagamento
        campoMetodoPagamento.setOpaque(true); // Torna o campo opaco
        campoMetodoPagamento.setBackground(Color.WHITE); // Define a cor de fundo
        campoMetodoPagamento.setForeground(Color.BLACK); // Define a cor do texto 
        campoMetodoPagamento.setFont(new Font("Calibri", Font.PLAIN, 14)); // Define a fonte e o tamanho do campo
        Border borda = BorderFactory.createLineBorder(Color.GRAY); // Cria uma borda
        Border margem = BorderFactory.createEmptyBorder(2, 5, 2, 5); // Cria uma margem
        Border componente = BorderFactory.createCompoundBorder(borda, margem); // Combina a borda e a margem
        campoMetodoPagamento.setBorder(componente); // Aplica a borda e a margem combinadas ao campo
        campoMetodoPagamento.setPreferredSize(new Dimension(metodoPagamentoComboBox.getPreferredSize().width, metodoPagamentoComboBox.getPreferredSize().height + 4));
        adicionarCampoTexto(painel, "Método de Pagamento:", campoMetodoPagamento, gbc); // Adiciona o campo de método de pagamento ao painel
        gbc.insets = new Insets(10, 2, 2, 2); // Atualiza o espaçamento
    
        // Adicionar os botões radio "Status do Pagamento", "Não Pago" e "Pago"
        JPanel painelStatusPagamento = criarPainelStatusPagamento(); // Chama um painel separado para o status do pagamento
        botaoradioGestaoAssento(naoPagoBotaoRadio); // Configura o botão de rádio para "Não Pago"
        botaoradioGestaoAssento(pagoBotaoRadio); // Configura o botão de rádio para "Pago"
        painel.add(new JLabel("Status do Pagamento:"), gbc); // Adiciona um campo para o status do pagamento
        painel.add(painelStatusPagamento, gbc); // Adiciona o painel de status do pagamento ao painel
        gbc.insets = new Insets(10, 2, 10, 2); // Define o espaçamento
        return painel; // Retorna o painel configurado
    }

    // Método para exibir a tela de gestão de reservas
    private void exibirTelaGestaoReservas() {
        estadoAtual = PanelState.GESTAO_RESERVAS; // Define o estado atual da janela
        atualizarEstadoBotaoAssento(); // Atualiza o estado de habilitação dos botões de assento
        getContentPane().removeAll(); // Remove o conteúdo atual
        repaint(); // Repinta a janela
        setLayout(new BorderLayout()); // Configura o layout com o BorderLayout

        // Cria e configura um JSplitPane para o painel visualização dos assentos e gestão das reservas
        JSplitPane divisorJanela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, criarPainelAssentos(), criarPainelGestaoReservas());
        divisorJanela.setResizeWeight(0.5); // Define a proporção inicial
        divisorJanela.setDividerLocation(650); // Define a posição inicial
        divisorJanela.setDividerSize(0); // Define a espessura
        divisorJanela.setBorder(null); // Remove a borda
        add(divisorJanela, BorderLayout.CENTER); // Adiciona o JSplitPane ao painel

        // Adicionar o botão para "Voltar"
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(_ -> voltarJanelaPrincipal()); // Define a ação do botão
        JPanel botaoVoltarPainel = new JPanel(new BorderLayout()); // Cria um painel para o botão
        botaoVoltarPainel.add(botaoVoltar, BorderLayout.WEST); // Adiciona o botão
        botaoVoltarPainel.setBackground(COR_BACKGROUND); // Define a cor de fundo
        add(botaoVoltarPainel, BorderLayout.NORTH); // Adiciona o painel do botão ao painel
        setSize(WIDTH, HEIGHT); // Configura o tamanho da janela
        revalidate(); // Atualiza a janela com as novas mudanças
    }

    // Método para criar o painel de gestão de reservas
    private JPanel criarPainelGestaoReservas() {
        // Criação e Configuração do painel Gestão de Reservas
        JPanel painel = new JPanel(new GridBagLayout()); // Utiliza GridBagLayout para o layout
        GridBagConstraints gbc = new GridBagConstraints(); // Define as restrições de layout dos componentes
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Define que os componentes ocupam o espaço
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que os componentes se expandam horizontalmente
        gbc.insets = new Insets(2, 2, 2, 2); // Define os espaçamentos entre os componentes
        painel.setBackground(COR_BACKGROUND); // Define a cor de fundo
    
        // Adicionar o campo "Nome Completo:"
        campoNomeCompleto = new JTextField(20); // Cria um campo de texto para "Nome Completo"
        campoNomeCompleto.setEditable(true); // Permite a edição do campo
        campoNomeCompleto.setBackground(Color.WHITE); // Define a cor de fundo
        int espacoExtraLargura = 20; // Define um espaço extra de largura
        int espacoExtraAltura = 8; // Define um espaço extra de altura
        Dimension tamanhoDefinido = campoNomeCompleto.getPreferredSize(); // Obtém o tamanho predefinido do campo de texto
        campoNomeCompleto.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura,tamanhoDefinido.height + espacoExtraAltura));
        adicionarCampoTexto(painel, "Nome Completo:", campoNomeCompleto, gbc); // Adiciona o texto e campo ao painel

        // Adicionar o campo "País:"
        paisComboBox = new JComboBox<>(paises); // Cria uma ComboBox para os países
        paisComboBox.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura,tamanhoDefinido.height + espacoExtraAltura));
        gbc.insets = new Insets(10, 2, 2, 2); // Define o espaçamento para a ComboBox do "País"
        adicionarCampoTexto(painel, "País:", paisComboBox, gbc); // Adiciona o texto e a ComboBox ao painel

        // Adicionar o campo "NIF:"
        campoNIF = new JTextField(); // Cria um campo de texto para "NIF"
        campoNIF.setEditable(false); // Define o campo como não editável
        campoNIF.setPreferredSize(new Dimension(tamanhoDefinido.width + espacoExtraLargura,tamanhoDefinido.height + espacoExtraAltura));
        adicionarCampoTexto(painel, "NIF:", campoNIF, gbc); // Adiciona o campo de texto e "NIF:" ao painel

        // Adicionar opções "Extras:", "Seguro", "Bagagem Extra", "Check-in Automático"
        JLabel campoExtras = new JLabel("Extras:"); // Cria o campo "Extras:" para as opções extras
        painel.add(campoExtras, gbc); // Adiciona o campo ao painel
        seguroCheckBox = new JCheckBox("Seguro (+10€)"); // Cria e adiciona a ComboBox "Seguro (+10€)"
        adicionarFocoCheckBox(painel, seguroCheckBox, gbc); // Adiciona a ComboBox ao painel
        bagagemExtraCheckBox = new JCheckBox("Bagagem Extra"); // Cria e adiciona a ComboBox "Bagagem Extra"
        adicionarFocoCheckBox(painel, bagagemExtraCheckBox, gbc); // Adiciona a ComboBox ao painel
        checkinAutomaticoCheckBox = new JCheckBox("Check-in Automático"); // Cria e adiciona a ComboBox "Check-in Automático"
        adicionarFocoCheckBox(painel, checkinAutomaticoCheckBox, gbc); // Adiciona a ComboBox ao painel

        // Adicionar os campos "Método de Pagamento:", "Preço" e ComboBox para escolher o método de pagamento
        adicionarCampoTexto(painel, "Método de Pagamento:", metodoPagamentoComboBox = new JComboBox<>(metodosPagamento), gbc);
        campoPreco = new JLabel("Preço: 0 €"); // Cria e adiciona um campo de texto para mostrar o preço
        painel.add(campoPreco, gbc); // Adiciona o campo do preço ao painel

        // Adicionar o campo "Status do Pagamento:" e os botões de radio do painel de status pagamento
        JPanel painelStatusPagamento = criarPainelStatusPagamento(); // Cria um painel separado para o status do pagamento, com os botões de radio
        painel.add(new JLabel("Status do Pagamento:"), gbc); // Adiciona um campo para o status do pagamento
        painel.add(painelStatusPagamento, gbc); // Adiciona o painel de status do pagamento ao painel da janela
        gbc.insets = new Insets(10, 2, 10, 2); // Ajusta o espaçamento

        // Adicionar o botão "Alterar"
        JButton botaoAlterar = new JButton("Alterar"); // Cria o botão com o texto "Voltar"
        botaoAlterar.addActionListener(_ -> { processarAlteracoes(); clearData(); }); // Adiciona um foco de ação para chamar processaralterações() e limpar os dados dos campos
        botaoAlterar.setHorizontalAlignment(SwingConstants.CENTER); // Centra o texto horizontalmente 
        botaoAlterar.setVerticalAlignment(SwingConstants.CENTER); // Centra o texto verticalmente
        botaoAlterar.setPreferredSize(new Dimension(150, 30)); // Define o tamanho
        botaoAlterar.setFont(new Font("Calibri", Font.BOLD, 14)); // Define a fonte e o tamanho do texto
        gbc.insets = new Insets(10, 2, 5, 2); // Ajusta o espaçamento
        painel.add(botaoAlterar, gbc); // Adiciona o botão ao painel

        // Adicinar o botão "Remover"
        JButton botaoRemover = new JButton("Remover"); // Cria um botão com o texto "Remover"
        botaoRemover.addActionListener(_ -> removerReserva()); // Adiciona um foco de ação que chama o método removerReserva() quando o botão é clicado
        botaoRemover.setHorizontalAlignment(SwingConstants.CENTER); // Centra o texto do botão horizontalmente
        botaoRemover.setVerticalAlignment(SwingConstants.CENTER); // Centra o texto do botão verticalmente
        botaoRemover.setPreferredSize(new Dimension(150, 30)); // Define o tamanho
        botaoRemover.setFont(new Font("Calibri", Font.BOLD, 14)); // Define a fonte e o tamanho do texto
        gbc.insets = new Insets(5, 2, 10, 2); // Ajusta o espaçamento
        painel.add(botaoRemover, gbc); // Adiciona o botão ao painel
        return painel; // Retorna o painel de gestão de reservas final completo
    }

    // Método para exibir a tela de ajuda no sistema de reserva de voos
    private void exibirTelaAjuda() {
        getContentPane().removeAll(); // Remove todos os componentes da janela
        repaint(); // Repinta a janela
        setLayout(new BorderLayout()); // Define o layout da janela como BorderLayout

        // Cria um botão "Voltar"
        JButton botaoVoltar = new JButton("Voltar"); // Cria o botão "Voltar"
        botaoVoltar.addActionListener(_ -> voltarJanelaPrincipal()); // Adiciona ação para voltar à JanelaInicial
        JPanel painelVoltar = new JPanel(new BorderLayout()); // Cria um painel para o botão
        painelVoltar.add(botaoVoltar, BorderLayout.WEST); // Adiciona o botão ao painel na posição oeste
        painelVoltar.setBackground(COR_BACKGROUND); // Define a cor de fundo
        add(painelVoltar, BorderLayout.NORTH); // Adiciona o painel final na parte superior da janela

        // Cria um painel central para exibir o conteúdo de ajuda
        JPanel painelAjuda = new JPanel(); // Cria um novo painel para o conteúdo de ajuda
        painelAjuda.setLayout(new BoxLayout(painelAjuda, BoxLayout.Y_AXIS)); // Define o layout do painel como BoxLayout no eixo Y
        painelAjuda.setBackground(COR_BACKGROUND); // Define a cor de fundo
        painelAjuda.add(Box.createVerticalStrut(20)); // Adiciona um espaçamento vertical

        // Adiciona um título para a seção de ajuda
        JPanel tituloAjuda = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Cria um painel para o título com FlowLayout alinhado à esquerda
        tituloAjuda.setBackground(COR_BACKGROUND); // Define a cor de fundo
        JLabel campoAjuda = new JLabel("Ajuda"); // Cria um campo para o título "Ajuda"
        campoAjuda.setFont(new Font("Calibri", Font.BOLD, 22)); // Define a fonte e o tamanho do texto
        tituloAjuda.add(campoAjuda); // Adiciona o campo ao painel de título
        painelAjuda.add(tituloAjuda); // Adiciona o painel de título final ao painel da janela

        // Adiciona o texto com as dicas
        JTextArea dicas = new JTextArea(
        "Aqui estão algumas dicas para ajudá-lo a utilizar o nosso sistema:\n\n" +
        "• Dica 1: Sempre verifique suas informações pessoais antes de confirmar uma reserva,\n" +
        "          e caso se tenha enganado na reserva por favor contacte o suporte ao cliente.\n\n" +
        "• Dica 2: Use a funcionalidade de check-in automático para economizar tempo no aeroporto.\n\n" +
        "• Dica 3: Adicione bagagem extra durante a reserva para evitar taxas adicionais no aeroporto.\n\n" +
        "• Dica 4: Se você precisar de assistência adicional, não hesite em contactar o nosso suporte ao cliente.\n\n");

        dicas.setEditable(false); // Impede que o texto seja editado pelo usuário
        dicas.setWrapStyleWord(true); // Define a quebra de linha no final das palavras
        dicas.setLineWrap(true); // Define a quebra automática de linha
        dicas.setBackground(COR_BACKGROUND); // Define a cor de fundo
        dicas.setFont(new Font("Calibri", Font.PLAIN, 14)); // Define a fonte e o tamanho do texto
        dicas.setMargin(new Insets(10, 50, 0, 20)); // Define as margens internas
        painelAjuda.add(dicas); // Adiciona o campo de texto ao painel da janela
        painelAjuda.add(Box.createVerticalStrut(0)); // Adiciona um espaçamento vertical ao painel, embora aqui o valor seja 0

        // Adiciona o campo do "FAQ"
        JPanel tituloFAQ = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
        tituloFAQ.setBackground(COR_BACKGROUND); // Define a cor de fundo
        JLabel campoFAQ = new JLabel("FAQ"); // Cria um campo para o título "FAQ"
        campoFAQ.setFont(new Font("Calibri", Font.BOLD, 22)); // Define a fonte e o tamanho do texto
        tituloFAQ.add(campoFAQ); // Adiciona o campo ao painel do título FAQ
        painelAjuda.add(tituloFAQ); // Adiciona o painel do título FAQ ao painel da janela

        // Texto com as perguntas e respostas do FAQ
        JTextArea FAQ = new JTextArea(
        "Aqui estão as respostas para algumas das perguntas mais comuns:\n\n" +
        "• Como faço para reservar um assento?\n  Resposta: Escolha o seu Voo, após escolher, entre na secção de \"Reserva Assento\" do nosso programa, " +
        "e escolha o seu Assento no avião, e preencha as informações necessárias, como também as opções extras e método de pagamento.\n\n" +
        "• Existe um limite de bagagem?\n  Resposta: Sim, cada passageiro pode levar até 20kg de bagagem. Bagagem extra pode ser adicionada mais tarde também.\n\n" +
        "• Como posso cancelar minha reserva?\n  Resposta: Caso queira cancelar a sua reserva. Por favor contacte o suporte ao cliente.\n\n" +
        "• O que está incluído na taxa de seguro?\n  Resposta: A taxa de seguro cobre cancelamentos inesperados, perda de bagagem e assistência médica de emergência.\n\n" +
        "• Existem descontos para grupos?\n  Resposta: Sim, oferecemos descontos para grupos de 5 ou mais pessoas. Por favor, entre em contato com o nosso suporte para mais detalhes.\n");

        FAQ.setEditable(false); // Impede que o texto seja editado pelo usuário
        FAQ.setWrapStyleWord(true); // Define a quebra de linha no final das palavras
        FAQ.setLineWrap(true); // Define a quebra automática de linha
        FAQ.setBackground(COR_BACKGROUND); // Define a cor de fundo
        FAQ.setFont(new Font("Calibri", Font.PLAIN, 14)); // Define a fonte e o tamanho do texto
        FAQ.setMargin(new Insets(10, 50, 15, 20)); // Define as margens internas
        painelAjuda.add(FAQ); // Adiciona o campo de texto FAQ ao painel da janela
        add(painelAjuda, BorderLayout.CENTER); // Adiciona o painel de ajuda ao centro da janela
        revalidate(); // Atualiza a janela para exibir as novas mudanças
    }

    // Método para criar o painel de assentos
    private JPanel criarPainelAssentos() {
        // Configuração do painel de assentos com 30 linhas e 7 colunas
        // 6 colunas para assentos e 1 para o corredor
        JPanel painelAssentos = new JPanel(new GridLayout(30, 7, 5, 5)); // Cria o painel usando um GridLayout do tamanho pretendido
        painelAssentos.setBackground(COR_BACKGROUND); // Define a cor de fundo

        // Itera sobre os botões de assento no mapa de assentos
        for (JToggleButton botoes : mapaAssentos.keySet()) {
            Assento assento = mapaAssentos.get(botoes); // Obtém o assento associado ao botão
            if (assento.isOcupado()) // Configura a cor do botão com base no estado de ocupação do assento
                botoes.setBackground(Color.RED);
            botoes.setEnabled(!assento.isOcupado()); // Permite ou não a seleção do botão com base na ocupação do assento
            botoes.addItemListener(e -> { // Adiciona um foco de item para gerenciar ações com base no estado do assento
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch (estadoAtual) {
                        case RESERVA_ASSENTO:
                            atualizarPreco(); // Atualiza o preço na reserva de assento
                            break;
                        case GESTAO_ASSENTOS:
                            visualizarDadosPassageiro(); // Atualiza os dados do usuário na gestão de assentos
                            break;
                        case GESTAO_RESERVAS:
                            atualizarReservaPassageiro(); // Atualiza os dados mostrados na gestão de reservas
                            atualizarPreco(); // Atualiza o preço
                            break;
                    }
                }
            });
            painelAssentos.add(botoes); // Adiciona o botão ao painel
            if (botoes.getActionCommand().endsWith("C")) // Adiciona um espaço para representar o corredor
                painelAssentos.add(Box.createRigidArea(new Dimension(10, 0)));}
    atualizarEstadoBotaoAssento(); // Atualiza o estado dos botões dos assentos
    return painelAssentos; // Retorna o painel de assentos completo
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------Metodos Auxiliares---------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Método para configurar os botões de radio do gestão de assento
    private void botaoradioGestaoAssento(JRadioButton botaoradio) {
        botaoradio.setOpaque(false); // Tornando o botão de rádio transparente
        botaoradio.setBorderPainted(false); // Remove a pintura da borda
        botaoradio.setForeground(Color.BLACK); // Define a cor do texto
        botaoradio.setFocusable(false); // Tornando o botão de rádio não focalizável

        // Criando um novo MouseAdapter para lidar com eventos do rato
        MouseAdapter atividaderato = new MouseAdapter() {
            @Override
            // Sobrescrevendo o método mouseClicked para definir um comportamento personalizado
            public void mouseClicked(MouseEvent e) {
                // Alternando o estado de selecionado do botão de rádio ao clicar
                if (botaoradio.isSelected()) {
                    botaoradio.setSelected(true);
                } else {
                    botaoradio.setSelected(false);
                }
                e.consume(); // Consumindo o evento do mouse
            }
        };
        botaoradio.addMouseListener(atividaderato); // Adicionar o MouseAdapter como um foco de rato ao botão de rádio
        botaoradio.setModel(new DefaultButtonModel() { // Definindo um modelo personalizado para o botão de rádio
            @Override
            // Sobrescrevendo o método setPressed, mas não definindo nenhum comportamento
            public void setPressed(boolean b) {
            }
            @Override
            // Sobrescrevendo o método setArmed, mas não definindo nenhum comportamento
            public void setArmed(boolean b) {
            }
        });
    }

    // Método para configurar os CheckBox do gestão de assento
    private void checkboxGestaoAssento(JCheckBox checkBox, boolean estado) {
        checkBox.setSelected(estado); // Define o estado inicial
        checkBox.setOpaque(false); // Torna a CheckBox transparente
        checkBox.setForeground(Color.BLACK); // Define a cor do texto
        checkBox.setBackground(COR_BACKGROUND); // Define a cor de fundo
        checkBox.setFocusable(false); // Torna a CheckBox não focalizável

        // Remove todos os focos de mouse existentes na CheckBox
        for (MouseListener foco : checkBox.getMouseListeners()) {
            checkBox.removeMouseListener(foco);
        }
        checkBox.addMouseListener(new MouseAdapter() { // Adiciona um novo foco de rato à CheckBox
            @Override
            // Sobrescreve o método mouseClicked para definir um comportamento personalizado
            public void mouseClicked(MouseEvent e) {
                e.consume(); // Consome o mouse click do utilizador
            }
        });
    }


    // Método para configurar um foco para JCheckBox
    private void adicionarFocoCheckBox(JPanel painel, JCheckBox checkBox, GridBagConstraints gbc) {
        checkBox.setOpaque(false); // Torna o JCheckBox transparente
        checkBox.addActionListener(_ -> atualizarPreco()); // Adiciona um foco de ação ao CheckBox para executar o atualizarPreco()
        painel.add(checkBox, gbc); // Adiciona o CheckBox ao painel
    }

    // Método para adicionar um campo de texto e seu campo no painel
    private void adicionarCampoTexto(JPanel painel, String campoTexto, JComponent componente, GridBagConstraints gbc) {
        JLabel campo = new JLabel(campoTexto); // Cria um JLabel com o texto fornecido
        campo.setForeground(Color.BLACK); // Define a cor para preto
        painel.add(campo, gbc); // Adiciona o campo ao painel

        // Verifica se o componente é uma instância de JTextField
        if (componente instanceof JTextField) {
            componente.setBackground(Color.WHITE); // Define a cor de fundo para branco
            componente.setForeground(Color.BLACK); // Define a cor de fundo para preto
            ((JTextField) componente).setCaretColor(Color.BLACK); // Define a cor do cursor do campo de texto para preto
            componente.setBorder(BorderFactory.createLineBorder(Color.GRAY));} // Define a borda do campo de texto para cor cinza
        painel.add(componente, gbc); // Adiciona o componente ao painel com as configurações de layout
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, componente.getPreferredSize().height)); // Define o tamanho maximo do componete
    }

    // Método para criar um campo de texto NIF que aceita apenas números inteiros
    private JTextField campoInteiroNIF(int columns) {
        JTextField campoTexto = new JTextField(columns); // Cria um novo JTextField
        ((AbstractDocument) campoTexto.getDocument()).setDocumentFilter(new FiltroNumeros()); // O filtro é configurado para aceitar apenas valores inteiros
        return campoTexto; // Retorna o campo de texto completo
    }

    // Método para criar um painel status de pagamento com os botões de radio
    private JPanel criarPainelStatusPagamento() {
        JPanel painelStatusPagamento = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Cria um novo JPanel com um FlowLayout alinhado à esquerda
        painelStatusPagamento.setOpaque(false); // Torna o painel transparentepor vez
        ButtonGroup grupoStatusPagamento = new ButtonGroup(); // Cria um grupo de botões para que apenas um botão possa ser selecionado cada vez
        pagoBotaoRadio = new JRadioButton("Pago"); // Cria e configura o botão de rádio para a opção "Pago"
        pagoBotaoRadio.setOpaque(false); // Torna o botão transparente
        naoPagoBotaoRadio = new JRadioButton("Não Pago", true); // Cria e configura o botão de rádio para a opção "Não Pago", e mete como true
        naoPagoBotaoRadio.setOpaque(false); // Torna o botão transparente

        // Adiciona os botões de rádio ao grupo de botões, e ao painel
        grupoStatusPagamento.add(pagoBotaoRadio);
        grupoStatusPagamento.add(naoPagoBotaoRadio);
        painelStatusPagamento.add(pagoBotaoRadio);
        painelStatusPagamento.add(naoPagoBotaoRadio);
        return painelStatusPagamento; // Retorna o painel completo
    }

    // Método para processar a reserva de um assento
    private void processarReserva() {
        Assento assentoSelecionado = getAssentoSelecionado(); // Obtém o assento selecionado
        
        // Verifica se um assento foi selecionado
        if (assentoSelecionado == null) {
            // Exibe uma mensagem de erro se nenhum assento for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um Assento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (campoNomeCompleto.getText().isEmpty()) {
            // Exibe uma mensagem de erro se o nome completo não for inserido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um Nome Completo", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (paisComboBox.getSelectedItem().equals("Selecione um Pais")) {
            // Exibe uma mensagem de erro se nenhum país for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um país", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (campoNIF.getText().isEmpty()) {
            // Exibe uma mensagem de erro se o NIF não for inserido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um NIF", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!nifValido(campoNIF.getText())) {
            // Exibe uma mensagem de erro se o NIF inserido não for válido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um NIF Válido", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (metodoPagamentoComboBox.getSelectedItem().equals("Selecione um Método")) {
            // Exibe uma mensagem de erro se nenhum método de pagamento for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um método de pagamento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém os dados inseridos pelo usuário
        String nome = campoNomeCompleto.getText(); // Obtém o nome completo inserido no campo
        String pais = paisComboBox.getSelectedItem().toString(); // Obtém o país selecionado na ComboBox de países
        long nif = Long.parseLong(campoNIF.getText()); // Converte o NIF inserido no campo de texto para um número long
        boolean seguro = seguroCheckBox.isSelected(); // Verifica se a Checkbox de seguro está true
        boolean bagagemExtra = bagagemExtraCheckBox.isSelected(); // Verifica se a CheckBox de bagagem extra está true
        boolean checkInAutomatico = checkinAutomaticoCheckBox.isSelected(); // Verifica se a CheckBox de check-in automático está marcada
        String metodoPagamento = metodoPagamentoComboBox.getSelectedItem().toString(); // Obtém o método de pagamento selecionado na ComboBox
        boolean statusPagamento = getStatusPagamento(); // Obtém o status do pagamento
        String precoTexto = campoPreco.getText(); // Obtém o texto do campo de preço
        String digitos = precoTexto.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos do preço
        int preco = Integer.parseInt(digitos); // Converte o preço para um número inteiro

        // Processa a reserva
        try {
            // Realiza a reserva e atualiza a interface
            Passageiro passageiro = gestorVoos.reservarAssento(nome, pais, nif, seguro, bagagemExtra, checkInAutomatico, metodoPagamento, statusPagamento, preco, assentoSelecionado);
            assentoSelecionado.reservarAssento(passageiro); // Reserva o assento para o passageiro
            atualizarCorAssento(assentoSelecionado); // Atualiza a cor do assento para mostrar que está reservado
        } catch (NifJaRegistradoException e) {
            // Exibe uma mensagem de erro se o NIF já estiver registrado
            JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.WARNING_MESSAGE);
        }
        clearData(); // Limpa os dados da janela após processar a reserva
    }

    // Método para remover uma reserva de assento
    private void removerReserva() {
        Assento assentoSelecionado = getAssentoSelecionado(); // Obtém o assento selecionado
        if (assentoSelecionado == null) { // Verifica se um assento foi selecionado
            // Exibe uma mensagem de aviso se nenhum assento for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um Assento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        // Cancela a reserva utilizando o NIF fornecido pelo usuário, convertido em long e passado para o método cancelarReserva
        gestorVoos.cancelarReserva(Long.parseLong(campoNIF.getText()));
        clearData(); // Limpa os dados da interface após o cancelamento da reserva
        atualizarCorAssento(assentoSelecionado); // Atualiza a cor do assento para mostrar que a reserva foi cancelada
    }

    // Método para limpar os dados nos campos do reservar assento e gestão de reservas
    private void clearData() {
        campoNIF.setText("0"); // Define o texto do NIF para "0", porque o campo só aceita int
        campoNomeCompleto.setText(""); // Define o texto para ""
        paisComboBox.setSelectedIndex(0); // Define a seleção do ComboBox para o primeiro item "Selecione um pais"
        metodoPagamentoComboBox.setSelectedIndex(0); // Define a seleção do ComboBox para o primeiro item "Selecione um metodo"
        seguroCheckBox.setSelected(false); //Desmarca a CheckBox do seguro
        checkinAutomaticoCheckBox.setSelected(false); // Desmarca a CheckBox do check-in automático
        bagagemExtraCheckBox.setSelected(false); // Desmarca a CheckBox de bagagem extra
        campoPreco.setText("Preço: 0 €"); // Define o texto para "Preço: 0 €"
        pagoBotaoRadio.setSelected(false); // Desmarca o botão de rádio "Pago"
        naoPagoBotaoRadio.setSelected(true); // Marca o botão de rádio "Não Pago" como true
        grupoAssentos.clearSelection(); // Desmarca qualquer assento selecionado no grupo de assentos
    }

    // Método para processar alterações em gestão de reservas
    private void processarAlteracoes() {
        Assento selectedAssento = getAssentoSelecionado(); // Obtém o assento selecionado
        if (selectedAssento == null) { // Verifica se um assento foi selecionado
            // Exibe uma mensagem de aviso se nenhum assento for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um Assento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (campoNomeCompleto.getText().isEmpty()) {
            // Exibe uma mensagem de aviso se o nome completo não for inserido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um Nome Completo", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (paisComboBox.getSelectedItem().equals("Selecione um Pais")) {
            // Exibe uma mensagem de aviso se nenhum país for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um País", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (campoNIF.getText().isEmpty()) {
            // Exibe uma mensagem de aviso se o NIF não for inserido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um NIF", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (!nifValido(campoNIF.getText())) {
            // Exibe uma mensagem de aviso se o NIF inserido não for válido
            JOptionPane.showMessageDialog(this, "Por favor, introduza um NIF Válido", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (metodoPagamentoComboBox.getSelectedItem().equals("Selecione um Metodo")) {
            // Exibe uma mensagem de aviso se nenhum método de pagamento for selecionado
            JOptionPane.showMessageDialog(this, "Por favor, selecione um método de pagamento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém os dados inseridos pelo usuário
        String nome = campoNomeCompleto.getText(); // Obtém o nome completo inserido no campo
        String pais = paisComboBox.getSelectedItem().toString(); // Obtém o país selecionado na ComboBox de países
        long nif = Long.parseLong(campoNIF.getText()); // Converte o NIF inserido no campo de texto para um número long
        boolean seguro = seguroCheckBox.isSelected(); // Verifica se a Checkbox de seguro está true
        boolean bagagemExtra = bagagemExtraCheckBox.isSelected(); // Verifica se a CheckBox de bagagem extra está true
        boolean checkInAutomatico = checkinAutomaticoCheckBox.isSelected(); // Verifica se a CheckBox de check-in automático está marcada
        String metodoPagamento = metodoPagamentoComboBox.getSelectedItem().toString(); // Obtém o método de pagamento selecionado na ComboBox
        boolean statusPagamento = getStatusPagamento(); // Obtém o status do pagamento
        String precoTexto = campoPreco.getText(); // Obtém o texto do campo de preço
        String digitos = precoTexto.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos do preço
        int preco = Integer.parseInt(digitos); // Converte o preço para um número inteiro
        gestorVoos.alterarReserva(nome, pais, nif, seguro, bagagemExtra, checkInAutomatico, metodoPagamento, statusPagamento, preco); // Realiza a alteração da reserva com os dados novos fornecidos
        grupoAssentos.clearSelection(); // Desmarca a seleção do assento
    }

    // Método para retornar à JanelaInicial do programa
    private void voltarJanelaPrincipal() {
        getContentPane().removeAll(); // Remove todos os componentes da janela
        repaint(); // Repinta
        configurarJanelaInicial(); // Chama o método para configurar a janela inicial
        criarPainelVoo(); // Chama o método para criar o painel de voos
        criarPainelCentral(); // Chama o método para criar o painel central
        comboBoxVoos.setSelectedIndex(IndexVooSelecionado); // Define o item selecionado no ComboBox de voos para o índice anteriormento selecionado
        revalidate(); // Verifica as mudanças dos componentes
        repaint(); // Repinta a janela novamente para garantir que todas as atualizações sejam mostradas
    }

    // Método para validar um NIF, utilizado no reserva de assento
    private boolean nifValido(String nif) {
        // Verifica se o NIF é nulo, tem menos de 9 dígitos ou contém caracteres não numéricos
        if (nif == null || nif.length() < 9 || !nif.matches("\\d+"))
            return false;
        // Se o NIF tiver mais de 9 dígitos, considera como válido (para lidar com nifs de outros paises)
        if (nif.length() > 9)
            return true;
        String prefix = nif.substring(0, 2); // Extrai o prefixo dos dois primeiros dígitos do NIF
        // Verifica se o NIF começa com dígitos válidos e se o prefixo corresponde a categorias específicas, pois é para nifs pt-pt
        if (!nif.matches("[12356789][0-9]{8}") && !prefix.matches("(45|5[0-9]|6[0-9]|70|71|72|74|75|77|78|79|90|91|98|99)"))
            return false;
        int sum = 0; // Calcula a soma para o dígito de controlo
        for (int i = 0; i < 8; i++) 
            sum += Character.getNumericValue(nif.charAt(i)) * (9 - i);
        int digitoControlo = 11 - (sum % 11); // Calcula o dígito de controlo com base na soma
        digitoControlo = (digitoControlo >= 10) ? 0 : digitoControlo; // Ajusta o dígito de controlo se for 10 ou mais
        return digitoControlo == Character.getNumericValue(nif.charAt(8)); // Verifica se o ultimo dígito é igual ao dígito de controlo calculado
    }

    // Método para trocar a seleção do voo atual
    private void trocarVoo() {
        int vooNovo = comboBoxVoos.getSelectedIndex(); // Obtém o índice do voo selecionado
        gestorVoos.trocarVooAtual(vooNovo); // Chama o gestor de voos para trocar o voo
        IndexVooSelecionado = vooNovo; // Atualiza a variável que armazena o índice do voo selecionado
        mapaAssentos = gestorVoos.getMapaAssentos(); // Obtém o novo mapa de assentos do voo selecionado
        grupoAssentos = gestorVoos.getGrupoBotoes(); // Obtém o novo grupo de botões de assentos do voo selecionado
    }

    // Método para obter o índice de um valor específico em um array de strings
    private int getIndex(String[] valores, String objectivo) {
        for (int i = 0; i < valores.length; i++ ) { // Itera sobre o array de strings
            if (valores[i].equals(objectivo)) { // Compara cada elemento do array com o valor objetivo
                return i; // Retorna o índice se encontrar uma correspondência
            }}
        return 0; // Retorna 0 se o valor objetivo não for encontrado 
    }

    // Método para verificar o Status do Pagamento
    private boolean getStatusPagamento() {
        // Retorna verdadeiro se o botão "pago" estiver selecionado, caso não é porque está selecionado o "não pago"
        return pagoBotaoRadio.isSelected();
    }

    // Método para obter o assento selecionado
    private Assento getAssentoSelecionado() {
        for (Map.Entry<JToggleButton, Assento> entry : mapaAssentos.entrySet()) { // Itera sobre o mapa de assentos
            if (entry.getKey().isSelected()) { // Verifica se o botão associado a um assento está selecionado
                return entry.getValue(); // Retorna o assento associado se o botão correspondente estiver selecionado
            }}
        return null; // Retorna nulo se nenhum assento estiver selecionado
    }

    // Método para atualizar a cor dos botões de assento com base no status do assento selecionado
    private void atualizarCorAssento(Assento assentoSelecionado) {
        for (Map.Entry<JToggleButton, Assento> entrada : mapaAssentos.entrySet()){ // Itera sobre cada entrada do mapa que associa botões de assento
            JToggleButton botaoAssento = entrada.getKey(); // Obtém o botão e o assento correspondente de cada entrada do mapa
            Assento assento = entrada.getValue();
            
            // Verifica se o assento atual é o assento selecionado
            if (assento.equals(assentoSelecionado) && assento.isOcupado()) { // Se for ocupado aplica as seguintes configurações
                botaoAssento.setBackground(new Color(255, 50, 50)); // Define a cor para vermelho
                botaoAssento.setEnabled(false); // Desabilita o botão
            } else if (!assento.isOcupado()) { // Se não estiver ocupado aplica as seguintes configurações
                botaoAssento.setBackground(assento.getNumeroDoAssento() <= 6 ? Color.YELLOW : Color.GREEN); // Amarelo 1-6 Linha , verde para 7-30
                botaoAssento.setEnabled(true); // Habilita o botão
                botaoAssento.repaint(); // Repinta
            }}
        atualizarEstadoBotaoAssento(); // Chama um método para atualizar o estado dos botões
    }

    // Método para atualizar o estado (habilitado ou desabilitado) dos botões de assento
    private void atualizarEstadoBotaoAssento() {
        for (Map.Entry<JToggleButton, Assento> entry : mapaAssentos.entrySet()) { // Itera sobre cada entrada do mapa que associa botões de assento
            JToggleButton botoes = entry.getKey(); // Obtém o botão e o assento correspondente de cada entrada do mapa
            Assento assento = entry.getValue(); // Obtém o assento correspondente
            if (estadoAtual == PanelState.RESERVA_ASSENTO) { // Verifica o estado atual do painel
                // Se o painel estiver no estado de reserva de assento, habilita o botão apenas se não tiver ocupado 
                botoes.setEnabled(!assento.isOcupado());
            } else {
                // Em outros estados, no gestão de assentos e gestão de reserva, habilita o botão apenas se tiver ocupado
                botoes.setEnabled(assento.isOcupado());
            }
        }
    }
    // Método para atualizar os dados do passageiro na interface gráfica
    private void visualizarDadosPassageiro() {
        Assento selecionado = getAssentoSelecionado(); // Obtém o assento selecionado
        Passageiro passageiro = selecionado.getPassageiro(); // Obtém o passageiro associado ao assento selecionado
        Recibo reciboPassageiro = passageiro.getRecibo(); // Obtém o recibo do passageiro

        // Atualiza informação dos campos da interface com as informações do passageiro
        campoNomeCompleto.setText(passageiro.getNome()); // Obtém o nome completo
        campoPais.setText(passageiro.getPais()); // Obtém o pais
        campoNIF.setText(String.valueOf(passageiro.getNIF())); // Obtém o NIF
        seguroCheckBox.setSelected(reciboPassageiro.hasSeguroViagem()); // Obtém o estado do Seguro
        bagagemExtraCheckBox.setSelected(reciboPassageiro.hasBagagemExtra()); // Obtém o estado do Bagagem Extra
        checkinAutomaticoCheckBox.setSelected(reciboPassageiro.hasCheckinAutomatico()); // Obtém o estado do Check-in Automatico
        campoMetodoPagamento.setText(reciboPassageiro.getMetodoPagamento()); // Obtém o Metodo de Pagamento
        
        // Atualiza o status de pagamento nos botões de rádio, se existirem
        if (pagoBotaoRadio != null)
            pagoBotaoRadio.setSelected(reciboPassageiro.getStatusPagamento());
        if (naoPagoBotaoRadio != null)
            naoPagoBotaoRadio.setSelected(!reciboPassageiro.getStatusPagamento());
    }
    
    // Método para processar as informações da alteração da reserva
    private void atualizarReservaPassageiro() {
        Assento selecionado = getAssentoSelecionado(); // Obtém o assento selecionado
        Passageiro passageiro = selecionado.getPassageiro(); // Obtém o passageiro associado ao assento selecionado
        
        // Verifica se o assento selecionado não está ocupado
        if (!selecionado.isOcupado()) {
            // Exibe uma mensagem de aviso se o assento não estiver ocupado
            JOptionPane.showMessageDialog(this, "Este assento não está ocupado", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (selecionado.getPassageiro() == null) {
            // Exibe uma mensagem de aviso se nenhum passageiro estiver associado ao assento
            JOptionPane.showMessageDialog(this, "Por favor selecione um Assento", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Recibo reciboPassageiro = passageiro.getRecibo(); // Obtém o recibo do passageiro
        
        // Atualiza os campos da interface com as informações do passageiro
        campoNomeCompleto.setText(passageiro.getNome()); // Define o nome completo, caso seja modificado
        paisComboBox.setSelectedIndex(getIndex(paises, passageiro.getPais())); // Define o pais, caso seja modificado
        campoNIF.setText(String.valueOf(passageiro.getNIF())); // NIF não é possivel alterar, pois esta associado a cada assento
        seguroCheckBox.setSelected(reciboPassageiro.hasSeguroViagem()); // Define o estado do Seguro, caso seja modificado
        bagagemExtraCheckBox.setSelected(reciboPassageiro.hasBagagemExtra()); // Define o estado do Bagagem Extra, caso seja modificado
        checkinAutomaticoCheckBox.setSelected(reciboPassageiro.hasCheckinAutomatico()); // Define o estado do Check-in Automatico, caso seja modificado
        metodoPagamentoComboBox.setSelectedIndex(getIndex(metodosPagamento, reciboPassageiro.getMetodoPagamento())); // Define o metodo de pagamento, caso seja modificado
        
        // Atualiza o status de pagamento nos botões de rádio, se existirem
        if (pagoBotaoRadio != null)
            pagoBotaoRadio.setSelected(reciboPassageiro.getStatusPagamento());
        
        if (naoPagoBotaoRadio != null)
            naoPagoBotaoRadio.setSelected(!reciboPassageiro.getStatusPagamento());
        atualizarCorAssento(selecionado); // Atualiza a cor do assento
    }

    // Método para atualizar o preço da reserva
    private void atualizarPreco() {
        int precoBase = 100; // Define o preço base 
        int taxaAdministracao = 5; // Define o preço da taxa de administração
        int precoTotal = precoBase + taxaAdministracao; // Calcula o preço total
        
        // Verifica se a CheckBox do seguro está marcada e, se estiver, adiciona 10 ao preço total
        if (seguroCheckBox != null && seguroCheckBox.isSelected())
            precoTotal += 10;
        Assento assentoAtual = getAssentoSelecionado(); // Obtém o assento atualmente selecionado
        if (assentoAtual != null) { // Verifica se algum assento está selecionado
            if (assentoAtual.getNumeroDoAssento() <= 6) // Se o número do assento selecionado for 6 ou menor, adiciona 15 ao preço total
                precoTotal += 15;
            campoPreco.setText(String.format("Preço: %d €", precoTotal)); // Atualiza o campo de texto do preço com o preço total
        }
    }
}