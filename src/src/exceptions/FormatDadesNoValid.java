package src.exceptions;

public class FormatDadesNoValid extends Excepcions {
    public String getTipus() {
        return "FormatDadesNoValid";
    }
    public FormatDadesNoValid() {
        super("El format de les dades del fitxer introduït no es correspon amb el seu tipus.");
    }
    public FormatDadesNoValid(String s) {
        super("El format de les dades del fitxer "+s+" introduït no es correspon amb el seu tipus.");
    }
    public FormatDadesNoValid(String path, String msg) {
        super("El fitxer "+path+" "+msg);
    }
}
