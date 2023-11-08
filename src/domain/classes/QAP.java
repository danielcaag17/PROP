package src.domain.classes;

public class QAP implements Strategy {
    static float cotaGilmore() {
        // 1r terme => Calculable
        //
        // F1 = Suma del cost de les arestes entre tots els caràcters ja emplaçats en una tecla.
        // Cost(i,j) = [(freq(i,j) + freq(j,i))/2] * dist(i,j)
        // 
        // Exemple:
        //      Solució parcial = [c1, c2, c3, c4]
        //      F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3)
        //           + Cost(c2,c4) + Cost(c3,c4)
       
        
        // 2n + 3r terme => Aproximació => Hungarian Algorithm
        // Hem de crear dues matrius: C1 i C2 => (C1+C2) és el punt de partida del Hungarian
        //
        // Matriu C1:
        //      Files    [i] -> Caràcters no emplaçats
        //      Columnes [j] -> Tecles (posicions) no emplaçades
        //      Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //                cada posició (i,j) de la matriu conté el valor corresponent al cost
        //                de posar el caràcter [i] a la posició [j] en relació amb tots els
        //                caràcters ja emplaçats en la solució parcial.
        //      Exemple:
        //
    }
    public void generarTeclat() {
        // asd    
    }
}
