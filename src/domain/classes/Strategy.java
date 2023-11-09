package src.domain.classes;

import java.util.Map;

// import src.domain.controllers.*;

public interface Strategy {
    public Map<Character, Integer> generarTeclat(Layout L, Alfabet A); // a definir quina Estructura retorna
}
