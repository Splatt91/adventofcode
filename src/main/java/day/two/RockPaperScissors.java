package day.two;

import utility.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;

public class RockPaperScissors {

    public static void main(String[] args) throws IOException {
        firstPart();
        secondPart();
    }

    private static void firstPart() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-two/strategy-guide.txt");
        int sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String [] hands = line.split(" ");
            Hand player1 = Hand.decryptHand(hands[1]);
            Hand player2 = Hand.decryptHand(hands[0]);
            int roundScore = Hand.decideRound(player1, player2);
            sum += roundScore + player1.getValue();
        }
        System.out.println(sum);
    }

    private static void secondPart() throws IOException {
        BufferedReader reader = ResourceReader.getResourceReader("day-two/strategy-guide.txt");
        int sum = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String [] hands = line.split(" ");
            Hand player2 = Hand.decryptHand(hands[0]);
            String strategy = hands[1];
            Hand player1 = Hand.decryptStrategy(player2, strategy);
            int roundScore = Hand.decideRound(player1, player2);
            sum += roundScore + player1.getValue();
        }
        System.out.println(sum);
    }
}
