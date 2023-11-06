package src.domain.controllers;

import java.util.*;

import src.data.*;
import src.domain.classes.*;
import src.exceptions.*;

public class CtrlDomini {
    /*
     * Instàncies de les classes del model
     */
    private CtrlAlfabetFile ctrlAlfabetFile;
    private Generador Generador;
    // private String strategy;
    private HashMap<String, Teclat> Teclats; // Cjt de Teclats, on String és el nom del Teclat
    private HashMap<String, Alfabet> Alfabets; // Cjt d'Alfabets, on String és el nom de l'Alfabet
    private HashMap<String, Layout> Layouts; // Cjt de Layouts, on String és l'id del Layout

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

    // Pre:
    // Post: s'han inicialitzat les instàncies del model i variables del CtrlDomini
    public void init() {
//      ctrlAlfabetFile = CtrlAlfabetFile.getInstance();
        Teclats = new HashMap<String, Teclat>();
        Alfabets = new HashMap<String, Alfabet>();
        Layouts = new HashMap<String, Layout>();
//      strategy = "QuadraticAssignmentProblem"
    }

    /* Funcions de Transacció i de Domini */

    
}
