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
        a.readInput("text", "./Alfabet.java");
        System.out.println(a.getAbecedari());

        // else if (ta == "llista-paraules")
        //a.readInput("llista-paraules", null);

        // else
        //a.readInput("no-es-cap-tipus-de-dades", null);
    }
}
