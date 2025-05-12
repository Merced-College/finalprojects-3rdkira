import java.util.*;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public int getHandValue() {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                aces++;
            }
        }

        // Adjust for aces if over 21
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void displayHand(boolean showAll) {
        System.out.print(name + "'s hand: ");
        for (int i = 0; i < hand.size(); i++) {
            if (!showAll && name.equals("Dealer") && i == 0) {
                System.out.print("[Hidden] ");
            } else {
                Card card = hand.get(i);
                System.out.print(card.getRank() + " of " + card.getSuit() + " ");
            }
        }
        System.out.println();
    }
}
