package src.domain.controllers;

import src.data.CtrlPersistenciaFile;

public class Factoria {
    private static Factoria singletonFactoria;
    private Factoria(){}
    private CtrlDomini ControladorDomini;
    private CtrlPersistenciaFile ControladorPersistencia;

    public static Factoria getInstance() {
        if (singletonFactoria == null) singletonFactoria = new Factoria();
        return singletonFactoria;
    }

    // Només es crida un cop des de CtrlPresentacio
    public void crearControladorDomini() {
        ControladorDomini = CtrlDomini.getInstance();
    }

    // Retorna el CtrlDomini
    public CtrlDomini getCtrlDomini() {
        return ControladorDomini;
    }

    // Retorna el CtrlPersistencia
    public CtrlPersistenciaFile getCtrlPersistenciaFile() {
        ControladorPersistencia = CtrlPersistenciaFile.getInstance();
        return ControladorPersistencia;
    }
}
