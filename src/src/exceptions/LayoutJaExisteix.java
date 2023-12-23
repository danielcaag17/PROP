package src.exceptions;

public class LayoutJaExisteix extends Excepcions {
    public String getTipus() {
        return "LayoutJaExisteix";
    }
    public LayoutJaExisteix() {
        super("El Layout ja existeix");
    }
    public LayoutJaExisteix(String s) {
        super("El Layout amb id " + s + " ja existeix.");
    }
}
