package day.eleven;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class MonkeyBusinesss {

    private static long lcm = 1;

    private static List<Monkey> deepCopyList(List<Monkey> list) {
        List<Monkey> newList = new LinkedList<>();
        for (Monkey monkey : list) {
            newList.add(new Monkey(monkey));
        }
        return newList;
    }

    public static void main(String[] args) throws IOException {
        List<Monkey> partOneList = parseMonkeys();
        List<Monkey> partTwoList = deepCopyList(partOneList);
        // Part one
        solution(partOneList, 20, true);
        // Part two
        solution(partTwoList, 10000, false);
    }

    public static void solution(List<Monkey> monkeyList,
                               int numberOfRounds,
                               boolean isPartOne) {
        for (int i = 0; i < numberOfRounds; i++) {
            for (int ii = 0; ii < monkeyList.size(); ii++) {
                Monkey monkey = monkeyList.get(ii);
                monkey.setInspected(monkey.getInspected() + monkey.getItems().size());
                for (int iii = 0; iii < monkey.getItems().size(); iii++) {
                    monkey.applyOperation(iii);
                    monkey.applyBoredom(iii, isPartOne);
                    int monkeyToTossIndex = monkey.applyTest(iii);
                    monkeyList.get(monkeyToTossIndex)
                            .getItems()
                            .add(monkey.getItems().get(iii));
                }
                monkey.setItems(new LinkedList<>());
            }
        }
        monkeyList.sort(Comparator.comparingLong(Monkey::getInspected).reversed());
        BigInteger maxInspected1 = new BigInteger(String.valueOf(monkeyList.get(0).getInspected()));
        BigInteger maxInspected2 = new BigInteger(String.valueOf(monkeyList.get(1).getInspected()));
        BigInteger levelOfMonkeyBusiness = maxInspected1.multiply(maxInspected2);
        System.out.println(levelOfMonkeyBusiness);
    }

    public static List<Monkey> parseMonkeys() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-eleven/monkeys.txt");
        List<Monkey> monkeyList = new ArrayList<>();
        while (reader.readLine() != null) {
            Monkey monkey = new Monkey();
            // Get starting items
            List<Long> startingItems = Arrays.stream(reader.readLine()
                            .split(":")[1]
                            .split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toList();
            monkey.getItems().addAll(startingItems);
            // Get operation
            String[] operationArgs = reader.readLine()
                    .split(":")[1]
                    .split("=")[1]
                    .trim()
                    .split(" ");
            monkey.setOperationArgs(operationArgs);
            // Get test
            int[] test = new int[3];
            String[] divisible = reader.readLine()
                    .split(":")[1]
                    .split(" ");
            test[0] = Integer.parseInt(divisible[divisible.length - 1]);
            lcm *= test[0];
            String[] monkeyIndexTrue = reader.readLine()
                    .split(":")[1]
                    .split(" ");
            test[1] = Integer.parseInt(monkeyIndexTrue[monkeyIndexTrue.length - 1]);
            String[] monkeyIndexFalse = reader.readLine()
                    .split(":")[1]
                    .split(" ");
            test[2] = Integer.parseInt(monkeyIndexFalse[monkeyIndexFalse.length - 1]);
            monkey.setTestArgs(test);
            monkeyList.add(monkey);
            reader.readLine();
        }
        return monkeyList;
    }

    static class Monkey {
        private List<Long> items;
        private String[] operationArgs;
        private int[] testArgs;
        private long inspected;

        Monkey() {
            items = new LinkedList<>();
            inspected = 0;
        }

        public Monkey(Monkey monkey) {
            this.items = new LinkedList<>();
            this.items.addAll(monkey.getItems());
            this.operationArgs = Arrays.copyOf(monkey.getOperationArgs(), monkey.getOperationArgs().length);
            this.testArgs = Arrays.copyOf(monkey.getTestArgs(), monkey.getTestArgs().length);
            this.inspected = monkey.getInspected();
        }

        public long getInspected() {
            return inspected;
        }

        public void setInspected(long inspected) {
            this.inspected = inspected;
        }

        public int[] getTestArgs() {
            return testArgs;
        }

        public void setTestArgs(int[] testArgs) {
            this.testArgs = testArgs;
        }

        public int applyTest(int itemIndex) {
            int divisible = testArgs[0];
            int monkeyIndexTrue = testArgs[1];
            int monkeyIndexFalse = testArgs[2];

            if ((items.get(itemIndex) % divisible) == 0) {
                return monkeyIndexTrue;
            } else {
                return monkeyIndexFalse;
            }
        }

        public String[] getOperationArgs() {
            return operationArgs;
        }

        public void setOperationArgs(String[] operationArgs) {
            this.operationArgs = operationArgs;
        }

        public void applyOperation(int itemIndex) {
            long arg1 = operationArgs[0].compareToIgnoreCase("old") == 0
                    ? items.get(itemIndex) : Integer.parseInt(operationArgs[0]);
            String operation = operationArgs[1];
            long arg2 = operationArgs[2].compareToIgnoreCase("old") == 0
                    ? items.get(itemIndex) : Integer.parseInt(operationArgs[2]);

            switch (operation) {
                case "*" -> items.set(itemIndex, arg1 * arg2);
                case "/" -> items.set(itemIndex, arg1 / arg2);
                case "+" -> items.set(itemIndex, arg1 + arg2);
                case "-" -> items.set(itemIndex, arg1 - arg2);
            }
        }

        public void applyBoredom(int itemIndex, boolean isPartOne) {
            if(isPartOne) {
                items.set(itemIndex, items.get(itemIndex) / 3);
            } else {
                items.set(itemIndex, items.get(itemIndex) % lcm);
            }
        }

        public List<Long> getItems() {
            return items;
        }

        public void setItems(List<Long> items) {
            this.items = items;
        }

    }
}
