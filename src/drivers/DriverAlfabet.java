package src.drivers;

import src.domain.classes.Alfabet;

public class DriverAlfabet {
    private static Alfabet a1, a2, a3;
    private String pathText1 = "../../test/Text1.txt";      // Text en anglès
    private String pathText2 = "../../test/Text2.txt";      // Text en català
    private String pathText3 = "../../test/Text3.txt";      // Text en castellà
    private String pathWords1 = "../../test/Words1.txt";    // Frecuencies en anglès
    private String pathWords2 = "../../test/Words2.txt";    // Frecuencies en català
    private String pathWords3 = "../../test/Words3.txt";    // Frecuencies en castellà


    public static void main (String[] args) {
        a1 = new Alfabet(null);
        a2 = new Alfabet("Alfabet A2");
        a3 = new Alfabet("Alfabet A3")

        //-------------------------------------------------------------
        // getNom();
        System.out.println("El nom de l'Alfabet es "+ a1.getNom()+"\n");
        System.out.println("El nom de l'Alfabet es "+ a2.getNom()+"\n");
        System.out.println("El nom de l'Alfabet es "+ a3.getNom()+"\n");


        //-------------------------------------------------------------
        // setNom();
        System.out.println("Assignar nom a a1 --> A1");
        a1.setNom("A1");
        System.out.println("El nom de l'Alfabet es "+ a1.getNom()+"\n");


        //-------------------------------------------------------------
        // readInput();
        System.out.println("El nom de l'Alfabet es "+ a1.getNom()+"\n");
        System.out.println("El nom de l'Alfabet es "+ a2.getNom()+"\n");




    }
}
