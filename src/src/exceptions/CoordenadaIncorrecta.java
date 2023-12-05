package src.exceptions;

import src.domain.classes.Pair;

public class CoordenadaIncorrecta extends Excepcions{
    public String getTipus() {
        return "CoordenadaIncorrecta";
    }

    public CoordenadaIncorrecta() {
        super("La coordenada introduïda no té una id vàlida a la posició apuntada o és d'una posició incorrecta.");
    }

    public CoordenadaIncorrecta(Pair<Integer, Integer> coord) {
        super("La coordenada (" + coord.first + ", " + coord.second + ") no té una id vàlida a la posició apuntada o és d'una posició incorrecta.");
    }
}
