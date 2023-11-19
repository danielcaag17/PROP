package src.domain.classes;

import java.io.FileNotFoundException;

import src.exceptions.FormatDadesNoValid;

public interface StrategyAlfabet {
    public Alfabet read (String path)  throws FormatDadesNoValid, FileNotFoundException;
}
