package src.exceptions;

public class LletraNoTeclat extends Excepcions{
    public String getTipus() {
        return "LletraNoTeclat";
    }

    public LletraNoTeclat() {
        super("La lletra no pertany al Teclat");
    }

    public LletraNoTeclat(String s) {
        super("La lletra " + s + " no pertany al Teclat");
    }
}
