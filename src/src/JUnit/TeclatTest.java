package src.JUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import src.domain.classes.*;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.LletraNoTeclat;
import src.exceptions.TipusDadesNoValid;

public class TeclatTest {
    String result1 = "x p l v z i w u q\n c y f e t k h g\n s d b m o a n r j";
    String result2 = "x p l v z i w u g\n c y f e t k h q\n s d b m o a n r j";
    String result3 = "x p l v z i w u q\n c y f e t k h g\n s d b m o a n r j";
    Layout L = new Layout(26);
    Alfabet A = new Alfabet("Alfabet test");
    Generador G = new Generador("Branch&bound");
    char[][] teclat;


    @Test
    public void test1 () {
        A = iniAlfabet("Alfabet test", "text", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        Teclat T = new Teclat("Teclat test", L, A, G);
        teclat = T.getDistribucioCharacters();

        assertEquals("Test 1: Correcte\n", Arrays.toString(teclat), result1);
    }
    
    @Test
    public void test2 () {
        A = iniAlfabet("Alfabet test", "text", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        Teclat T = new Teclat("Teclat test", L, A, G);
        try {
            T.modificarTeclat('q', 'g');
        }
        catch (LletraNoTeclat e) {
            System.out.println("Alguna lletra no pertany al Teclat");
        }
        
        teclat = T.getDistribucioCharacters();

        assertEquals("Test 2: Correcte\n", Arrays.toString(teclat), result2);
    }

    @Test
    public void test3 () {
        A = iniAlfabet("Alfabet test", "text", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        Teclat T = new Teclat("Teclat test", L, A, G);
        try {
            T.modificarTeclat('x', 'j');
        }
        catch (LletraNoTeclat e) {
            System.out.println("Alguna lletra no pertany al Teclat");
        }
        try {
            T.modificarTeclat('x', 'j');
        }
        catch (LletraNoTeclat e) {
            System.out.println("Alguna lletra no pertany al Teclat");
        }
        
        teclat = T.getDistribucioCharacters();

        assertEquals("Test 2: Correcte\n", Arrays.toString(teclat), result3);
    }

    
    private Alfabet iniAlfabet (String nom, String tipus, String path) {
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
}
