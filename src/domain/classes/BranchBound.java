package src.domain.classes;

public class BranchBound implements Strategy {

    private double[][] Frequencies;
    private double[][] Distances;
    private int n_size;

    public BranchBound(double[][] freq_matrix, double[][] dist_matrix) {
        this.Frequencies = freq_matrix;
        this.Distances = dist_matrix;
    }

    public void generarTeclat() {
        // Ignorar de moment 
    }

    private double cost(Pair<int,int> a, Pair<int,int> b) {
        // Cost(a,b) = [(freq(a,b) + freq(a,b))/2] * dist(a,b)
        // On a i b són parelles <id_caracter, id_tecla>
        double freq_avg = (this.Frequencies[a.first][b.first] + this.Frequencies[b.first][a.first])/2;
        return freq_avg*this.Distances[a.second][b.second];
    }

    private int[] lazyBranch() {
        // Lazy Branching algorithm => Similar a DFS
        int[] partial_sol = new int[n_size];
        return partial_sol;
    }

    private double[][] matrixC1() {
        return;
    }

    private double[][] matrixC2() {
        return;
    }

    private double cotaGilmore() {
        // 1r terme => Calculable
        //
        // F1 = Suma del cost de les arestes entre tots els caràcters ja emplaçats en una tecla.
        // Cost(a,b) = [(freq(a,b) + freq(a,b))/2] * dist(a,b)
        // 
        // Exemple:
        //      Solució parcial = [c1, c2, c3, c4] **
        //      F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3)
        //           + Cost(c2,c4) + Cost(c3,c4)
        //
        // ** Apunt: En el vector solució, l'index del vector indica la tecla/posició, i el
        //           contingut de cada índex és el caràcter que correspon a aquella posició.
        //           ex: Solució parcial = [c5, c2, ...]
        //               El caràcter 5 està assignat a la tecla 0
        //               El caràcter 2 està assignat a la tecla 1
        //
        //
        // 2n + 3r terme => Aproximació => Hungarian Algorithm
        // Hem de crear dues matrius: C1 i C2 => (C1+C2) és el punt de partida del Hungarian
        //
        // Matriu C1:
        //      Files    [i] -> Caràcters no emplaçats
        //      Columnes [j] -> Tecles (posicions) no emplaçades
        //
        //      Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //                cada posició (i,j) de la matriu conté el valor corresponent al cost
        //                de posar el caràcter [i] a la posició [j] en relació amb tots els
        //                caràcters ja emplaçats en la solució parcial.
        //      
        //      Exemple:
        //          Solució parcial = [c1, c2, c3, ...]
        //          Per a qualsevol (i,j) de C1 suposem = Sol. [c1, c2, c3, ..., c(i), ...]
        //          C1[i][j] = Cost de les arestes entre c[i] en tecla[j] i totes les tecles
        //                     ja emplaçades en la solució parcial.
        //
        // Matriu C2:
        //      Files    [i] -> Caràcters no emplaçats
        //      Columnes [j] -> Tecles (posicions) no emplaçades
        //
        //      Resultat: Matriu quadrada (N-m)*(N-m) de caràcters i tecles no emplaçades, on
        //                cada posició (i,j) de la matriu conté el valor corresponent al producte
        //                escalar de dos vectors T i D.
        //
        //      Vector T: Vector de freqüències entre caràcter [i] i la resta de caràcters no
        //                emplaçats. Ordre CREIXENT.
        //      Vector D: Vector de distàncies entre tecla [j] i la resta de tecles no emplaçades.
        //                Ordre DECREIXENT.
        //
        //      Exemple: Tenim 5 caràcters i 5 posicions en total = (c1...c5) i (p1...p5)
        //               Solució parcial = [c1, c2, ...] -> (c1 en p1, c2 en p2)
        //               Suposem Punt[i][j] = (c3,p5)
        //               - Vector T = [freq(c3-c4), freq(c3-c5)] -> (Ordre creixent)
        //               - Vector D = [dist(p5-p3), dist(p5,p4)] -> (Ordre decreixent)
        //
        //
        // Fem Hungarian Algorithm amb (C1+C2), i ens retorna una matriu quadrada (N-m)*(N-m)
        // amb una solució "òptima" per a els caràcters i tecles que encara no són emplaçats
        // en la solució parcial. La matriu la podem representar com un vector, tal com en la
        // solució parcial.
        //      # Explicació pas per pas de Hungarian Algorithm i Línies Mínimes en el PDF.
        //
        //
        // Càlcul 2n terme cota Gilmore:
        //      F2 = Sumatori del cost de cada node en la solució Hungarian en relació amb cada
        //           node de la solució parcial.
        //      
        //      Exemple F2:
        //          - Solució parcial   : [c1, c2, c3, .., ..]
        //          - Solució Hungarian : [.., .., .., c4, c5]
        //          F2 = [Cost(c4-c1) + Cost(c4-c2) + Cost(c4-c3)]
        //               + [Cost(c5-c1) + Cost(c5-c2) + Cost(c5-c3)]
        //
        //
        // Càlcul 3r terme cota Gilmore:
        //      F3 = Aplicar F1 sobre la solució proporcionada per Hungarian.
        //      
        //      Exemple F3:
        //          - Solució Hungarian: [.., .., c3, c4, c5]
        //          F3 = Cost(c3-c4) + Cost(c3-c5) + Cost(c4-c5)
        //
        //
        // Finalment, Cota Gilmore => F1 + F2 + F3
        //
        return 0.0;
    }
}
