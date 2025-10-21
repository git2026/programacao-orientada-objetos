import java.io.Serializable;


public interface Passageiro extends Serializable {

    /**
     * Metodo que devolve o nome do Passageiro
     * @return nome do passageiro
     */
    String getNome();

    /**
     * Metodo que altera o nome do Passageiro
     * @param nomeNovo - novo nome do Passageiro
     */
    void setNome(String nomeNovo);

    /**
     * Metodo que devolve o NIF do Passageiro
     * @return o NIF do Passageiro
     */
    long getNIF();

    /**
     * Metodo que devolve o pais do Passageiro
     * @return o pais do Passageiro
     */
    String getPais();

    /**
     * Metodo que altera o pais do Passageiro
     * @param paisNovo novo pais do Passageiro
     */
    void setPais(String paisNovo);

    /**
     * Metodo que devolve o Recibo do Passsageiro
     * @return Recibo do Passageiro
     */
    Recibo getRecibo();

    /**
     * Metodo que atualiza o Recibo do Passageiro
     * @param preco - preço total do Assento
     * @param bagagemExtra - variavel que diz se o passsageiro quer bagagem extra ou não 
     * @param seguroViagem - variavel que diz se o passsageiro quer seguro ou não
     * @param checkInAutomatico - variavel que diz se o passsageiro quer check in outomatico ou não
     * @param metodoPagamento - metodo de pagamento selecionado pelo passageiro
     * @param statusPagamento - status de pagamento
     */
    void atualizarRecibo(int preco, boolean bagagemExtra, boolean seguroViagem, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento);
    
    /**
     * Metodo que devolve o Assento reservado pelo Passageiro atualmente
     * @return Assento reservado pelo Passageiro
     */
    Assento getAssento();
}