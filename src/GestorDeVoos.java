import java.io.Serializable;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;


public interface GestorDeVoos extends Serializable {

    /**
     * Metodo que altera o voo selecionado 
     * @param indiceVoo - index do novo voo selecionado
     */
    void trocarVooAtual (int indiceVoo);

    /**
     * Metodo simples para reservar um Assento
     * @param nome - nome do passageiro
     * @param pais - pais do passageiro
     * @param nif - nif unico do passageiro
     * @param seguro - variavel que diz se o passsageiro quer seguro ou não
     * @param bagagemExtra - variavel que diz se o passsageiro quer bagagem extra ou não
     * @param checkInAutomatico - variavel que diz se o passsageiro quer check in outomatico ou não
     * @param metodoPagamento - metodo de pagamento selecionado pelo passageiro
     * @param statusPagamento - status do pagamento
     * @param preco - custo total da reserva do Assento
     * @param assento - Assento reservado pelo Passageiro
     * @return objecto do Passageiro que reservo o Assento
     * @throws NifJaRegistradoException quando o NIF usado já está registrado noutro assento
     */
    Passageiro reservarAssento (String nome, String pais, long nif, boolean seguro, boolean bagagemExtra, 
        boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco, Assento assento) throws NifJaRegistradoException;

    /**
     * Metodo para guardar o estado atual dos Voos
     */
    void guardar();

    /**
     * Metodo que devolve os botoes dos Assentos 
     * @return grupo com os botoes dos Assentos do voo atual
     */
    ButtonGroup getGrupoBotoes();

    /**
     * Metodo que devolve o mapa dos Assentos do voo atual
     * @return mapa dos Assentos do Voo atual
     */
    Map<JToggleButton, Assento> getMapaAssentos();

    /**
     * @param nome - nome do Passageiro
     * @param pais - pais do Passageiro
     * @param nif - NIF do Passageiro
     * @param seguro - variavel que diz se o passsageiro quer seguro ou não
     * @param bagagemExtra - variavel que diz se o passsageiro quer bagagem extra ou não
     * @param checkInAutomatico - variavel que diz se o passsageiro quer check in outomatico ou não
     * @param metodoPagamento - metodo de pagamento selecionado pelo passageiro
     * @param statusPagamento - status do pagamento
     * @param preco - custo total da reserva do Assento
     */
    void alterarReserva(String nome, String pais, long nif, boolean seguro, boolean bagagemExtra,
            boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco);

    /**
     * Metodo para cancelar a reserva do Passageiro com o NIF dado
     * @param nif - NIF do Passageiro a cancelar a sua reserva
     */
    void cancelarReserva(long nif);
}