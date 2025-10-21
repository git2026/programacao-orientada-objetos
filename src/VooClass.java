import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

public class VooClass implements Voo {
    private Map<JToggleButton, Assento> mapaAssentos; // Assento Botão --> Assento Objeto
    private Map<Long, Passageiro> passageiros; // ID Passageiro --> Passageiro Objeto
    private ButtonGroup grupoAssentos; // Grupo dos butoes dos Assentos do Voo

    public VooClass() {
        this.mapaAssentos = new LinkedHashMap<>();
        this.passageiros = new HashMap<>();
        grupoAssentos = new ButtonGroup();
        inicializarMapaAssentos();
    }

    @Override
    public Passageiro reservarAssento(String nome, String pais, long nif, boolean seguro, boolean bagagemExtra, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco, Assento assento) throws NifJaRegistradoException {
        Passageiro passageiro = passageiros.get(nif);
        
        if (passageiro != null)
            throw new NifJaRegistradoException();
            
        passageiro = new PassageiroClass(nome, nif, pais, assento);
        adicionarPassageiro(passageiro);
        passageiro.atualizarRecibo(preco, bagagemExtra, seguro, checkInAutomatico, metodoPagamento, statusPagamento);
        return passageiro;
    }

    @Override
    public void removerPassageiro(long nifPassageiro) {
        if (passageiros.containsKey(nifPassageiro)) {
            passageiros.remove(nifPassageiro);
        }
    }

    @Override
    public void adicionarPassageiro(Passageiro passageiro) {
        passageiros.put(passageiro.getNIF(), passageiro);
    }
    
    @Override
    public ButtonGroup getButtonGroup() {
        grupoAssentos.clearSelection();
        return grupoAssentos;
    }
    
    @Override
    public Map<JToggleButton, Assento> getAssentoMap() {
        return mapaAssentos;
    }
    
    @Override
    public void alterarReserva(String nome, String pais, long nif, boolean seguro, boolean bagagemExtra,
        boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco) {
        
        Passageiro passageiro = passageiros.get(nif);
        passageiro.setNome(nome);
        passageiro.setPais(pais);
        passageiro.atualizarRecibo(preco, bagagemExtra, seguro, checkInAutomatico, metodoPagamento, statusPagamento);
    }

    @Override
    public void cancelarReserva(long nif) { 
        Passageiro passageiro = passageiros.get(nif);
        Assento assento = passageiro.getAssento();
        assento.libertarAssento();
        passageiros.remove(nif);
    }
    
    private Assento createAssentoInstance(int row, String letra) {
        if (row <= 6) {
            return new Executivo(row, letra);
        } else {
            return new Economico(row, letra);
        }
    }
    
    private void inicializarMapaAssentos() {
        for (int i = 1; i <= 30; i++) {
            for (char c = 'A'; c <= 'F'; c++) {
                String idAssento = i + String.valueOf(c);
                Assento assento = createAssentoInstance(i, String.valueOf(c));
                JToggleButton botao = new JToggleButton(idAssento);
                botao.setActionCommand(idAssento);
                botao.setBackground(assento.isOcupado() ? Color.RED : (i <= 6 ? Color.YELLOW : Color.GREEN));
                botao.setEnabled(!assento.isOcupado());
                grupoAssentos.add(botao);
                mapaAssentos.put(botao, assento);
            }
        }
    }
}