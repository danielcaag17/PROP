package src.presentation;
import java.io.FileNotFoundException;
import java.util.Map;

import src.domain.controllers.*;
import src.exceptions.*;

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
        return cd.getListTeclats();
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

    public String modificarTeclat(String nt, Map<Character, Character> canvis) throws TeclatNoExisteix, LletraNoTeclat {
        return cd.modificarTeclat(nt, canvis);
    }

    public String visualitzarTeclat(String nt) throws TeclatNoExisteix {
        return cd.visualitzarTeclat(nt);
    }

    public void esborrarTeclat(String nt) throws TeclatNoExisteix {
        cd.esborrarTeclat(nt);
    }

    public void afegirAlfabet(String na, String ta, String pf) throws AlfabetJaExisteix, FileNotFoundException, FormatDadesNoValid, TipusDadesNoValid, EntradaLlegidaMalament {
        cd.afegirAlfabet(na, ta, pf);
    }

    public String visualitzarAlfabet(String na) throws AlfabetNoExisteix {
        return cd.visualitzarAlfabet(na);
    }

    public void esborrarAlfabet(String na) throws AlfabetNoExisteix {
        cd.esborrarAlfabet(na);
    }

    public void afegirLayout(Integer idL) throws LayoutJaExisteix, MidaMassaPetita {
        cd.afegirLayout(idL);
    }
    
    public String visualitzarLayout(Integer idL) throws LayoutNoExisteix {
        return cd.visualitzarLayout(idL);
    }

    public void esborrarLayout(Integer idL) throws LayoutNoExisteix, LayoutNoBorrable {
        cd.esborrarLayout(idL);
    }

}
