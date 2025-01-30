import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Rock-Paper-Scissors!");

        // Main game loop: allow multiple single-round games until user quits.
        boolean playAgain = true;
        while (playAgain) {
            // Prompt for user move
            System.out.print("\nEnter your move (rock, paper, or scissors): ");
            String playerMove = scanner.nextLine().toLowerCase();

            // Validate input
            if (!playerMove.equals("rock") &&
                    !playerMove.equals("paper") &&
                    !playerMove.equals("scissors")) {
                System.out.println("Invalid move! Try again.");
                continue; // skip the rest of this loop iteration
            }

            // Computer's random move: 0 -> rock, 1 -> paper, 2 -> scissors
            int computerChoice = random.nextInt(3);
            String computerMove = switch (computerChoice) {
                case 0 -> "rock";
                case 1 -> "paper";
                case 2 -> "scissors";
                // Technically won't happen, but included for completeness
                default -> "rock";
            };

            System.out.println("Computer chose: " + computerMove);

            // Determine the winner of this single round
            String singleRoundResult;
            if (playerMove.equals(computerMove)) {
                singleRoundResult = "tie";
                System.out.println("It's a tie!");
            } else if (
                    (playerMove.equals("rock")     && computerMove.equals("scissors")) ||
                            (playerMove.equals("paper")    && computerMove.equals("rock"))     ||
                            (playerMove.equals("scissors") && computerMove.equals("paper"))
            ) {
                singleRoundResult = "player";
                System.out.println("You win this round!");
            } else {
                singleRoundResult = "computer";
                System.out.println("Computer wins this round!");
            }

            // If the computer just lost (i.e., user won this round),
            // ask if the user wants a two-out-of-three rematch
            if (singleRoundResult.equals("player")) {
                System.out.print("C'mon, two out of three? (y/n): ");
                char response = scanner.nextLine().toLowerCase().charAt(0);

                if (response == 'y') {
                    // The user already has 1 win from this round
                    playBestOfThree(scanner, random, playerMove);
                }
            }

            // Ask if the user wants another single-round game
            System.out.print("\nPlay another single round? (y/n): ");
            char playChoice = scanner.nextLine().toLowerCase().charAt(0);
            playAgain = (playChoice == 'y');
        }

        System.out.println("Thanks for playing! Goodbye.");
    }

    /**
     * Conducts a best-of-three series, where the user starts with 1 win already.
     * If either side reaches 2 total wins, that side is declared the champion.
     */
    private static void playBestOfThree(Scanner scanner, Random random, String previousPlayerMove) {
        // The user begins with 1 point because they just won the single round.
        int userWins = 1;
        int compWins = 0;

        // Keep playing rounds until someone has 2 total wins
        while (userWins < 2 && compWins < 2) {
            // Prompt user for move
            System.out.print("\n[Best of 3] Enter your move (rock, paper, or scissors): ");
            String playerMove = scanner.nextLine().toLowerCase();

            // Validate
            if (!playerMove.equals("rock") &&
                    !playerMove.equals("paper") &&
                    !playerMove.equals("scissors")) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            // Computer move
            int computerChoice = random.nextInt(3);
            String computerMove = switch (computerChoice) {
                case 0 -> "rock";
                case 1 -> "paper";
                case 2 -> "scissors";
                default -> "rock";
            };
            System.out.println("Computer chose: " + computerMove);

            // Determine the round winner
            if (playerMove.equals(computerMove)) {
                System.out.println("It's a tie! No points awarded.");
            } else if (
                    (playerMove.equals("rock")     && computerMove.equals("scissors")) ||
                            (playerMove.equals("paper")    && computerMove.equals("rock"))     ||
                            (playerMove.equals("scissors") && computerMove.equals("paper"))
            ) {
                userWins++;
                System.out.println("You win this round! Current score: You "
                        + userWins + " vs. Computer " + compWins);
            } else {
                compWins++;
                System.out.println("Computer wins this round! Current score: You "
                        + userWins + " vs. Computer " + compWins);
            }
        }

        // Announce the final result of the best-of-three
        if (userWins == 2) {
            System.out.println("\nCONGRATS! You won the best-of-three series!");
        } else {
            System.out.println("\nOh no! The computer won the best-of-three series!");
        }
    }
}