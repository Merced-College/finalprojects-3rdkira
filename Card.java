public class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        switch (rank) {
            case "Ace":
                return 11; // Default to 11; Player logic should handle Ace being 1 or 11
            case "2": case "3": case "4": case "5":
            case "6": case "7": case "8": case "9": case "10":
                return Integer.parseInt(rank);
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
