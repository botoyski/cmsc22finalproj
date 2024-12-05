import java.util.ArrayList;
import java.util.List;

public class GameRules {
    private Player player;
    private Player banker;
    private Deck deck;
    private double playerBalance;
    private List<Bet> sideBets; // List to store all side bets
    private boolean mainBetPlaced; // Flag to track if a main bet is placed

    public GameRules(double initialBalance) {
        this.deck = new Deck(); // Create and shuffle the deck
        this.player = new Player(false); // Player is not the banker
        this.banker = new Player(true); // Banker
        this.playerBalance = initialBalance; // Initialize player balance
        this.sideBets = new ArrayList<>(); // Initialize the side bet list
        this.mainBetPlaced = false; // Initially, no main bet is placed
    }

    // Place a main bet (Player, Banker, Tie)
    public void dealCards() {
        // Deal two cards each to the player and banker
        player.addCardToHand(deck.drawCard());
        banker.addCardToHand(deck.drawCard());
        player.addCardToHand(deck.drawCard());
        banker.addCardToHand(deck.drawCard());
    }

    public Player getPlayer() {
        return player;
    }

    // Method to get the banker instance
    public Player getBanker() {
        return banker;
    }

    public boolean placeMainBet(Bet mainBet) {
        if (mainBet != null) {
            this.mainBetPlaced = true; // Main bet is now placed
            System.out.println("Main Bet placed: " + mainBet.getBetType());
            return true;
        }
        return false;
    }

    // Add side bet to the list, only if a main bet has been placed
    public boolean addSideBet(Bet sideBet) {
        if (!mainBetPlaced) {
            System.out.println("You must place a main bet first before placing a side bet.");
            return false; // Prevent side bet if main bet hasn't been placed
        }

        // Otherwise, allow the side bet
        sideBets.add(sideBet);
        System.out.println("Side Bet placed: " + sideBet.getBetType());
        return true;
    }

    // Resolve all side bets
    public void resolveSideBets() {
        double totalSideBetPayout = 0;

        // Iterate over all side bets and resolve them
        for (Bet bet : sideBets) {
            totalSideBetPayout += bet.sideBet(player, banker);
        }

        System.out.println("Total Side Bet Payout: $" + totalSideBetPayout);
        playerBalance += totalSideBetPayout; // Add the total side bet payout to player's balance
    }

    // Calculate P and B Bonuses (based on specific Dragon Bonus odds)
    private double calculateBonus(int winnerScore, int loserScore, String side, int amount) {
        int gap = winnerScore - loserScore; // Calculate score gap
        boolean isNatural = winnerScore == 8 || winnerScore == 9;
        double payout = 0;

        // Apply Dragon Bonus odds based on score gap and natural status
        if (gap == 9 && !isNatural) {
            payout = amount * 30;
        } else if (gap == 8 && !isNatural) {
            payout = amount * 10;
        } else if (gap == 7 && !isNatural) {
            payout = amount * 6;
        } else if (gap == 6 && !isNatural) {
            payout = amount * 4;
        } else if (gap == 5 && !isNatural) {
            payout = amount * 2;
        } else if (gap == 4 && !isNatural) {
            payout = amount * 1;
        } else if (isNatural) {
            payout = amount * 1; // Natural win
        } else if (gap == 0 && isNatural) {
            payout = amount * 0; // Natural Tie
        }

        return payout;
    }

    public void applyThirdCardRule() {
        if (player.calculateScore() <= 5) {
            player.addCardToHand(deck.drawCard());
        }

        if (banker.calculateScore() <= 2) {
            banker.addCardToHand(deck.drawCard());
        } else if (banker.calculateScore() == 3 && !isThirdCard(8)) {
            banker.addCardToHand(deck.drawCard());
        } else if (banker.calculateScore() == 4 && isThirdCardBetween(2, 7)) {
            banker.addCardToHand(deck.drawCard());
        } else if (banker.calculateScore() == 5 && isThirdCardBetween(4, 7)) {
            banker.addCardToHand(deck.drawCard());
        } else if (banker.calculateScore() == 6 && isThirdCardBetween(6, 7)) {
            banker.addCardToHand(deck.drawCard());
        }
    }

    private boolean isThirdCard(int value) {
        int thirdCardValue = player.getThirdCardValue();
        return thirdCardValue != -1 && thirdCardValue == value; // Ensure the third card exists and matches the value
    }
    
    private boolean isThirdCardBetween(int min, int max) {
        int thirdCardValue = player.getThirdCardValue();
        return thirdCardValue != -1 && thirdCardValue >= min && thirdCardValue <= max; // Ensure third card is in range
    }
    

    // Main method to simulate game flow
    public static void main(String[] args) {
        GameRules game = new GameRules(1000); // Start with an initial balance of 1000

        // Place a main bet (e.g., Player)
        Bet mainBet = new Bet(Bet.BetType.PLAYER, 100); // Example main bet on PLAYER
        game.placeMainBet(mainBet); // Place the main bet

        // Attempt to place side bets
        Bet playerPairBet = new Bet(Bet.BetType.PLAYER_PAIR, 50); // Player Pair side bet
        Bet bankerPairBet = new Bet(Bet.BetType.BANKER_PAIR, 50); // Banker Pair side bet
        Bet eitherPairBet = new Bet(Bet.BetType.EITHER_PAIR, 50); // Either Pair side bet

        // Add side bets
        game.addSideBet(playerPairBet); // This should succeed
        game.addSideBet(bankerPairBet); // This should succeed
        game.addSideBet(eitherPairBet); // This should succeed

        // Try to place side bets before placing a main bet (should fail)
        GameRules gameWithoutMainBet = new GameRules(1000);
        Bet anotherSideBet = new Bet(Bet.BetType.PLAYER_PAIR, 50);
        gameWithoutMainBet.addSideBet(anotherSideBet); // This should fail because no main bet was placed

        // Resolve side bets
        game.resolveSideBets();

        // Output final balance after side bets
        System.out.println("Player's final balance: $" + game.playerBalance);
    }
}
