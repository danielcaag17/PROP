package src.presentation;
import java.io.FileNotFoundException;
import java.util.Map;

import src.domain.controllers.*;
import src.exceptions.AlfabetNoExisteix;
import src.exceptions.LayoutNoExisteix;
import src.exceptions.MidesDiferents;
import src.exceptions.TeclatJaExisteix;
import src.exceptions.TeclatNoExisteix;

public class CtrlPresentacio {
    private IOterminal iot;
    private CtrlDomini cd;

    public CtrlPresentacio() throws FileNotFoundException {
        // Aquí s'ha d'iniciar Factoria, Controlador domini, la visualització (per a la primera entrega potser per terminal)
        Factoria factoriaCtrl = Factoria.getInstance();
        factoriaCtrl.crearControladorDomini();
        cd = factoriaCtrl.getCtrlDomini();
        iot = new IOterminal(this);
        // Inicialització de terminal
        iot.inicialitzaTerminal();
    }

    public String[] getListTeclats() {
        return cd.getListLayouts();
    }

    public String[] getListAlfabets() {
        return cd.getListAlfabets();
    }

    public String[] getListLayouts() {
        return cd.getListLayouts();
    }

    public void crearNouTeclat(String nt, String na, Integer idL) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix, LayoutNoExisteix {
        cd.crearNouTeclat(nt, na, idL);
    }

    public String modificarTeclat(String nt, Map<Character, Character> canvis) throws TeclatNoExisteix {
        return cd.modificarTeclat(nt, canvis);
    }

    public String visualitzarTeclat(String nt) throws TeclatNoExisteix {
        return cd.visualitzarTeclat(nt);
    }
}
