package src.exceptions;

public class MidesDiferents extends Excepcions {
    public String getTipus() {
        return "MidesDiferents";
    }
    public MidesDiferents(String midaAlfabet, String midaLayout) {
        super("La mida del Alfabet seleccionat (" + midaAlfabet + 
              ") i la mida del Layout seleccionat (" + midaLayout + 
              ") no poden ser diferents.");
    }
}