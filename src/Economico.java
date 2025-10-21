public class Economico implements Assento {
    private int numeroDoAssento; //Numero do respectivo Assento
    private String letraDoAssento; //Letra do respectivo Assento
    private boolean ocupado; //Variavel representante do estado do Assento
    private Passageiro passageiro; //Passageiro que ocupa o Assento

    public Economico(int numeroDoAssento, String letraDoAssento) {
        this.numeroDoAssento = numeroDoAssento;
        this.letraDoAssento = letraDoAssento;
        this.ocupado = false;
        this.passageiro = null;
    }

    @Override
    public void reservarAssento(Passageiro passageiro) {
        this.passageiro = passageiro;
        ocupado = true;
    }

    @Override
    public void libertarAssento() {
        ocupado = false;
        passageiro = null;
    }

    @Override
    public boolean isOcupado() {
        return ocupado;
    }

    @Override
    public int getNumeroDoAssento() {
        return numeroDoAssento;
    }

    @Override
    public String getLetraDoAssento(){
        return letraDoAssento;
    }

    @Override
    public Passageiro getPassageiro() {
        return passageiro;
    }
}