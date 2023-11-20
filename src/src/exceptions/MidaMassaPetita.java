package src.exceptions;

public class MidaMassaPetita extends Excepcions{
    public String getTipus() {
        return "MidaMassaPetita";
    }

    public MidaMassaPetita() {
        super("La mida és massa petita");
    }

    public MidaMassaPetita(String s) {
        super("La mida " + s + " és massa petita.");
    }
}
