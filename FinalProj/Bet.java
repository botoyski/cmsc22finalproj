public class Bet {
    public enum BetType {
        PLAYER, BANKER, TIE, PLAYER_PAIR, BANKER_PAIR, EITHER_PAIR, P_BONUS, B_BONUS
    }

    private BetType betType;
    private double amount;

    public Bet(BetType betType, double amount) {
        this.betType = betType;
        this.amount = amount;
    }

    public BetType getBetType() {
        return betType;
    }

    public double getAmount() {
        return amount;
    }

    // Resolve regular bets (Player, Banker, Tie)
    public double resolveBet(Player player, Player banker) {
        int playerScore = player.calculateScore();
        int bankerScore = banker.calculateScore();

        // Resolve regular bets (1:1 payout, etc.)
        if (betType == BetType.PLAYER && playerScore > bankerScore) {
            return amount * 2; // 1:1 payout
        } else if (betType == BetType.BANKER && bankerScore > playerScore) {
            return amount * 1.95; // 1:1 payout minus 5% commission
        } else if (betType == BetType.TIE && playerScore == bankerScore) {
            return amount * 8; // 8:1 payout
        }
        return 0; // No win
    }

    // Handle side bets
    public double sideBet(Player player, Player banker) {
        int playerScore = player.calculateScore();
        int bankerScore = banker.calculateScore();
        double payout = 0;

        switch (betType) {
            case PLAYER_PAIR:
                if (isPair(player)) {
                    payout = amount * 11; // Player Pair bet (11:1 odds)
                }
                break;
            case BANKER_PAIR:
                if (isPair(banker)) {
                    payout = amount * 11; // Banker Pair bet (11:1 odds)
                }
                break;
            case EITHER_PAIR:
                if (isPair(player) || isPair(banker)) {
                    payout = amount * 5; // Either Pair bet (5:1 odds)
                }
                break;
            case TIE:
                if (playerScore == bankerScore) {
                    payout = amount * 8; // Tie bet (8:1 odds)
                }
                break;
            case P_BONUS:
                payout = calculateBonus(playerScore, bankerScore, "P");
                break;
            case B_BONUS:
                payout = calculateBonus(playerScore, bankerScore, "B");
                break;
        }

        return payout; // Return the payout for the side bet
    }

    // Helper method to check if a player has a pair
    private boolean isPair(Player player) {
        if (player.getHand().size() < 2) return false;
        int rank1 = player.getHand().get(0).getValue();
        int rank2 = player.getHand().get(1).getValue();
        return rank1 == rank2;
    }

    // Calculate P and B Bonuses (based on the gap between the winning and losing score)
    private double calculateBonus(int winnerScore, int loserScore, String side) {
        int gap = Math.abs(winnerScore - loserScore);

        if (side.equals("P")) { // P Bonus
            return gap * 1.5; // Adjust payout based on the gap
        } else if (side.equals("B")) { // B Bonus
            return gap * 1.5; // Adjust payout based on the gap
        }

        return 0;
    }
}