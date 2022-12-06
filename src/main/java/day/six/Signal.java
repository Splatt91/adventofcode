package day.six;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Signal {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-six/signal.txt");
        String line = reader.readLine();
        int startOfPacket = 4;
        int startOfMessage = 14;
        partOne(line, startOfPacket);
        partTwo(line, startOfMessage);
    }

    public static int findMarker(String line, int numberOfDifferentCharacters) {
        int marker = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            marker = i + 1;
            if (!map.containsKey(c)) {
                map.put(c, i);
                if (map.size() == numberOfDifferentCharacters) {
                    break;
                }
            } else {
                int index = map.get(c);
                map.clear();
                for (int ii = index + 1; ii <= i; ii++) {
                    c = line.charAt(ii);
                    map.put(c, ii);
                }
            }
        }
        return marker;
    }

    public static void partOne(String line, int startOfPacket) {
        int marker = findMarker(line, startOfPacket);
        System.out.println(marker);
    }

    public static void partTwo(String line, int startOfMessage) {
        int marker = findMarker(line, startOfMessage);
        System.out.println(marker);
    }

}
