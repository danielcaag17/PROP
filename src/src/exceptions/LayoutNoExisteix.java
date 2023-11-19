package src.exceptions;

public class LayoutNoExisteix extends Excepcions {
    public String getTipus() {
        return "LayoutNoExisteix";
    }
    public LayoutNoExisteix(String s) {
        super("El layout amb id " + s + " no existeix.");
    }
}
