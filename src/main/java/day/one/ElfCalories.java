package day.one;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;

public class ElfCalories {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-one/calories.txt");

        int maxSum = 0;
        int sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            if (line.isBlank()) {
                if (maxSum < sum) {
                    maxSum = sum;
                }
                sum = 0;
            } else {
                sum += Integer.parseInt(line);
            }
        }

        System.out.println(maxSum);
    }
}
