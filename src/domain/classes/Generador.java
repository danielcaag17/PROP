package src.domain.classes;

import java.util.Map;

public class Generador { // segons el patró estrategia, la classe no ha de ser abstracta
    private Strategy S;

    public Map<Character, Integer> generarTeclat(Layout L, Alfabet A) {
        return S.generarTeclat(L, A);   // a definir quina estructura retorna
    }
}
