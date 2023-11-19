package src.exceptions;

public class TeclatNoExisteix extends Excepcions {
    public String getTipus() {
        return "TeclatNoExisteix";
    }
    public TeclatNoExisteix(String s) {
        super("El teclat amb nom " + s + " no existeix.");
    }
}
