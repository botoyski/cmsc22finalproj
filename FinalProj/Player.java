import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private boolean isBanker;

    public Player(boolean isBanker) {
        this.hand = new ArrayList<>();
        this.isBanker = isBanker;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public int calculateScore() {
        int total = 0;
        for (Card card : hand) {
            int value = card.getValue();
            if (value >= 10) {
                value = 0; // Face cards and 10s are treated as 0 in Baccarat
            }
            total += value;
        }
        return total % 10;  // Last digit of the total (Baccarat scoring)
    }

    public void resetHand() {
        hand.clear();
    }

    public boolean isBanker() {
        return isBanker;
    }

    public int getThirdCardValue() {
        return hand.size() >= 3 ? hand.get(2).getValue() : -1; // Return -1 if no third card to differentiate
    }

    public int getTotalScore() {
        return calculateScore(); // Updated to reflect Baccarat scoring
    }

    public List<Card> getHand() {
        return hand;
    }

    public void displayHand() {
        hand.forEach(card -> System.out.println(card.getRank() + " of " + card.getSuit()));
    }
}
