package src.JUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import src.domain.classes.*;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TipusDadesNoValid;


public class AlfabetTest {

    public static void main(String[] args) {
        readInput();
    }

    private static Alfabet iniAlfabet (String nom, String tipus, String path) {
        Alfabet a = new Alfabet(nom);
        try {
            a.readInput(tipus, path);
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: El fitxer " + path + " no s'ha trobat");
        }
        catch (FormatDadesNoValid e) {
            System.out.println("El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus.");
        }
        catch (TipusDadesNoValid e) {
            System.out.println("El tipus de dades (" + tipus + ") no és vàlid.");
        }
        catch (EntradaLlegidaMalament e) {
            System.out.println("L'entrada no s'ha llegit correctament");
        }
        return a;
    }

    //@Test
    public static void readInput() {
        
        // if (ta == text) ---------------------------------------------------------
        String path1 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        Alfabet a1 = iniAlfabet("Alfabet1", "text", path1);
        System.out.println(a1.toString());

        String path2 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text2.txt";
        Alfabet a2 = iniAlfabet("Alfabet2", "text", path2);
        System.out.println(a2.toString());

        String path3 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text3.txt";
        Alfabet a3 = iniAlfabet("Alfabet3", "text", path3);
        System.out.println(a3.toString());


        // else if (ta == "llista-paraules") -----------------------------------------
        String path4 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt";
        Alfabet a4 = iniAlfabet("Alfabet4", "llista-paraules", path4);
        System.out.println(a4.toString());

        String path5 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words2.txt";
        Alfabet a5 = iniAlfabet("Alfabet5", "llista-paraules", path5);
        System.out.println(a5.toString());

        String path6 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words3.txt";
        Alfabet a6 = iniAlfabet("Alfabet6", "llista-paraules", path6);
        System.out.println(a6.toString());


        // else -----------------------------------------------------------------------
        String path7 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        Alfabet a7 = iniAlfabet("Alfabet7", "string-qualsevol", path7);
        System.out.println(a7.toString());
        // Valor esperat EXC --> TipusDadesNoValid
        // "El tipus de dades (" + tipus + ") no és vàlid."

        String path8 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        Alfabet a8 = iniAlfabet("Alfabet8", "12345", path8);
        System.out.println(a8.toString());
        // Valor esperat EXC --> TipusDadesNoValid
        // "El tipus de dades (" + tipus + ") no és vàlid."
       

        // Altres casos ----------------------------------------------------------------
        String path9 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt";
        Alfabet a9 = iniAlfabet("Alfabet9", "text", path9);
        System.out.println(a9.toString());
        // Valor esperat EXC --> FormatDadesNoValid
        // "El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus."
        
        String path10 = "./subgrup-prop32.2/test/exemples_input_alfabet/path-incorrecte.txt";
        Alfabet a10 = iniAlfabet("Alfabet10", "text", path10);
        System.out.println(a10.toString());
        // Valor esperat EXC --> FileNotFoundException
        // "ERROR: El fitxer " + path + " no s'ha trobat"

        String path11 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        Alfabet a11 = iniAlfabet("Alfabet11", "llista-paraules", path11);
        System.out.println(a11.toString());
        // Valor esperat EXC --> FormatDadesNoValid
        // "El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus."
        
        String path12 = "./subgrup-prop32.2/test/exemples_input_alfabet/path-incorrecte.txt";
        Alfabet a12 = iniAlfabet("llista-paraules", "text", path12);
        System.out.println(a12.toString());
        // Valor esperat EXC --> FileNotFoundException
        // "ERROR: El fitxer " + path + " no s'ha trobat"
        
        
    }
}
