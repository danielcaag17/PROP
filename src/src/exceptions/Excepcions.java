package src.exceptions;

@SuppressWarnings("serial") // potser es pot comentar aixó

public abstract class Excepcions extends Exception {
    public abstract String getTipus();
    public Excepcions() {
        super();
    }
    public Excepcions(String s) {
        super(s);
    }
}
