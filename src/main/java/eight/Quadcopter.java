package eight;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quadcopter {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-eight/trees.txt");
        List<List<Integer>> treeMatrix = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            treeMatrix.add(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).boxed().toList());
        }
        partOne(treeMatrix);
        partTwo(treeMatrix);
    }


    public static void partTwo(List<List<Integer>> treeMatrix) {
        int n = treeMatrix.get(0).size();
        int m = treeMatrix.size();
        int maxScenicScore = Integer.MIN_VALUE;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m - 1; j++) {
                int[] scenicScores = new int[] {0, 0, 0, 0};
                int currentTree = treeMatrix.get(i).get(j);

                // Check top
                for (int k = i - 1; k >= 0; k--) {
                    int topTree = treeMatrix.get(k).get(j);
                    scenicScores[0]++;
                    if (currentTree <= topTree) {
                        break;
                    }
                }
                // Check right
                for (int k = j + 1; k < m; k++) {
                    int rightTree = treeMatrix.get(i).get(k);
                    scenicScores[1]++;
                    if (currentTree <= rightTree) {
                        break;
                    }
                }

                // Check bottom
                for (int k = i + 1; k < n; k++) {
                    int bottomTree = treeMatrix.get(k).get(j);
                    scenicScores[2]++;
                    if (currentTree <= bottomTree) {
                        break;
                    }
                }

                // Check left
                for (int k = j - 1; k >= 0; k--) {
                    int leftTree = treeMatrix.get(i).get(k);
                    scenicScores[3]++;
                    if (currentTree <= leftTree) {
                        break;
                    }
                }

                maxScenicScore = Math.max(maxScenicScore, Arrays.stream(scenicScores).reduce(1, (a, b) -> a * b));
            }
        }
        System.out.println(maxScenicScore);
    }

    public static void partOne(List<List<Integer>> treeMatrix) {
        int n = treeMatrix.get(0).size();
        int m = treeMatrix.size();
        int visibleTrees = 2 * n + 2 * m - 4;
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                boolean[] visibilities = new boolean[] {true, true, true, true};
                int currentTree = treeMatrix.get(i).get(j);

                // Check top
                for (int k = i - 1; k >= 0; k--) {
                    int topTree = treeMatrix.get(k).get(j);
                    if (currentTree <= topTree) {
                        visibilities[0] = false;
                        break;
                    }
                }
                // Check right
                for (int k = j + 1; k < m; k++) {
                    int rightTree = treeMatrix.get(i).get(k);
                    if (currentTree <= rightTree) {
                        visibilities[1] = false;
                        break;
                    }
                }

                // Check bottom
                for (int k = i + 1; k < n; k++) {
                    int bottomTree = treeMatrix.get(k).get(j);
                    if (currentTree <= bottomTree) {
                        visibilities[2] = false;
                        break;
                    }
                }

                // Check left
                for (int k = j - 1; k >= 0; k--) {
                    int leftTree = treeMatrix.get(i).get(k);
                    if (currentTree <= leftTree) {
                        visibilities[3] = false;
                        break;
                    }
                }

                for (boolean isVisible : visibilities) {
                    if (isVisible) {
                        visibleTrees++;
                        break;
                    }
                }
            }
        }
        System.out.println(visibleTrees);
    }
}
