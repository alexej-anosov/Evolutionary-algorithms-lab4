package lab4;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {
    private TspProblemReader problem;

    public TspFitnessFunction(String problem_name) {
        this.problem = new TspProblemReader(problem_name);
    }

    public double getFitness(TspSolution candidate, List<? extends TspSolution> population) {
        double totalRouteDistance = 0.0;
        for (int i = 0; i < problem.getDim(); i++) {
            int startNode = candidate.get(i);
            int endNode = candidate.get((i + 1) % problem.getDim());
            totalRouteDistance += computeDistance(startNode, endNode);
        }
        return totalRouteDistance;
    }

    public boolean isNatural() {
        return false;
    }

    private double computeDistance(int fromNode, int toNode) {
        int[] fromCoordinates = problem.getCoordinates(fromNode);
        int[] toCoordinates = problem.getCoordinates(toNode);

        return calculateDistance(fromCoordinates[0], fromCoordinates[1], toCoordinates[0], toCoordinates[1]);
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public int getDim() {
        return problem.getDim();
    }
}