package src.domain.classes;

public class Generador { // segons el patró estrategia, la classe no ha de ser abstracta
    private Strategy S;

    public char[][] generarTeclat(Layout L, Alfabet A) {
        return S.generarTeclat(L, A);   // a definir quina estructura retorna
    }
}
