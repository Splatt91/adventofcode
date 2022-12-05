package day.four;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;

public class Assigments {

    record Section(int left, int right) { }

    private static Section createSection(String s) {
        String[] leftAndRight = s.split("-");
        return new Section(Integer.parseInt(leftAndRight[0]),
                Integer.parseInt(leftAndRight[1]));
    }

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    public static void partTwo() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-four/assigments.txt");
        int pairs = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] sections = line.split(",");
            Section elfSection1 = createSection(sections[0]);
            Section elfSection2 = createSection(sections[1]);
            if (!((elfSection1.left() < elfSection2.left()
                    && elfSection1.right() < elfSection2.left())
                    || (elfSection1.left() > elfSection2.left()
                    && elfSection1.left() > elfSection2.right()))) {
                pairs += 1;
            }
        }
        System.out.println(pairs);
    }

    public static void partOne() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-four/assigments.txt");
        int pairs = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] sections = line.split(",");
            Section elfSection1 = createSection(sections[0]);
            Section elfSection2 = createSection(sections[1]);
            if ((elfSection1.left() <= elfSection2.left()
                    && elfSection1.right() >= elfSection2.right())
                    || (elfSection1.left() >= elfSection2.left()
                    && elfSection1.right() <= elfSection2.right())) {
                pairs += 1;
            }
        }
        System.out.println(pairs);
    }
}
