package src.presentation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import src.domain.controllers.*;
import src.domain.classes.Pair;
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
        try {
            cd.restore();
        }
        catch (Exception e) {
            System.out.println("Error al restaurar");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Causa: " + e.getCause());
            e.getStackTrace();
        }
        // iot = new IOterminal(this);
        // Inicialització de terminal
        // iot.inicialitzaTerminal();
        canviVista("PantallaInici", null);
    }

    public String[] getListTeclats() {
        return cd.getListTeclats();
    }

    public char[][] getDistribucio(String nt) throws TeclatNoExisteix {
        return cd.getDistribucio(nt);
    }

    public int[][] getDistribucioLayout(Integer idL) throws LayoutNoExisteix {
        return cd.getDistribucioLayout(idL);
    }

    public String[] getNomTeclats() {
        return cd.getNomTeclats();
    }

    public String[] getListAlfabets() {
        return cd.getListAlfabets();
    }

    public Map<Character, Double> getCharacters(String na) {
        return cd.getCharacters(na);
    }

    public double[] getFrequenciesCharacter(String na, String c) throws EntradaLlegidaMalament {
        return cd.getFrequenciesCharacter(na, c);
    }

    public Character[] getAbecedari(String na) {
        return cd.getAbecedari(na);
    }

    public String[] getNomAlfabets() {
        return cd.getNomAlfabets();
    }

    public String[] getListLayouts() {
        return cd.getListLayouts();
    }

    public Integer[] getNomLayouts() {
        return cd.getNomLayouts();
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

    public void crearNouTeclat(String nt, String na, String ge) throws TeclatJaExisteix, MidesDiferents, AlfabetNoExisteix, IOException {
        cd.crearNouTeclat(nt, na, ge);
    }

    public String modificarTeclat(String nt, ArrayList<Pair<Character, Character>> canvis) throws TeclatNoExisteix, LletraNoTeclat, IOException {
        return cd.modificarTeclat(nt, canvis);
    }

    public String visualitzarTeclat(String nt) throws TeclatNoExisteix {
        return cd.visualitzarTeclat(nt);
    }

    public void esborrarTeclat(String nt) throws TeclatNoExisteix, IOException {
        cd.esborrarTeclat(nt);
    }

    public void afegirAlfabet(String na, String ta, String pf) throws AlfabetJaExisteix, FileNotFoundException, FormatDadesNoValid, TipusDadesNoValid, EntradaLlegidaMalament, IOException {
        cd.afegirAlfabet(na, ta, pf);
    }

    public String visualitzarAlfabet(String na) throws AlfabetNoExisteix {
        return cd.visualitzarAlfabet(na);
    }

    public void esborrarAlfabet(String na) throws AlfabetNoExisteix, IOException {
        cd.esborrarAlfabet(na);
    }

    public void afegirLayout(Integer idL) throws LayoutJaExisteix, MidaMassaPetita, IOException {
        cd.afegirLayout(idL);
    }
    
    public String visualitzarLayout(Integer idL) throws LayoutNoExisteix {
        return cd.visualitzarLayout(idL);
    }

    public void esborrarLayout(Integer idL) throws LayoutNoExisteix, LayoutNoBorrable, IOException {
        cd.esborrarLayout(idL);
    }

    public void guarda() throws IOException {
        cd.saveState();
    }

    public String lastSave() {
        return cd.lastSave();
    }

    public void canviVista(String vista, String elementAMostrar) {
        switch (vista) {
            case "PantallaInici":
                new CtrlPantallaInici();
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
                new CtrlMostrarTeclat(elementAMostrar);
                break;
            case "PreMostrarTeclat":
                new CtrlPreMostrarTeclat(elementAMostrar);
                break;
            case "ModificarTeclat":
                new CtrlModificarTeclat(elementAMostrar);
                break;
            case "AfegirAlfabet":
                new CtrlAfegirAlfabet();
                break;
            case "MostrarAlfabet":
                new CtrlMostrarAlfabet(elementAMostrar);
                break;
            case "AfegirLayout":
                new CtrlAfegirLayout();
                break;
            case "MostrarLayout":
                new CtrlMostrarLayout(elementAMostrar);
                break;
            case "ProvarTeclat":
                new CtrlProvarTeclat(elementAMostrar);
                break;
            default:
                System.out.println("No existeix aquesta pantalla");
                break;
        }
    }

    /**
     * Elimina una instància
     * 
     * @param tipus tipus d'instància eliminada
     * @param clau clau de l'instància eliminada 
     * @param pantalla pantalla on es vol retornar una vegada acabat el diàleg
     */
    public void elimina(String tipus, String clau, JFrame pantalla, String pantallaRetorn) {
        new CtrlEliminar(tipus, clau, pantalla, pantallaRetorn);
    }

    public void Excepcio(JFrame pantalla, String title, String msg) {
        new CtrlPantallaInformativa(pantalla, title, msg);
    }
}
