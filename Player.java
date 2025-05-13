import java.util.*;

public class Player {
    private String name;
    private int balance;
    private List<Card> hand;
    private int totalGames;
    private int wins;
    private int ties;
    private int blackjacks;
    private int busts;

    public Player() {
        this(100, "Dealer"); // Default dealer with $100
    }

    public Player(int balance, String name) {
        this.name = name;
        this.balance = balance;
        this.hand = new ArrayList<>();
        this.totalGames = 0;
        this.wins = 0;
        this.ties = 0;
        this.blackjacks = 0;
        this.busts = 0;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void resetHand() {
        hand.clear();
    }

    public int getTotal() {
        int total = 0;
        int aceCount = 0;

        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        // Adjust for Aces if total is over 21
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    public void winBet(int amount, boolean blackjack) {
        int winnings = blackjack ? (int)(amount * 1.5) : amount;
        balance += amount + winnings;
    }

    public void loseBet(int amount) {
        balance -= amount;
    }

    public void resolveTie(int amount) {
        balance += amount;
    }

    public void displayPlayerStats() {
        System.out.println();
        System.out.println("Stats for " + name + ":");
        System.out.println("Total Games Played: " + totalGames);
        System.out.println("Final Balance: $" + balance);
        System.out.println("Wins: " + wins);
        System.out.println("Ties: " + ties);
        System.out.println("Blackjacks: " + blackjacks);
        System.out.println("Busts: " + busts);
        System.out.println();
    }

    // Increment methods
    public void incWins() {
        wins++;
    }

    public void incTies() {
        ties++;
    }

    public void incBlackjacks() {
        blackjacks++;
        wins++;
    }

    public void incBusts() {
        busts++;
    }

    public void incTotalGames() {
        totalGames++;
    }
}
