package lab4;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int numberOfCrossoverPoints, Random random) {
        ArrayList<TspSolution> children = new ArrayList<>();
        int dim = p1.dim;

        int pointA = random.nextInt(dim);
        double alpha =  Math.pow(random.nextDouble(), 0.2);
        int pointB = (int)(pointA * alpha + dim * (1 - alpha));

        int[] c1 = new int[dim];
        int[] c2 = new int[dim];
        HashSet<Integer> c1Genes = new HashSet<>();
        HashSet<Integer> c2Genes = new HashSet<>();

        for (int i = pointA; i <= pointB; i++) {
            c1[i] = p1.get(i);
            c2[i] = p2.get(i);
            c1Genes.add(c1[i]);
            c2Genes.add(c2[i]);
        }

        int indexC1 = (pointB + 1) % dim;
        int indexC2 = (pointB + 1) % dim;

        for (int i = 0; i < dim; i++) {
            int p2Gene = p2.get((pointB + 1 + i) % dim);
            if (!c1Genes.contains(p2Gene)) {
                c1[indexC1] = p2Gene;
                indexC1 = (indexC1 + 1) % dim;
            }

            int p1Gene = p1.get((pointB + 1 + i) % dim);
            if (!c2Genes.contains(p1Gene)) {
                c2[indexC2] = p1Gene;
                indexC2 = (indexC2 + 1) % dim;
            }
        }

        children.add(new TspSolution(c1));
        children.add(new TspSolution(c2));

        return children;
    }
}