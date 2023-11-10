package src.drivers;

import src.domain.classes.Alfabet;

public class DriverAlfabet {
    private Alfabet a = new Alfabet(null);

    public void getNomTest(){
        System.out.println("El nom de l'Alfabet es "+a.getNom()+"\n");
    }
}
