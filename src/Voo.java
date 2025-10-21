import java.io.Serializable;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;


public interface Voo extends Serializable {

        /**
         * Metodo que registra um novo Passageiro, no Voo
         * @param passageiro novo Passageiro a ser registrado
         */
        void adicionarPassageiro(Passageiro passageiro);

        /**
         * Metodo que remove o Passageiro com o NIF dado
         * @param nifPassageiro - NIF do passageiro a ser removido
         */
        void removerPassageiro(long nifPassageiro);

        /**
         * Metodo que devolve o grupo de butoes dos Assentos do Voo
         * @return butoes dos Assentos do Voo
         */
        ButtonGroup getButtonGroup();

        /**
         * Metodo que devolve o mapa dos Assentos do Voo
         * @return mapa dos Assentos do Voo
         */
        Map<JToggleButton, Assento> getAssentoMap();

        /**
         * Metodo para reservar um Assento
         * @param nome - nome do passageiro
         * @param pais - pais do passageiro
         * @param nif - nif unico do passageiro
         * @param seguro - variavel que diz se o passsageiro quer seguro ou não
         * @param bagagemExtra - variavel que diz se o passsageiro quer bagagem extra ou não
         * @param checkInAutomatico - variavel que diz se o passsageiro quer check in outomatico ou não
         * @param metodoPagamento - metodo de pagamento selecionado pelo passageiro
         * @param statusPagamento - status do pagamento
         * @param preco - custo total da reserva do Assento
         * @param assento  - Assento reservado pelo Passageiro
         * @return objecto do Passageiro que reservo o Assento
         * @throws NifJaRegistradoException quando o NIF usado já está registrado noutro assento
         */
        Passageiro reservarAssento(String nome, String pais, long nif, boolean seguro, 
                boolean bagagemExtra, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento, int preco, Assento assento) throws NifJaRegistradoException;

        /**
         * Metodo para alterar uma reserva
         * @param nome - nome do passageiro
         * @param pais - pais do passageiro
         * @param nif - nif unico do passageiro
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
         * Metodo para cancelar uma reserva, feita pelo Passageiro com o NIF dado
         * @param nif NIF do Passageiro a cnacelar a reserva
         */
        void cancelarReserva(long nif);
}