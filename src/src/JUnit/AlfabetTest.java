package src.JUnit;
import src.domain.classes.*;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TipusDadesNoValid;

import java.io.*;
import java.util.Vector;

//import static org.junit.Assert.*;
//import org.junit.Test;
//import org.junit.runners.*;

public class AlfabetTest {

    public static void main(String[] args) throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException {
        readInput();
    }

    //@Test
    public static void readInput() throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException {
        
        // if (ta == text)
        Alfabet a = new Alfabet("Alfabet1");
        String path = "./test/exemples_input_alfabet/Text1.txt";
        System.out.println(path);
        a.readInput("text", path);
        System.out.println(a.getAbecedari());

        Alfabet a2 = new Alfabet("Alfabet2");
        String path2 = "./test/exemples_input_alfabet/Text2.txt";
        System.out.println(path2);
        a2.readInput("text", path2);
        System.out.println(a2.getAbecedari());
        
        Alfabet a3 = new Alfabet("Alfabet3");
        String path3 = "./test/exemples_input_alfabet/Text3.txt";
        System.out.println(path3);
        a3.readInput("text", path3);
        System.out.println(a3.getAbecedari());

        // else if (ta == "llista-paraules")
        Alfabet a4 = new Alfabet("Alfabet4");
        String path4 = "./test/exemples_input_alfabet/Words1.txt";
        System.out.println(path4);
        a4.readInput("text", path4);
        System.out.println(a4.getAbecedari());

        // else
        Alfabet a7 = new Alfabet("Alfabet7");
        a7.readInput("llista-paraules", "string-qualsevol");
        System.out.println(a7.getAbecedari());
        // Valor esperat EXC --> tipus de dades no valid

        Alfabet b = new Alfabet("Alfabet1");
        b.readInput("llista-paraules", "12345");
        System.out.println(b.getAbecedari());
        // Valor esperat EXC --> tipus de dades no valid
    }
}
