package day.three;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Rucksacks {

    static Map<Character, Integer> priorities = new HashMap<>();
    static {
        int value = 1;
        for (char c = 'a'; c <= 'z'; c++) {
            priorities.put(c, value++);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            priorities.put(c, value++);
        }
    }

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-three/rucksacks.txt");

        int sum = 0;
        for (String rucksack; (rucksack = reader.readLine()) != null;) {
            int end = rucksack.length();
            int mid = end / 2;

            Set<Character> set = new HashSet<>();
            for (int i = 0; i < mid; i++) {
                char item = rucksack.charAt(i);
                set.add(item);
            }
            for (int i = mid; i < end; i++) {
                char item = rucksack.charAt(i);
                if (set.contains(item)) {
                    set.remove(item);
                    int priority = priorities.get(item);
                    sum += priority;
                }
            }
        }
        System.out.println(sum);
    }

    private static Map<Character, Integer> getAllCharactersInRucksackGroup(String [] rucksackGroup) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i  < rucksackGroup[0].length(); i++) {
            char item = rucksackGroup[0].charAt(i);
            map.putIfAbsent(item, 1);
        }
        for (int i = 0; i  < rucksackGroup[1].length(); i++) {
            char item = rucksackGroup[1].charAt(i);
            map.putIfAbsent(item, 1);
        }
        for (int i = 0; i  < rucksackGroup[2].length(); i++) {
            char item = rucksackGroup[2].charAt(i);
            map.putIfAbsent(item, 1);
        }
        return map;
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-three/rucksacks.txt");

        int sum = 0;
        for (String rucksack1; (rucksack1 = reader.readLine()) != null;) {
            String rucksack2 = reader.readLine();
            String rucksack3 = reader.readLine();

            Map<Character, Integer> map = getAllCharactersInRucksackGroup(new String[] {
                    rucksack1, rucksack2, rucksack3
            });

            for (char item : map.keySet()) {
                if (rucksack1.indexOf(item) != -1
                        && rucksack2.indexOf(item) != -1
                        && rucksack3.indexOf(item) != -1) {
                    sum += priorities.get(item);
                }
            }
        }
        System.out.println(sum);
    }
}
