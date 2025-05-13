import java.util.*;

public class Deck {
    private List<Card> cards;
    private int currentCardIndex;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King", "Ace"
        };

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        currentCardIndex = 0;
    }

    public void shuffle() {
        Collections.shuffle(cards);
        currentCardIndex = 0;
    }

    public Card dealCard() {
        if (currentCardIndex < cards.size()) {
            return cards.get(currentCardIndex++);
        } else {
            System.out.println("Deck is out of cards. Reshuffling...");
            shuffle();
            return dealCard();
        }
    }

    public boolean isEmpty() {
        return currentCardIndex >= cards.size();
    }
}
