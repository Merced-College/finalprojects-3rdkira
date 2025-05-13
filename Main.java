// **
// * Jacob Butler
// * Professor Kathleen Kanemoto
// * May 13th, 2025
// * CPSC-39-10106
// Blackjack with Multiple Rounds, Betting System, Handling Aces, Player Statistics, Multiplayer Option
// **

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Player jacob = new Player(100, "Jacob");
        Player john = new Player(50, "John");

        List<Player> players = new ArrayList<>();
        players.add(jacob);
        players.add(john);

        BlackjackGame game = new BlackjackGame(players);
        game.startGame();
    }
}