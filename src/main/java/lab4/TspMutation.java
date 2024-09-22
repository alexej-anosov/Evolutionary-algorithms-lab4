package lab4;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    private static final int PERCENT_OF_INVERT = 20;

    @Override
    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        for (TspSolution candidate : population) {
            TspSolution newCandidate = new TspSolution(candidate);

            int pointA = random.nextInt(newCandidate.dim);
            double alpha =  Math.pow(random.nextDouble(), 0.2);
            int pointB = (int)(pointA * alpha + newCandidate.dim * (1 - alpha));

            if (random.nextInt(100) < PERCENT_OF_INVERT) {
                newCandidate.invert(pointA, pointB);
            } else {
                newCandidate.swap(pointA, pointB);
            }

            population.set(population.indexOf(candidate), newCandidate);
        }
        return population;
    }
}