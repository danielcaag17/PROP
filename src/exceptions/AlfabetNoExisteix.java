package src.exceptions;

public class AlfabetNoExisteix extends Excepcions {
    public String getTipus() {
        return "AlfabetNoExisteix";
    }
    public AlfabetNoExisteix(String s) {
        super("L'alfabet amb nom " + s + " no existeix.");
    }
}