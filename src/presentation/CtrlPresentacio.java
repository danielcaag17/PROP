package src.presentation;
import java.io.FileNotFoundException;

import src.domain.controllers.*;

public class CtrlPresentacio {
    private IOterminal ioTerminal;
    private CtrlDomini controlDomini;

    public CtrlPresentacio() throws FileNotFoundException {
        // Aquí s'ha d'iniciar Factoria, Controlador domini, la visualització (per a la primera entrega potser per terminal)
        Factoria factoriaCtrl = Factoria.getInstance();
        factoriaCtrl.crearControladorDomini();
        controlDomini = factoriaCtrl.getCtrlDomini();
        ioTerminal = new IOterminal(this);
        // Inicialització de ctrlDomini
        // Inicialització de terminal
    }
}
