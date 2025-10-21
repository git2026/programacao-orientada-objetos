public class ReciboClass implements Recibo{
    private int valorTotal; //Valor total do Recibo
    private String metodoPagamento; //Metodo de pagamento selecionado pelo Passageiro
    private boolean statusPagamento; //Status de pagamento do Recibo
    private boolean bagagemExtra; //Variavel que diz se o user quer bagagem extra ou nao
    private boolean seguroViagem; //Variavel que diz se o user quer seguro de viagem ou nao
    private boolean checkinAutomatico; //Variavel que diz se o user quer checkin automatico ou nao

    public ReciboClass(int precoVoo, boolean bagagemExtra, boolean seguroViagem, boolean checkInAutomatico, String metodoPagamento, boolean statusPagamento) {
        this.valorTotal = precoVoo;
        this.bagagemExtra = bagagemExtra;
        this.seguroViagem = seguroViagem;
        this.checkinAutomatico = checkInAutomatico;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = statusPagamento;
    }

    @Override
    public int getValorTotal() {
        return valorTotal;
    }

    @Override
    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    @Override
    public boolean getStatusPagamento() {
        return statusPagamento;
    }

    @Override
    public boolean hasBagagemExtra() {
        return bagagemExtra;
    }

    @Override
    public boolean hasSeguroViagem() {
        return seguroViagem;
    }

    @Override
    public boolean hasCheckinAutomatico() {
        return checkinAutomatico;
    }

    @Override
    public void setMetodoPagamento(String metodo) {
        this.metodoPagamento = metodo;
    }
}