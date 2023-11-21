package src.domain.classes;

import java.io.FileNotFoundException;

import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;

public interface StrategyAlfabet {

    /**
     * Pre:
     * Post: es retorna l'Alfabet caculat amb l'entrada doanada
     * 
     * @param path string que indica on està l'entrada que defineix l'Alfabet
     * 
     * @return l'Alfabet resultat de l'entrada donada
     */
    public Alfabet read (String path)  throws FormatDadesNoValid, FileNotFoundException, EntradaLlegidaMalament;
}
