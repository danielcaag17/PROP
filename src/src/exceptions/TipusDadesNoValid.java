package src.exceptions;

public class TipusDadesNoValid extends Excepcions {
    public String getTipus() {
        return "TipusDadesNoValid";
    }
    public TipusDadesNoValid() {
        super("El tipus de dades no és vàlid.");
    }
    public TipusDadesNoValid(String s) {
        super("El tipus de dades ("+s+") no és vàlid.");
    }
}
