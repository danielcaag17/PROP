package src.drivers;

import java.io.FileNotFoundException;
import java.util.Scanner;

import src.domain.classes.Alfabet;
import src.domain.classes.Generador;
import src.domain.classes.Layout;
import src.domain.classes.Teclat;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.Excepcions;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.GeneradorNoValid;
import src.exceptions.LletraNoTeclat;
import src.exceptions.TipusDadesNoValid;

public class DriverTeclat {
    // Paths d'exemple per l'opció 6
    // ./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt     --> text (mida = 26)
    // ./subgrup-prop32.2/test/exemples_input_alfabet/Words1.txt    --> llista-paraules (mida = 26)

    public static void main (String[] args)  {
        Alfabet A = iniAlfabet("Alfabet de prova", "text", "./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt");

        Layout L = new Layout(26);
        Generador G = new Generador("Branch&bound");
        Teclat T = new Teclat("Teclat de prova");
        T = new Teclat("Teclat de prova", L, A, G);
        Scanner sc = new Scanner(System.in);
        int func = 0;
        Boolean primeraOpt = false;
        while (func != 7){
            System.out.println("Selecciona una de les funcionalitats: ");
            System.out.println("\t1. CrearTeclat amb Alfabet i Layout definits");
            System.out.println("\t2. VeureAlfabet (toString method)");
            System.out.println("\t3. VeureLayout (toString method)");
            System.out.println("\t4. ModificarTeclat");
            System.out.println("\t5. obteInfo (toString method)");
            System.out.println("\t6. CrearTeclat Nou");
            System.out.println("\t7. Sortir");
            System.out.println("\n(Només es guardarà un Teclat, un Alfabet i un Layout per cada cop que es crea un nou Teclat)");
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
                        System.out.println("Indica dues lletres a intercanviar: ");    
                        String a = sc.next();
                        String b = sc.next();
                        try {
                            T.modificarTeclat(a.charAt(0), b.charAt(0));
                        }
                        catch (LletraNoTeclat e) {
                            System.out.println("Alguna lletra no pertany al Teclat");
                        }
                        System.out.println("Teclat modificat");
                    }
                    break;
                case 5: 
                    if (! primeraOpt) System.out.println("Primer has de fer un ReadInput"); 
                    else System.out.println(T.toString());
                    break;
                case 6: 
                    System.out.println("Primer indica la mida del Layout:");
                    int mida = sc.nextInt();
                    L = new Layout(mida);

                    System.out.println("Indica el nom de l'Alfabet:");
                    String nomA = sc.next();
                    A = new Alfabet(nomA);
                    System.out.println("Indica el format d'entrada:");
                    System.out.println("1 --> text");
                    System.out.println("2 --> llista-paraules");
                    int opc = sc.nextInt();
                    sc.nextLine();
                    if (opc == 1) {
                        System.out.println("Indica el path:");
                        String path = sc.nextLine();
                        A = iniAlfabet(nomA, "text", path);
                        System.out.println("Input llegit");
                    }
                    else if (opc == 2) {
                        System.out.println("Indica el path:");
                        String path = sc.nextLine();
                        A = iniAlfabet(nomA, "llista-paraules", path);
                        System.out.println("Input llegit");
                    }
                    else System.out.println("Opció incorrecte");

                    if (A.getSize() != L.getSize()) System.out.println("[!] > Les mides de l'Alfabet seleccionat i del Layout han de ser iguals."); 

                    System.out.println("Indica el nom del Teclat: ");
                    String nom = sc.next();
                    T = new Teclat(nom, L, A, G);
                    T.crearTeclat();
                    primeraOpt = true;
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

    private static Alfabet iniAlfabet (String nom, String tipus, String path) {
        Alfabet a = new Alfabet(nom);
        try {
            a.readInput(tipus, path);
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: El fitxer ./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt no s'ha trobat");
        }
        catch (FormatDadesNoValid e) {
            System.out.println("El format de les dades del fitxer ./subgrup-prop32.2/test/exemples_input_alfabet/Text1.txt no s'ha trobat introduït no es correspon amb el seu tipus.");
        }
        catch (TipusDadesNoValid e) {
            System.out.println("El tipus de dades (text) no és vàlid.");
        }
        catch (EntradaLlegidaMalament e) {
            System.out.println("L'entrada no s'ha llegit correctament");
        }
        return a;
    }
}
