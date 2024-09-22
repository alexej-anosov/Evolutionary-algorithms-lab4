package lab4;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.maths.random.Probability;
import java.util.ArrayList;
import java.util.Random;

public class TspAlg {

    public  double best_route_len;
    public  int best_route_iter;
    public static Random random = new Random();

    public static void main(String[] args) {

        int populationSize = 50; // size of population
        int generations = 100000; // number of generations
        String problem_name = "pma343"; // name of problem or path to input file
        TspAlg tsp = new TspAlg();

        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(problem_name);
        int dims = ((TspFitnessFunction) evaluator).getDim();

        CandidateFactory<TspSolution> factory = new TspFactory(dims); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation()); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.85d));  // Selection operator


        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);


        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                TspSolution best = (TspSolution)populationData.getBestCandidate();
                if (bestFit < tsp.best_route_len) {
                    tsp.best_route_len = bestFit;
                    tsp.best_route_iter = populationData.getGenerationNumber();
                }
                System.out.println("\tBest solution = " + best.toString());
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);
    }
}