import java.io.Serializable;

public interface Assento extends Serializable{

    /**
     * Metodo para reservar o Assento, metendo o user = passageiro e ocupado = <code>true</code>
     * @param passageiro - Passegeiro a reservar o Assento
     */
    void reservarAssento(Passageiro passageiro);

    /**
     * Liberta o assento, metendo o user = <code>Null</code> e ocupado = <code>false</code>
     */
    void libertarAssento();

    /**
     * Metodo para verificar o status do Assento
     * @return <code>true</code> se estiver ocupado, <code>false</code> em caso contrario
     */
    boolean isOcupado();

    /**
     * Metodo que devolve um int com o numero do Assento
     * @return numero do Assento
     */
    int getNumeroDoAssento();

    /**
     * Metodo que devolve uma String com a letra do Assento
     * @return letra do Assento
     */
    String getLetraDoAssento();

    /**
     * Metodo que devolve o Passageiro a ocupar o Assento
     * @return Passageiro, ou <code>Null</code> se o Assento estiver livre
     */
    Passageiro getPassageiro();
}