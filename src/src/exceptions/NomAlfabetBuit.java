package src.exceptions;

public class NomAlfabetBuit extends Excepcions {
    public String getTipus() {
        return "NomAlfabetBuit";
    }

    public NomAlfabetBuit() {
        super("El nom d'un Alfabet no pot ser buit.");
    }
    
}
