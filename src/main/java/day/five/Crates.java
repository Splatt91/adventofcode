package day.five;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Crates {

    public static void main(String[] args) throws IOException {
        partOne();
        System.out.println();
        partTwo();
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-five/crates.txt");
        List<Deque<Character>> containerStacks = getContainerStacks(reader);

        for (String line; (line = reader.readLine()) != null;) {
            int[] commands = getCommands(line);
            int numberOfCratesToMove = commands[0];
            int from = commands[1] - 1;
            int to = commands[2] - 1;

            Deque<Character> tempStack = new LinkedList<>();
            for (int i = 0; i < numberOfCratesToMove; i++) {
                tempStack.addLast(containerStacks.get(from).removeLast());
            }

            for (int i = 0; i < numberOfCratesToMove; i++) {
                containerStacks.get(to).addLast(tempStack.removeLast());
            }
        }

        for (int i = 0; i < containerStacks.size(); i++) {
            System.out.print(containerStacks.get(i).getLast());
        }
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-five/crates.txt");
        List<Deque<Character>> containerStacks = getContainerStacks(reader);

        for (String line; (line = reader.readLine()) != null;) {
            int[] commands = getCommands(line);
            int numberOfCratesToMove = commands[0];
            int from = commands[1] - 1;
            int to = commands[2] - 1;

            for (int i = 0; i < numberOfCratesToMove; i++) {
                containerStacks.get(to).addLast(containerStacks.get(from).removeLast());
            }
        }

        for (int i = 0; i < containerStacks.size(); i++) {
            System.out.print(containerStacks.get(i).getLast());
        }
    }

    private static int[] getCommands(String line) {
        String commandStr = line.replaceAll("[^0-9]+", " ");
        return Arrays.stream(commandStr.trim()
                        .split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static List<Deque<Character>> getContainerStacks(BufferedReader reader) throws IOException {
        Deque<String> deque = new LinkedList<>();
        int numberOfStacks = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isBlank()) {
                break;
            }
            if (Character.isDigit(line.charAt(1))) {
                numberOfStacks = Integer.parseInt(line.substring(line.length() - 2).trim());
            } else {
                StringBuilder sb = new StringBuilder();
                int curPos = 0;
                int emptySpaces = 0;
                for (int i = 1; i < line.length(); i += 4) {
                    char c = line.charAt(i);
                    if (Character.isAlphabetic(c)) {
                        if (emptySpaces == 0) {
                            curPos = (i - 1) / 4;
                        } else {
                            curPos = curPos == 0 ? -1 : curPos;
                            curPos = emptySpaces + curPos + 1;
                        }
                        sb.append("%d-%c ".formatted(curPos, c));
                        emptySpaces = 0;
                    } else {
                        emptySpaces++;
                    }
                }
                deque.addLast(sb.toString().trim());
            }
        }

        List<Deque<Character>> containerStacks = new ArrayList<>();
        for (int i = 0; i < numberOfStacks; i++) {
            containerStacks.add(new LinkedList<>());
        }
        while (!deque.isEmpty()) {
            String[] containers = deque.removeLast().split(" ");
            for (String container : containers) {
                String[] posAndMark = container.split("-");
                int pos = Integer.parseInt(posAndMark[0]);
                char mark = posAndMark[1].charAt(0);
                containerStacks.get(pos).addLast(mark);
            }
        }

        return containerStacks;
    }
}
