package src.JUnit;
import src.domain.classes.*;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TipusDadesNoValid;

import java.io.*;
import java.util.Vector;

//import static org.junit.Assert.*;
//import org.junit.Test;
//import org.junit.runners.*;

public class TeclatTest {
    public static void main(String[] args) {
        crearTeclat();
    }

    //@Test
    public static void Teclat () {
        Teclat t = new Teclat("Teclat1");
    }

    //@Test
    public static void crearTeclat () {
        Teclat t = new Teclat("Teclat1");
        t.crearTeclat();
    }

    public static void modificarTeclat () {
        Teclat t = new Teclat("Teclat1");
        t.modificarTeclat('a', 'b');
        t.modificarTeclat(',', '.');
        t.modificarTeclat('1', '2');
        t.modificarTeclat('a', '1');
        t.modificarTeclat('a', '$');
    }

    public static void visualitzarTeclat () {
        Teclat t = new Teclat("Teclat1");
        t.visualitzarTeclat();
    }

    public static void ToString () {
        Teclat t = new Teclat("Teclat1");
        String result = t.toString();
        System.out.println(result);
    }
}
