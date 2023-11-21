package src.drivers;

import java.io.FileNotFoundException;
import java.util.Scanner;

import src.domain.classes.Alfabet;
import src.domain.classes.Generador;
import src.domain.classes.Layout;
import src.domain.classes.Teclat;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.GeneradorNoValid;
import src.exceptions.LletraNoTeclat;
import src.exceptions.TipusDadesNoValid;

public class DriverTeclat {
    public static void main (String[] args) throws LletraNoTeclat, GeneradorNoValid, FormatDadesNoValid, TipusDadesNoValid, FileNotFoundException, EntradaLlegidaMalament {
        Alfabet A = new Alfabet("Alfabet de prova");
        A.readInput("text", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");
        Layout L = new Layout(26);
        Generador G = new Generador("Branch&bound");
        Teclat T = new Teclat("Teclat de prova", L, A, G);
        Scanner sc = new Scanner(System.in);
        int func = 0;
        Boolean primeraOpt = false;
        while (func != 7){
            System.out.println("Selecciona una de les funcionalitats:");
            System.out.println("\t1. CrearTeclat amb Alfabet i Layout definits");
            System.out.println("\t2. VeureAlfabet (toString method)");
            System.out.println("\t3. VeureLayout (toString method)");
            System.out.println("\t4. ModificarTeclat");
            System.out.println("\t5. obteInfo (toString method)");
            System.out.println("\t6. CrearTeclat Nou");
            System.out.println("\t7. Sortir");
            func = sc.nextInt();
            switch(func){
                case 1:
                    T.crearTeclat();
                    primeraOpt = true;
                    break;
                case 2: 
                    System.out.println(A.toString());
                    break;
                case 3: 
                    System.out.println(L.toString());
                    break;
                case 4: 
                    if (! primeraOpt) System.out.println("Primer has de fer un CrearTeclat"); 
                    else {
                        System.out.println("Indica dues lletres a intercanviar");    
                        String a = sc.next();
                        String b = sc.next();
                        T.modificarTeclat(a.charAt(0), b.charAt(0));
                        System.out.println("Teclat modificat");
                    }
                    break;
                case 5: 
                    if (! primeraOpt) System.out.println("Primer has de fer un ReadInput"); 
                    else System.out.println(T.toString());
                    break;
                case 6: 
                    T = new Teclat("nou");
                    break;
                case 7:
                    System.out.println("Sortint del driver");
                    sc.close();
                    break;
                default:
                    System.out.println("Funcionalitat equivocada");
                    break;
            }
            System.out.println("\n");
        }
    }
}
