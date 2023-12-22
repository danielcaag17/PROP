package src.domain.classes;
import java.util.*;


public class Genetic implements Strategy {

    private final int population_size = 3000;
    private final int generations = 1500;
    private final int mutation_percent = 30; // Percentatge MÀXIM de parelles subjectes a mutació
                                             // NO pot ser mai superior al 50% !!!
    private double[][] Frequencies;
    private double[][] Distancies;
    private int max_pair_mutation;
    private int problem_size;
    private Random rand;
    
    private ArrayList<ArrayList<Integer>> Population;
    private double[] Fitness; // The lowest the better

    /**
     * Retorna cost de l'aresta entre (c1,p1) i (c2,p2)
     * c1 i c2 són els IDs de caracter ; p1 i p2 són les posicions (tecles)
     */
    private double edgeCost(int c1, int p1, int c2, int p2) {
        return ((this.Frequencies[c1][c2]+this.Frequencies[c2][c1])/2)*this.Distancies[p1][p2];
    }

    /**
     * F1 = Suma del cost de les arestes entre tots els caràcters ja emplaçats en una tecla.
     * Exemple: Solució parcial = [c1, c2, c3, c4]
     * F1 = Cost(c1,c2) + Cost(c1,c3) + Cost(c1,c4) + Cost(c2,c3) + Cost(c2,c4) + Cost(c3,c4)
     */
    private double fitnessScore(ArrayList<Integer> perm) {
        double res = 0;
        // Per cada element (fins al penúltim) de la solució parcial, obtenir el cost d'aquell
        // element en funció de tots els següents.
        for (int i = 0; i < (problem_size-1); i++) {
            int charID_1 = perm.get(i);
            for (int k = i+1; k < problem_size; k++) {
                int charID_2 = perm.get(k);
                res += edgeCost(charID_1, i, charID_2, k);
            }
        }
        return res;
    }

    /**
     * Retorna un valor INT aleatori entre 0 i el valor de range.
     */
    private int randInt(int range) {
        return this.rand.nextInt(range);
    }

    /**
     * Retorna un valor INT aleatori entre 0 i el valor de range, complint la condicio de 
     * que el valor de retorn no pot ser mai un valor que ja existeix dins del ArrayList exclude.
     */
    private int randIntExcluding(int range, ArrayList<Integer> exclude) {
        int best = randInt(range);
        while (exclude.contains(best)) best = randInt(range);
        return best;
    }
    
    /**
     * Retorna un valor INT aleatori entre 0 i el valor de range, complint la condicio de 
     * que el valor de retorn no pot ser mai igual al valor de exclude.
     */
    private int randIntExcluding(int range, int exclude) {
        int best = randInt(range);
        while (best == exclude) best = randInt(range);
        return best;
    }

