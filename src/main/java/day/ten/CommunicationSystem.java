package day.ten;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;

public class CommunicationSystem {

    private static final Integer CYCLE_SKIP = 40;

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-ten/operations.txt");
        int x = 1;
        int cycle = 0;
        StringBuilder pixels = new StringBuilder();
        for (String line; (line = reader.readLine()) != null;) {
            String[] inputs = line.split(" ");
            String operation = inputs[0];
            int val = inputs.length > 1 ? Integer.parseInt(inputs[1]) : 0;
            int cycleLoop = 0;
            int spritePos = x - 1;
            if (operation.compareToIgnoreCase("noop") == 0) {
                cycleLoop = 1;
            }
            if (operation.compareToIgnoreCase("addx") == 0) {
                cycleLoop = 2;
            }

            while (cycleLoop > 0) {
                int pixelPos = cycle % CYCLE_SKIP;
                cycle += 1;

                // Draw pixels
                if (pixelPos >= spritePos
                        && pixelPos < (spritePos + 3) ) {
                    pixels.append("#");
                } else {
                    pixels.append(".");
                }

                if ((cycle % CYCLE_SKIP) == 0) {
                    System.out.println(pixels);
                    pixels = new StringBuilder();
                }
                cycleLoop -= 1;
            }
            x += val;
        }
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-ten/operations.txt");
        int x = 1;
        int cycle = 0;
        int signalStrengthCheck = 20;
        int signalStrength = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] inputs = line.split(" ");
            String operation = inputs[0];
            int val = inputs.length > 1 ? Integer.parseInt(inputs[1]) : 0;
            int cycleLoop = 0;
            if (operation.compareToIgnoreCase("noop") == 0) {
                cycleLoop = 1;
            }
            if (operation.compareToIgnoreCase("addx") == 0) {
                cycleLoop = 2;
            }

            while (cycleLoop > 0) {
                cycle += 1;
                if (cycle == signalStrengthCheck) {
                    signalStrength = signalStrength + signalStrengthCheck * x;
                    signalStrengthCheck += CYCLE_SKIP;
                }
                cycleLoop -= 1;
            }
            x += val;
        }
        System.out.println(signalStrength);
    }
}
