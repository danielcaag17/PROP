package src.exceptions;

public class MidesDiferents extends Excepcions {
    public String getTipus() {
        return "MidesDiferents";
    }
    public MidesDiferents(int midaAlfabet, int midaLayout) {
        super("La mida del Alfabet seleccionat (" + midaAlfabet + 
              ") i la mida del Layout seleccionat (" + midaLayout + 
              ") no poden ser diferents.");
    }
}