package src.domain.controllers;

public class Factoria {
    private static Factoria singletonFactoria;
    private Factoria(){}
    private CtrlDomini ControladorDomini;

    public static Factoria getInstance() {
        if (singletonFactoria == null) singletonFactoria = new Factoria();
        return singletonFactoria;
    }

    // Només es crida un cop des de CtrlPresentacio
    public void crearControladorDomini() {
        ControladorDomini = CtrlDomini.getInstance();
    }
    // potser es poden fusionar funcions.
    public CtrlDomini getCtrlDomini() {
        return ControladorDomini;
    }
}
