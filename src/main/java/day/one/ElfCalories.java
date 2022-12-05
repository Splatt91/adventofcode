package day.one;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class ElfCalories {

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-one/calories.txt");
        int sum = 0;
        PriorityQueue<Integer> pQueue = new PriorityQueue<>(Collections.reverseOrder());
        for (String line; (line = reader.readLine()) != null;) {
            sum += Integer.parseInt(!line.isBlank() ? line : "0");
            if (!reader.ready()
                    || line.isBlank()) {
                pQueue.add(sum);
                sum = 0;
            }
        }
        int maxSum = pQueue.poll() + pQueue.poll() + pQueue.poll();
        System.out.println(maxSum);
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-one/calories.txt");
        int maxSum = 0;
        int sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            sum += Integer.parseInt(!line.isBlank() ? line : "0");
            if (!reader.ready()
                    || line.isBlank()) {
                if (maxSum < sum) {
                    maxSum = sum;
                }
                sum = 0;
            }
        }
        System.out.println(maxSum);
    }
}
