package src.JUnit;
import src.domain.classes.*;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TipusDadesNoValid;

import java.io.*;

//import static org.junit.Assert.*;
//import org.junit.Test;
//import org.junit.runners.*;

public class AlfabetTest {

    public static void main(String[] args) throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        readInput();
    }

    //@Test
    public static void readInput() throws FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        
        // if (ta == text) ---------------------------------------------------------
        Alfabet a = new Alfabet("Alfabet1");
        String path = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        a.readInput("text", path);
        System.out.println(a.toString());

        Alfabet a2 = new Alfabet("Alfabet2");
        String path2 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text2.txt";
        a2.readInput("text", path2);
        System.out.println(a2.toString());
        
        Alfabet a3 = new Alfabet("Alfabet3");
        String path3 = "./subgrup-prop32.2/test/exemples_input_alfabet/Text3.txt";
        a3.readInput("text", path3);
        System.out.println(a3.toString());


        // else if (ta == "llista-paraules") -----------------------------------------
        Alfabet a4 = new Alfabet("Alfabet4");
        String path4 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt";
        a4.readInput("llista-paraules", path4);
        System.out.println(a4.toString());

        Alfabet a5 = new Alfabet("Alfabet5");
        String path5 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words2.txt";
        a5.readInput("llista-paraules", path5);
        System.out.println(a5.toString());

        Alfabet a6 = new Alfabet("Alfabet6");
        String path6 = "./subgrup-prop32.2/test/exemples_input_alfabet/Words3.txt";
        a6.readInput("llista-paraules", path6);
        System.out.println(a6.toString());


        // else -----------------------------------------------------------------------
        Alfabet a7 = new Alfabet("Alfabet7");
        a7.readInput("string-qualsevol", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        System.out.println(a7.toString());
        // Valor esperat EXC --> TipusDadesNoValid

        Alfabet a8 = new Alfabet("Alfabet1");
        a8.readInput("12345", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        System.out.println(a8.toString());
        // Valor esperat EXC --> TipusDadesNoValid


        // Altres casos ----------------------------------------------------------------
        Alfabet a9 = new Alfabet("Alfabet9");
        a9.readInput("text", "./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt");
        System.out.println(a9.toString());
        // Valor esperat EXC --> FormatDadesNoValid

        Alfabet a10 = new Alfabet("Alfabet10");
        a10.readInput("text", "./subgrup-prop32.2/test/exemples_input_alfabet/path-incorrecte.txt");
        System.out.println(a10.toString());
        // Valor esperat EXC --> FileNotFoundException

        Alfabet a11 = new Alfabet("Alfabet11");
        a11.readInput("llista-paraules", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        System.out.println(a11.toString());
        // Valor esperat EXC --> FormatDadesNoValid

        Alfabet a12 = new Alfabet("Alfabet12");
        a12.readInput("llista-paraules", "./subgrup-prop32.2/test/exemples_input_alfabet/path-incorrecte.txt");
        System.out.println(a12.toString());
        // Valor esperat EXC --> FileNotFoundException
        
    }
}
