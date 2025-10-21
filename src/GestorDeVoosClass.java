import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import java.io.EOFException;


public class GestorDeVoosClass implements GestorDeVoos {
    private List<Voo> voos; //Lista com os Voos do programa
    private Voo vooAtual; //Voo selecionado atualmento
    private static final String[] NOME_FICHEIRO = {"PT_ES.txt", "FR_DE.txt", "IT_UK.txt"}; //Array com os nomes dos ficheiros com os dados de cada Voo

    public GestorDeVoosClass() {
        this.voos = new ArrayList<>();
        for (String nome : NOME_FICHEIRO)
            criarVoo(nome);
        this.vooAtual = voos.get(0);
    }

    @Override
    public void trocarVooAtual (int index) {
        this.vooAtual = voos.get(index);
    }

    @Override
    public Passageiro reservarAssento(String nome, String pais, long id, boolean seguro, boolean bagagemExtra, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco, Assento assento) throws NifJaRegistradoException {
        return vooAtual.reservarAssento(nome, pais, id, seguro, bagagemExtra, checkInAutomatico, metodoPagamento, statusPagamento, preco, assento);
    }

    @Override
    public void guardar() {
        if (voos.size() != NOME_FICHEIRO.length) {
            System.err.println("O numero de ficheiros e os nomes dos voos são diferentes");
            return;
        }
        for (int i = 0; i < voos.size(); i++) {
            guardarVoo(voos.get(i), NOME_FICHEIRO[i]);
        }
    }
    
    @Override
    public ButtonGroup getGrupoBotoes() {
        return vooAtual.getButtonGroup();
    }

    @Override
    public Map<JToggleButton, Assento> getMapaAssentos() {
        return vooAtual.getAssentoMap();
    }

    @Override
    public void alterarReserva(String nome, String pais, long nif, boolean seguro, boolean bagagemExtra,
        boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco) {
        
        vooAtual.alterarReserva(nome, pais, nif, seguro, bagagemExtra, checkInAutomatico, metodoPagamento, statusPagamento, preco);
    }

    @Override
    public void cancelarReserva(long nif) {
        vooAtual.cancelarReserva(nif);
    }

    /**
     * Metodo para escrever os dados de um dado Voo para um ficheiro com o nome dado
     * @param voo - voo a ser guardado
     * @param nomeFicheiro - nome do ficheiro onde o Voo vai ser guardado
     */
    private void guardarVoo(Voo voo, String nomeFicheiro) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            outputStream.writeObject(voo);
        } catch (IOException e) {
            System.err.println("Erro a guardar o ficheiro: " + nomeFicheiro);
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo para ler os dados de um voo de um dado ficheiro, ou criar um novo voo caso o ficheiro nao exista
     * @param nomeFicheiro - nome do ficheiro com os dados do Voo
     */
    private void criarVoo(String nomeFicheiro) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            Voo voo = (Voo) inputStream.readObject();
            voos.add(voo);
        } catch (FileNotFoundException | EOFException e) {
            voos.add(new VooClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}