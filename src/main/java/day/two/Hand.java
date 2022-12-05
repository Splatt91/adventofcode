package day.two;

public enum Hand {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private int value;

    Hand(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Hand decryptHand(String encryptedHand) {
        switch (encryptedHand) {
            case "A", "X":
                return ROCK;
            case "B", "Y":
                return PAPER;
            case "C", "Z":
                return SCISSORS;
            default:
                break;
        }
        throw new RockPaperScissorsException();
    }

    public static int decideRound(Hand player1, Hand player2) {
        if (player1 == player2) {
            return 3;
        }

        if (player1 == ROCK
                && player2 == SCISSORS) {
            return 6;
        }

        if (player1 == PAPER
                && player2 == ROCK) {
            return 6;
        }

        if (player1 == SCISSORS
                && player2 == PAPER) {
            return 6;
        }

        return 0;
    }

    public static Hand decryptStrategy(Hand hand, String strategy) {
        switch (strategy) {
            case "Y":
                return hand;
            case "X":
                if (hand == ROCK) {
                    return SCISSORS;
                }
                if (hand == PAPER) {
                    return ROCK;
                }
                if (hand == SCISSORS) {
                    return PAPER;
                }
                break;
            case "Z":
                if (hand == ROCK) {
                    return PAPER;
                }
                if (hand == PAPER) {
                    return SCISSORS;
                }
                if (hand == SCISSORS) {
                    return ROCK;
                }
                break;
            default:
                break;
        }
        throw new RockPaperScissorsException();
    }
}

