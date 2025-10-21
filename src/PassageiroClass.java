
public class PassageiroClass implements Passageiro {
    private String nome; //Nome do Passageiro
    private long nif; // NIF do Passageiro
    private String pais; //Pais do Passageiro
    private Recibo recibo; //Recibo do Passageiro
    private Assento assento; //Assento reservado pelo Passageiro

    public PassageiroClass(String nome, long nif, String pais, Assento assento) {
        this.nome = nome;
        this.nif = nif;
        this.pais = pais;
        this.assento = assento;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Assento getAssento() {
        return this.assento;
    }

    @Override
    public long getNIF() {
        return nif;
    }

    @Override
    public String getPais() {
        return pais;
    }

    @Override
    public void setNome(String nomeNovo) {
        this.nome = nomeNovo;
    }

    @Override
    public void setPais(String paisNovo) {
        this.pais = paisNovo;
    }

    @Override
    public Recibo getRecibo() {
        return recibo;
    }

    @Override
    public void atualizarRecibo(int precoVoo, boolean bagagemExtra, boolean seguroViagem, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento) {
        this.recibo = new ReciboClass(precoVoo, bagagemExtra, seguroViagem, checkInAutomatico, metodoPagamento, statusPagamento);
    }
}