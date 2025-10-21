import java.io.Serializable;


public interface Recibo extends Serializable {

    /**
     * Metodo que devolve o valor atual do Recibo
     * @return valor atual do Recibo
     */
    int getValorTotal();

    /**
     * Metodo que devolve o metodo que pagamento selecionado
     * @return metodo de pagamento selecionado
     */
    String getMetodoPagamento();

    /**
     * Metodo que devolve o status do pagamento
     * @return status do pagamento
     */
    boolean getStatusPagamento();

    /**
     * Metodo que devolve se o Passageiro quer bagagem extra ou não
     * @return <code>true</code> se o Passageiro quiser bagagem extra, <code>false</code> se não quiser
     */
    boolean hasBagagemExtra();

    /**
     * Metodo que devolve se o Passageiro quer seguro de viagem ou não
     * @return <code>true</code> se o Passageiro quiser seguro de viagem, <code>false</code> se não quiser
     */
    boolean hasSeguroViagem();

    /**
     * Metodo que devolve se o Passageiro quer checkin outomatico ou não
     * @return <code>true</code> se o Passageiro quiser checkin automatico, <code>false</code> se não quiser
     */
    boolean hasCheckinAutomatico();

    /**
     * Metodo que define um novo metodo de pagamento selecionado pelo Passageiro
     * @param metodo - metodo selecionado pelo Passageiro
     */
    void setMetodoPagamento(String metodo);
}