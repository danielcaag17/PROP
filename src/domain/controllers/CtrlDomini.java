package src.domain.controllers;

import src.data.*;
import src.domain.*;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    private static CtrlDomini singletonDomini;

    // Pre:
    // Post: s'ha creat una instància de controlador de domini
    public CtrlDomini() {
        init();
    }

    public static CtrlDomini getInstance() {
        if(singletonDomini == null) singletonDomini = new CtrlDomini();
        return singletonDomini;
    }

    public void init() {
        // Inicialització de les diverses instàncies del model
    }

}
