
public class NifJaRegistradoException extends Exception {
    public NifJaRegistradoException() {
        super("Numero NIF já registrado para outro passageiro");
    }
}