public class GameLogic {
    private GameRules game;
    private BetHistory betHistory;
    private Account account;

    public GameLogic(double initialBalance, String username, String password) {
        this.account = new Account(username, password, initialBalance);
        this.game = new GameRules(initialBalance);
        this.betHistory = new BetHistory();
    }

    public void placeBet(String betType, double betAmount) {
        try {
            Bet mainBet = new Bet(Bet.BetType.valueOf(betType.toUpperCase()), betAmount);
            game.placeMainBet(mainBet);
            betHistory.addBet(mainBet);
            System.out.println("Bet placed successfully: " + betType + " - Amount: " + betAmount);
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid bet type: " + betType);
        }
    }

    public void playRound() {
        game.dealCards();  // Deal the cards
        game.applyThirdCardRule();  // Apply third card rule

        // Calculate player and banker scores
        int playerScore = game.getPlayer().calculateScore();
        int bankerScore = game.getBanker().calculateScore();
        System.out.println("Player's Score: " + playerScore);
        System.out.println("Banker's Score: " + bankerScore);

        // Resolve bets based on scores
        resolveMainBet(playerScore, bankerScore);
        game.resolveSideBets();

        System.out.println("Player's Balance: $" + account.getBalance());
    }

    // Method to resolve the main bet
    private void resolveMainBet(int playerScore, int bankerScore) {
        double payout = 0;
        Bet mainBet = betHistory.getLastBet();  // Get the most recent main bet

        if (mainBet != null) {
            if (mainBet.getBetType() == Bet.BetType.PLAYER && playerScore > bankerScore) {
                payout = mainBet.getAmount() * 2;  // Player wins
            } else if (mainBet.getBetType() == Bet.BetType.BANKER && bankerScore > playerScore) {
                payout = mainBet.getAmount() * 2;  // Banker wins
            } else if (mainBet.getBetType() == Bet.BetType.TIE && playerScore == bankerScore) {
                payout = mainBet.getAmount() * 8;  // Tie wins
            } else {
                payout = 0;  // No win
            }

            // Update balance based on payout or loss
            account.updateBalance(payout - mainBet.getAmount());
            System.out.println("Main bet result: " + (payout > 0 ? "Win" : "Loss"));
        }
    }

    // Method to get player score
    public int getPlayerScore() {
        return game.getPlayer().calculateScore();  // Get player score
    }

    // Method to get banker score
    public int getBankerScore() {
        return game.getBanker().calculateScore();  // Get banker score
    }

    // Method to determine who won
    public String getRoundResult() {
        int playerScore = getPlayerScore();
        int bankerScore = getBankerScore();

        if (playerScore > bankerScore) {
            return "Player Wins!";
        } else if (bankerScore > playerScore) {
            return "Banker Wins!";
        } else {
            return "It's a Tie!";
        }
    }

    // Method to get the current account balance
    public double getBalance() {
        return account.getBalance();
    }

    public GameRules getGameRules(){
        return this.game;
    }
}
