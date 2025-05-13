import java.util.*;

public class BlackjackGame {
    private Deck deck;
    private List<Player> players;
    private Set<String> activePlayers;
    private Player dealer;
    private Map<String, Integer> bets;
    private Map<String, Integer> playerTotalsMap;
    private int dealerTotal;

    public BlackjackGame(List<Player> players) {
        this.players = players;
        this.dealer = new Player();
        this.deck = new Deck();
        this.deck.shuffle();

        this.activePlayers = new HashSet<>();
        for (Player p : players) {
            activePlayers.add(p.getName());
        }

        this.bets = new HashMap<>();
        this.playerTotalsMap = new HashMap<>();
    }

    public void startGame() {
        while (!activePlayers.isEmpty()) {
            System.out.println("There are " + activePlayers.size() + " player(s) in the game.");
            playRound();
            replayCheck();
        }
    }

    private void playRound() {
        prepareRound();                 // Reset hands, bets, and totals
        takeBet();                     // Prompt players to place bets
        dealInitialPlayerCards();        // Deal 2 cards to each player

        dealCards(dealer, 1);
        dealerTotal = dealer.getTotal();
        System.out.println("Dealer's card: " + dealer.getHand().get(0));

        handlePlayerTurns();
        dealerTurn();
        determineWinners(dealerTotal);
    }

    private void prepareRound() {
        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;
            p.incTotalGames();
            p.resetHand();
            bets.put(p.getName(), 0);
            playerTotalsMap.put(p.getName(), 0);
        }

        deck.shuffle();
        dealerTotal = 0;
        dealer.resetHand();
    }

    private void takeBet() {
        Scanner scanner = new Scanner(System.in);

        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;

            int balance = p.getBalance();
            int bet = 0;
            boolean validBet = false;

            System.out.println(p.getName() + " has $" + balance + ". How much would you like to bet?");

            while (!validBet) {
                try {
                    bet = Integer.parseInt(scanner.nextLine());

                    if (validateBet(balance, bet)) {
                        bets.put(p.getName(), bet);
                        validBet = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
        }
    }

    private boolean validateBet(int balance, int bet) {
        if (bet <= balance && bet >= 0) {
            return true;
        } else if (bet > balance) {
            System.out.println("Insufficient funds. Please place a lower bet.");
        } else {
            System.out.println("Invalid bet amount. Please place a valid bet.");
        }
        return false;
    }

    private void dealInitialPlayerCards() {
        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;

            dealCards(p, 2);
            playerTotalsMap.put(p.getName(), p.getTotal());
        }
    }

    private void dealCards(Player p, int amount) {
        for (int i = 0; i < amount; i++) {
            p.addCard(deck.dealCard());
        }
    }

    private void handlePlayerTurns() {
        Scanner scanner = new Scanner(System.in);

        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;

            int total = playerTotalsMap.get(p.getName());

            System.out.println("It is now " + p.getName() + "'s turn.");
            List<Card> hand = p.getHand();
            System.out.println(p.getName() + " cards: " + hand.get(0) + " and " + hand.get(1));

            while (total < 21) {
                System.out.println("Your total is " + total + ". Do you want to hit or stand?");
                String action = scanner.nextLine().trim().toLowerCase();

                if (action.equals("hit")) {
                    Card newCard = deck.dealCard();
                    p.addCard(newCard);
                    System.out.println("You drew a " + newCard);
                    total = p.getTotal();
                } else if (action.equals("stand")) {
                    break;
                } else {
                    System.out.println("Invalid action. Please type 'hit' or 'stand'.");
                }
            }

            if (total == 21) {
                System.out.println("You got a blackjack!");
            } else if (total > 21) {
                System.out.println("You busted with a total of " + total + ".");
            }

            playerTotalsMap.put(p.getName(), total);
        }
    }

    private void dealerTurn() {
        System.out.println("Dealer's card: " + dealer.getHand().get(0));

        while (dealerTotal < 17) {
            Card newCard = deck.dealCard();
            dealer.addCard(newCard);
            System.out.println("Dealer drew a " + newCard);
            dealerTotal = dealer.getTotal();
        }

        System.out.println("Dealer's total is " + dealerTotal);
    }

    private void determineWinners(int dealerTotal) {
        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;

            int playerTotal = playerTotalsMap.get(p.getName());
            int bet = bets.get(p.getName());

            System.out.println("Calculating " + p.getName() + "'s total...");

            if (playerTotal > 21) {
                System.out.println(p.getName() + " busted! Dealer wins.");
                p.loseBet(bet);
                p.incBusts();
            } else if (playerTotal == 21) {
                System.out.println("Blackjack!");
                p.winBet(bet, true);
                p.incBlackjacks();
            } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
                System.out.println("You win!");
                p.winBet(bet, false);
                p.incWins();
            } else if (dealerTotal == playerTotal) {
                System.out.println("It's a tie!");
                p.resolveTie(bet);
                p.incTies();
            } else {
                System.out.println("Dealer wins!");
                p.loseBet(bet);
                p.incBusts();
            }
        }
    }

    private void replayCheck() {
        Scanner scanner = new Scanner(System.in);

        for (Player p : players) {
            if (!isActivePlayer(p.getName())) continue;

            if (p.getBalance() <= 0) {
                System.out.println("Sorry " + p.getName() + ", you have no more money, game over :(");
                closePlayer(p);
            } else if (!promptForReplay(p, scanner)) {
                closePlayer(p);
            } else {
                System.out.println(p.getName() + " has $" + p.getBalance() + " left.");
            }
        }
    }

    private boolean promptForReplay(Player p, Scanner scanner) {
        System.out.print(p.getName() + ", do you want to play again? (yes/no): ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }

    private void closePlayer(Player p) {
        p.displayPlayerStats();
        activePlayers.remove(p.getName());
    }

    private boolean isActivePlayer(String name) {
        return activePlayers.contains(name);
    }

    public static void main(String[] args) {
        Player jacob = new Player(100, "Jacob");
        Player john = new Player(50, "John");
        List<Player> players = new ArrayList<>(Arrays.asList(jacob, john));

        BlackjackGame game = new BlackjackGame(players);
        game.startGame();
    }
}
