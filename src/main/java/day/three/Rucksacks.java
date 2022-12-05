package day.three;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Rucksacks {

    public static void main(String[] args) throws IOException {
        partOne();
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-three/rucksacks.txt");

        Map<Character, Integer> priorities = new HashMap<>();
        int value = 1;
        for (char c = 'a'; c <= 'z'; c++) {
            priorities.put(c, value++);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            priorities.put(c, value++);
        }

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
}