    /**
     * Genera un individu de la poblacio de forma aleatoria, per tant retorna un ArrayList
     * amb valors entre 0 i size, ordenats aleatoriament i sense repeticions.
     */
    private ArrayList<Integer> randomIndividual(int size) {
        ArrayList<Integer> perm = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            perm.add(randIntExcluding(size, perm));
        }
        return perm;
    }

    /**
     * Genera una poblacio inicial aleatoria.
     */
    private void setInitialPopulation() {
        this.Population.clear();
        for (int i = 0; i < population_size; i++) {
            this.Population.add(randomIndividual(problem_size));
        }
    }

    /**
     * Actualiza el vector Fitness amb els costos de la poblacio actual.
     */
    private void updateFitness() {
        for (int i = 0; i < population_size; i++) {
            ArrayList<Integer> aux = this.Population.get(i);
            this.Fitness[i] = fitnessScore(aux);
        }
    }

    /**
     * Retorna un vector que conte els indexs del top_percentage% de individus amb menor 
     * cost dintre la poblacio actual.
     */
    private int[] obtainSelection(int top_percentage) {
        // Returns int array containing the indexes of the top_percentage% best individuals
        // in the Population
        int n_indexes = (top_percentage*population_size)/100;
        if (top_percentage == -1) n_indexes = 1;
        
        int[] top_indexes = new int[n_indexes];
       
        double[] clonedFitness = this.Fitness.clone();
        double[] sortedFitness = this.Fitness.clone();
        Arrays.sort(sortedFitness);

        // Tenim el vector de Fitness, on cada index (i) representa el score associat al individu
        // (i) de Population. Ordenem el vector de Fitness, escollim els x millors, busquem els
        // indexos als quals pertanyen i retornem el vector amb els indexos.
        for (int i = 0; i < n_indexes; i++) {
            double aux = sortedFitness[i];
            int index = -1;
            for (int j = 0; j < population_size; j++) {
                if (clonedFitness[j] == aux) {
                    index = j;
                    // Així si tenim 2 scores iguals no s'agafa x2 el mateix index
                    clonedFitness[j] = -1;
                    break;
                }
            }
            top_indexes[i] = index;
        }
        return top_indexes;
    }

    /**
     * Realitza la funcio de Crossover entre dos individus A i B, i retorna un individu fruit
     * de la mescla dels dos pares.
     */
    private ArrayList<Integer> performCrossover(ArrayList<Integer> A, ArrayList<Integer> B) {
        ArrayList<Integer> next = new ArrayList<>(problem_size);
        for (int i = 0; i < problem_size; i++) next.add(-1);

        int nchars_a = (problem_size*50)/100;
        int nchars_b = problem_size - nchars_a;
       
        // Primera meitat dels IDs del teclat (part central del teclat) correspon al parent A
        for (int i = 0; i < nchars_a; i++) next.set(i, A.get(i));

        // Segona meitat (part externa del teclat) correspon als element q falten x emplaçar
        // segons l'ordre en que surten a B
        int next_index = nchars_a;
        for (int i = 0; i < problem_size; i++) {
            int aux = B.get(i);
            if (!next.contains(aux)) {
                next.set(next_index, aux);
                next_index++;
            }
        }
        return next;
    }

    /**
     * Realitza la funcio de mutacio d'un individu, intercanviant la posició d'entre
     * [0 i n_pars] parelles de caràcters escollides aleatòriament.
     */
    private ArrayList<Integer> randomMutation(ArrayList<Integer> input_individual) {
        ArrayList<Integer> out_individual = new ArrayList<>(problem_size);
        out_individual.addAll(input_individual);
        
        int obtained_mutations = (max_pair_mutation*randInt(101))/100;
        for (int i = 0; i < obtained_mutations; i++) {
            int first = randInt(problem_size);
            int second = randIntExcluding(problem_size, first);

            int first_int = out_individual.get(first);
            int second_int = out_individual.get(second);
            
            out_individual.set(first, second_int);
            out_individual.set(second, first_int);
        }
        return out_individual;
    }

    /**
     * Crea la seguent generacio de poblacio a partir de la poblacio acutal, utilitzant
     * les funcions de crosssover i mutacio.
     */
    private ArrayList<ArrayList<Integer>> nextGenPopulation() {
        ArrayList<ArrayList<Integer>> nextGen = new ArrayList<ArrayList<Integer>>(population_size);
        int missing_population = this.population_size;
        // 20% of top population goes directly to the next generation
        int[] indexes_1 = obtainSelection(20);
        for (int i = 0; i < indexes_1.length; i++) {
            nextGen.add(this.Population.get(indexes_1[i]));
            missing_population--;
        }
        // 20% of top population gets mixed with itself and goes to next generation
        int[] indexes_2 = obtainSelection(20);
        for (int i = 0; i < indexes_2.length; i += 2) {
            ArrayList<Integer> parent_a = this.Population.get(indexes_2[i]);
            ArrayList<Integer> parent_b = this.Population.get(indexes_2[i+1]);
            nextGen.add(performCrossover(parent_a, parent_b));
            nextGen.add(performCrossover(parent_b, parent_a));
            missing_population -= 2;
        }
        // 60% of top population gets RANDOMLY mixed with itself and goest to next generation
        int[] indexes_3 = obtainSelection(60);
        for (int i = 0; i < indexes_3.length; i += 2) {
            ArrayList<Integer> parent_a = this.Population.get(indexes_3[i]);
            int next_rand_index = randIntExcluding(indexes_3.length, i);
            ArrayList<Integer> parent_b = this.Population.get(indexes_3[next_rand_index]);
            nextGen.add(performCrossover(parent_a, parent_b));
            nextGen.add(performCrossover(parent_b, parent_a));
            missing_population -= 2;
        }
        // Introduce random mutations to retard convergence
        for (int i = 20; i < population_size; i += 2) {
            ArrayList<Integer> aux = randomMutation(nextGen.get(i));
            nextGen.set(i, aux);
        }
        return nextGen;
    }

    /**
     * Calcula la millor solucio possible, a partir d'una poblacio inicial aleatoria i la
     * evolucio d'aquesta poblacio durant el nombre definit de generacions.
     */
    private ArrayList<Integer> algorithm() {
        setInitialPopulation();
        updateFitness();
        
        for (int i = 0; i < generations; i++) {
            ArrayList<ArrayList<Integer>> auxPopulation = nextGenPopulation();
            this.Population.clear();
            this.Population.addAll(auxPopulation);
            updateFitness();
        }
        
        int bestIndex = obtainSelection(-1)[0];
        ArrayList<Integer> bestSolution = this.Population.get(bestIndex); 
        System.out.println("Cota: " + this.Fitness[bestIndex]);
        return bestSolution;
    }

    /**
     * Retorna el millor teclat generat utilitzant un algorisme genetic.
     */
    public ArrayList<Integer> generarTeclat(double[][] freq_matrix, double[] abs_frequencies, double[][] dist_matrix) {
        // No es fa ús de abs_frequencies per aquest algorisme
        // Totes les matrius són quadrades, de mida problem_size
        this.Frequencies = freq_matrix;
        this.Distancies = dist_matrix;
        this.problem_size = freq_matrix.length;
        this.rand = new Random();
        this.max_pair_mutation = (mutation_percent*problem_size)/100;
        this.Population = new ArrayList<ArrayList<Integer>>(population_size);
        this.Fitness = new double[population_size];
        return algorithm();
    }
}
