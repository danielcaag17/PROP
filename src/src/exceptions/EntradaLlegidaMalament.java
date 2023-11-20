package src.exceptions;

public class EntradaLlegidaMalament extends Excepcions{
    public String getTipus() {
        return "EntradaLlegidaMalament";
    }

    public EntradaLlegidaMalament() {
        super("L'entrada s'ha llegit malament");
    }

    public EntradaLlegidaMalament(String s) {
        super("La lletra " + s + " de l'entrada no s'ha llegit correctament");
    }
}
