import java.util.Scanner;

public class BlackjackGame {
    private final Deck deck = new Deck();
    private final Player player = new Player("Player");
    private final Player dealer = new Player("Dealer");
    private final Scanner scanner = new Scanner(System.in);

    public void play() {
        System.out.println("Welcome to Blackjack!");

        while (true) {
            player.clearHand();
            dealer.clearHand();
            deck.shuffle();

            // Initial deal
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());

            showHands(false);

            // Player turn
            while (true) {
                System.out.print("Hit or Stand? (h/s): ");
                String action = scanner.nextLine().trim().toLowerCase();

                if (action.equals("h")) {
                    player.addCard(deck.dealCard());
                    showHands(false);

                    if (player.isBusted()) {
                        System.out.println("You busted! Dealer wins.");
                        break;
                    }
                } else if (action.equals("s")) {
                    break;
                } else {
                    System.out.println("Invalid input.");
                }
            }

            // Dealer turn
            if (!player.isBusted()) {
                while (dealer.getHandValue() < 17) {
                    dealer.addCard(deck.dealCard());
                }

                showHands(true); //

                if (dealer.isBusted()) {
                    System.out.println("Dealer busted! You win!");
                } else {
                    determineWinner(); //call method
                }
            }

            System.out.print("Play again? (y/n): ");
            if (!scanner.nextLine().trim().toLowerCase().equals("y")) {
                break; //Accept letter regardless of capitalization
            }
        }

        System.out.println("Thanks for playing!");
    }

    private void showHands(boolean showDealer) {
        player.displayHand(true);
        dealer.displayHand(showDealer);
        System.out.println("Your hand value: " + player.getHandValue());
        if (showDealer) {
            System.out.println("Dealer hand value: " + dealer.getHandValue());
        }
        System.out.println();
    }

    private void determineWinner() {
        int playerVal = player.getHandValue();
        int dealerVal = dealer.getHandValue();

        if (playerVal > dealerVal) {
            System.out.println("You win!");
        } else if (playerVal < dealerVal) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
