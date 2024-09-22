package lab4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.stream.Collectors;

public class TspProblemReader {
    private String path;
    private ArrayList<int[]> problem;
    private int dim;

    public TspProblemReader(String problem_name) {
        this.path = "./problems/" + problem_name + ".tsp";
        this.problem = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            List<String> content = lines.collect(Collectors.toList());
            int nodeSectionIndex = content.indexOf("NODE_COORD_SECTION");
            for (int i = nodeSectionIndex + 1; i < content.size(); i++) {
                String line = content.get(i);
                if ("EOF".equals(line)) {
                    break;
                }
                int[] vals = Arrays.stream(line.trim().split("\\s+"))
                        .skip(1)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                problem.add(vals);
            }
        } catch (IOException e) {
            System.out.println("File not found: " + path);
        }
        this.dim = problem.size();
    }

    public int getDim() {
        return dim;
    }

    public int[] getCoordinates(int i) {
        return problem.get(i);
    }
}