public class GameLogic {
    private GameRules game;
    private BetHistory betHistory;
    private Account account;

    // Constructor to initialize game, account, and bet history
    public GameLogic(double initialBalance, String username, String password) {
        this.account = new Account(username, password, initialBalance);
        this.game = new GameRules(initialBalance);
        this.betHistory = new BetHistory();
    }

    // Method to place a bet
    public void placeBet(String betType, double betAmount) {
        try {
            // Convert bet type to enum and create the main bet
            Bet mainBet = new Bet(Bet.BetType.valueOf(betType.toUpperCase()), betAmount);
            game.placeMainBet(mainBet); // Process the main bet
            betHistory.addBet(mainBet); // Add bet to history

            // Notify the UI (you can update your label or display a message)
            System.out.println("Bet placed successfully: " + betType + " - Amount: " + betAmount);
        } catch (IllegalArgumentException ex) {
            // Handle invalid bet type
            System.err.println("Invalid bet type: " + betType);
        }
    }

    // Method to place side bets
    public void placeSideBet(String sideBetType, double sideBetAmount) {
        try {
            Bet sideBet = new Bet(Bet.BetType.valueOf(sideBetType.toUpperCase()), sideBetAmount);
            game.addSideBet(sideBet); // Add side bet to game
            betHistory.addBet(sideBet); // Add side bet to history

            // Notify the UI (you can update your label or display a message)
            System.out.println("Side Bet placed: " + sideBetType + " - Amount: " + sideBetAmount);
        } catch (IllegalArgumentException ex) {
            // Handle invalid side bet type
            System.err.println("Invalid side bet type: " + sideBetType);
        }
    }

    // Method to play a round
    public void playRound() {
        // Deal cards and apply third card rule
        game.dealCards(); // Call the dealCards method from the Game class
        game.applyThirdCardRule(); // Apply third card rule

        // Calculate scores
        int playerScore = game.getPlayer().calculateScore(); // Calculate score for player
        int bankerScore = game.getBanker().calculateScore(); // Calculate score for banker
        System.out.println("Player's Score: " + playerScore);
        System.out.println("Banker's Score: " + bankerScore);

        // Resolve bets based on scores
        resolveMainBet(playerScore, bankerScore);
        game.resolveSideBets(); // Resolve side bets

        // Output player balance after round
        System.out.println("Player's Balance: $" + account.getBalance());
    }

    // Method to resolve the main bet
    private void resolveMainBet(int playerScore, int bankerScore) {
        double payout = 0;
        Bet mainBet = betHistory.getLastBet(); // Assuming betHistory stores the most recent main bet

        if (mainBet != null) {
            if (mainBet.getBetType() == Bet.BetType.PLAYER && playerScore > bankerScore) {
                payout = mainBet.getAmount() * 2; // Player wins
            } else if (mainBet.getBetType() == Bet.BetType.BANKER && bankerScore > playerScore) {
                payout = mainBet.getAmount() * 2; // Banker wins
            } else if (mainBet.getBetType() == Bet.BetType.TIE && playerScore == bankerScore) {
                payout = mainBet.getAmount() * 8; // Tie wins
            } else {
                payout = 0; // No win
            }

            // Update balance based on payout or loss
            account.updateBalance(payout - mainBet.getAmount());
            System.out.println("Main bet result: " + (payout > 0 ? "Win" : "Loss"));
        }
    }

    // Method to display bet history
    public void displayBetHistory() {
        betHistory.displayBets();
    }

    // Method to exit the game
    public void exitGame() {
        System.out.println("Thank you for playing Baccarat!");
        // Save account data and bet history if required
    }

    public double getTotalBet() {
        double totalBet = 0;

        // Sum up all bets in the bet history for the current round
        for (Bet bet : betHistory.getBets()) { // Assuming getBets() retrieves all bets for the current round
            totalBet += bet.getAmount();
        }

        return totalBet;
    }

    // Method to get the current account balance
    public double getBalance() {
        return account.getBalance();
    }
}
