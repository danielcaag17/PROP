package src.presentation;
import java.io.FileNotFoundException;
import java.util.Map;

import src.domain.controllers.*;
import src.exceptions.*;

public class CtrlPresentacio {
    // private IOterminal iot;
    private CtrlDomini cd;
    private static CtrlPresentacio ctrlPresentacio = null;

    public static CtrlPresentacio getInstance() {
        if(ctrlPresentacio == null) ctrlPresentacio = new CtrlPresentacio();
        return ctrlPresentacio;
    }

    private CtrlPresentacio() {
        // Aquí s'ha d'iniciar Factoria, Controlador domini, la visualització (per a la primera entrega potser per terminal)
        Factoria factoriaCtrl = Factoria.getInstance();
        factoriaCtrl.crearControladorDomini();
        cd = factoriaCtrl.getCtrlDomini();
        // iot = new IOterminal(this);
        // Inicialització de terminal
        // iot.inicialitzaTerminal();
        canviVista("PantallaInici");
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

    public String[] getListGeneradors() {
        return cd.getListGeneradors();
    }

    public String[] getListTipusEntrada() {
        return cd.getListTipusEntrada();
    }

    public String toggleStrategy() {
        return cd.toggleStrategy();
    }

    public void crearNouTeclat(String nt, String na, String ge) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix {
        cd.crearNouTeclat(nt, na, ge);
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

    public void guarda() {

    }

    public void canviVista(String vista) {
        switch (vista) {
            case "PantallaInici":
                new CtrlPantallaInici(this);
                break;
            case "LlistaTeclats":
                new CtrlLlistaTeclats();
                break;
            case "LlistaAlfabets":
                new CtrlLlistaAlfabets();
                break;
            case "LlistaLayouts":
                new CtrlLlistaLayouts();
                break;
            case "AfegirTeclat":
                new CtrlAfegirTeclat();
                break;
            case "MostrarTeclat":
                new CtrlMostrarTeclat();
                break;
            case "PreMostrarTeclat":
                new CtrlPreMostrarTeclat();
                break;
            case "ModificarTeclat":
                new CtrlModificarTeclat();
                break;
            case "AfegirAlfabet":
                new CtrlAfegirAlfabet();
                break;
            case "MostrarAlfabet":
                new CtrlMostrarAlfabet();
                break;
            case "AfegirLayout":
                new CtrlAfegirLayout();
                break;
            case "MostrarLayout":
                new CtrlMostrarLayout();
                break;
            default:
                System.out.println("No existeix aquesta pantalla");
                break;
        }
    }

    public void elimina(String tipus, String missatge) {
        new CtrlEliminar(tipus, missatge);
    }
}
