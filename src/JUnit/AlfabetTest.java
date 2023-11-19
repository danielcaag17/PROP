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
        Alfabet a = new Alfabet("Alfabet1");

        // if (ta == text)
        String path = "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt";
        a.readInput("text", path);
        System.out.println(a.getAbecedari());

        // else if (ta == "llista-paraules")
        //a.readInput("llista-paraules", null);

        // else
        //a.readInput("no-es-cap-tipus-de-dades", null);
    }
}
