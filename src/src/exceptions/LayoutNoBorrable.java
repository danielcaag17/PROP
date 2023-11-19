package src.exceptions;

public class LayoutNoBorrable extends Excepcions {
    public String getTipus() {
        return "LayoutJaExisteix";
    }
    public LayoutNoBorrable() {
        super("El Layout no es pot borrar");
    }
    public LayoutNoBorrable(String s) {
        super("El Layou amb id " + s + " no es pot borrar.");
    }
}
