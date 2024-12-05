
import java.util.ArrayList;
import java.util.List;

public class BetHistory {
    private List<Bet> bets;

    public BetHistory() {
        this.bets = new ArrayList<>();
    }

    public void addBet(Bet bet) {
        bets.add(bet);
    }

    public void displayBets() {
        System.out.println("Bet History:");
        for (Bet bet : bets) {
            System.out.println("Bet Type: " + bet.getBetType() + ", Amount: " + bet.getAmount());
        }
    }

    public Bet getLastBet() {
        if (bets.isEmpty()) {
            return null; // Return null if no bets have been placed
        }
        return bets.get(bets.size() - 1); // Return the most recent bet
    }


    public List<Bet> getBets() {
        return bets;
    }
}