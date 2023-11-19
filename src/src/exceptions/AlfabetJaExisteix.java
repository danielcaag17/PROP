package src.exceptions;

public class AlfabetJaExisteix extends Excepcions {
    public String getTipus() {
        return "AlfabetJaExisteix";
    }
    public AlfabetJaExisteix() {
        super("L'alfabet ja existeix");
    }
    public AlfabetJaExisteix(String s) {
        super("L'alfabet amb nom " + s + " ja existeix.");
    }
}