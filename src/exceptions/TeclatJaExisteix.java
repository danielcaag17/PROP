package src.exceptions;

public class TeclatJaExisteix extends Excepcions {
    public String getTipus() {
        return "TeclatJaExisteix";
    }
    public TeclatJaExisteix() {
        super("El teclat ja existeix");
    }
    public TeclatJaExisteix(String s) {
        super("El teclat amb nom " + s + " ja existeix.");
    }
}
