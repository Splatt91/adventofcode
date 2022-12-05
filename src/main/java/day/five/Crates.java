package day.five;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class Crates {

    public static void main(String[] args) throws IOException {
        partTwo();
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-five/crates.txt");
        // Form stacks of crates
        Stack<String> stack = new Stack<>();
        int numberOfStacks = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isBlank()) {
                break;
            }

            if (Character.isDigit(line.charAt(1))) {
                numberOfStacks = Integer.parseInt(line.substring(line.length() - 2).trim());
                continue;
            }

            StringBuilder sb = new StringBuilder();
            int curPos = 0;
            int emptySpaces = 0;
            for (int i = 1; i < line.length(); i += 4) {
                char c = line.charAt(i);
                if (Character.isAlphabetic(c)) {
                    curPos = emptySpaces == 0 ? ((i - 1) / 4) :
                            emptySpaces
                                    + (curPos == 0 ? -1 : curPos) + 1;
                    sb.append("%d-%c ".formatted(curPos, c));
                    emptySpaces = 0;
                }  else {
                    emptySpaces++;
                }
            }

            stack.push(sb.toString().trim());
        }

        Stack<Character>[] containerStacks = new Stack[numberOfStacks];
        for (int i = 0; i < numberOfStacks; i++) {
            containerStacks[i] = new Stack();
        }
        while (!stack.isEmpty()) {
            String[] containers = stack.pop().split(" ");
            for (String container : containers) {
                String[] posAndMark = container.split("-");
                int pos = Integer.parseInt(posAndMark[0]);
                char mark = posAndMark[1].charAt(0);
                containerStacks[pos].push(mark);
            }
        }

        for (String line; (line = reader.readLine()) != null;) {
            line = line.replaceAll("[^0-9]+", " ");
            int[] commands = Arrays.asList(line.trim().split(" "))
                    .stream().mapToInt(Integer::parseInt).toArray();
            int numberOfCratesToMove = commands[0];
            int from = commands[1] - 1;
            int to = commands[2] - 1;

            Stack<Character> tempStack = new Stack<>();
            for (int i = 0; i < numberOfCratesToMove; i++) {
                tempStack.push(containerStacks[from].pop());
            }

            for (int i = 0; i < numberOfCratesToMove; i++) {
                containerStacks[to].push(tempStack.pop());
            }
        }

        for (int i = 0; i < numberOfStacks; i++) {
            System.out.print(containerStacks[i].peek());
        }
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-five/crates.txt");
        // Form stacks of crates
        Stack<String> stack = new Stack<>();
        int numberOfStacks = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isBlank()) {
                break;
            }

            if (Character.isDigit(line.charAt(1))) {
                numberOfStacks = Integer.parseInt(line.substring(line.length() - 2).trim());
                continue;
            }

            StringBuilder sb = new StringBuilder();
            int curPos = 0;
            int emptySpaces = 0;
            for (int i = 1; i < line.length(); i += 4) {
                char c = line.charAt(i);
                if (Character.isAlphabetic(c)) {
                    curPos = emptySpaces == 0 ? ((i - 1) / 4) :
                            emptySpaces
                            + (curPos == 0 ? -1 : curPos) + 1;
                    sb.append("%d-%c ".formatted(curPos, c));
                    emptySpaces = 0;
                }  else {
                    emptySpaces++;
                }
            }

            stack.push(sb.toString().trim());
        }

        Stack<Character>[] containerStacks = new Stack[numberOfStacks];
        for (int i = 0; i < numberOfStacks; i++) {
            containerStacks[i] = new Stack();
        }
        while (!stack.isEmpty()) {
            String[] containers = stack.pop().split(" ");
            for (String container : containers) {
                String[] posAndMark = container.split("-");
                int pos = Integer.parseInt(posAndMark[0]);
                char mark = posAndMark[1].charAt(0);
                containerStacks[pos].push(mark);
            }
        }

        for (String line; (line = reader.readLine()) != null;) {
            line = line.replaceAll("[^0-9]+", " ");
            int[] commands = Arrays.asList(line.trim().split(" "))
                    .stream().mapToInt(Integer::parseInt).toArray();
            int numberOfCratesToMove = commands[0];
            int from = commands[1] - 1;
            int to = commands[2] - 1;

            for (int i = 0; i < numberOfCratesToMove; i++) {
                containerStacks[to].push(containerStacks[from].pop());
            }
        }

        for (int i = 0; i < numberOfStacks; i++) {
            System.out.print(containerStacks[i].peek());
        }
    }
}
