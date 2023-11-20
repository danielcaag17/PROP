package src.exceptions;

public class GeneradorNoValid extends Excepcions {
    public String getTipus() {
        return "GeneradorNoValid";
    }
    public GeneradorNoValid() {
        super("El generador no és vàlid");
    }
}