
public class CardTest {
    public static void main(String[] args) {
        // Test Card creation
        System.out.println("Testing Card Creation:");
        Card aceOfHearts = new Card("Hearts", "A", 1);
        Card tenOfDiamonds = new Card("Diamonds", "10", 2);
        Card kingOfSpades = new Card("Spades", "K", 3);

        System.out.println("Card: " + aceOfHearts.getRank() + " of " + aceOfHearts.getSuit() + " has value: " + aceOfHearts.getValue());
        System.out.println("Card: " + tenOfDiamonds.getRank() + " of " + tenOfDiamonds.getSuit() + " has value: " + tenOfDiamonds.getValue());
        System.out.println("Card: " + kingOfSpades.getRank() + " of " + kingOfSpades.getSuit() + " has value: " + kingOfSpades.getValue());
        System.out.println();

        // Test Deck creation and shuffling
        System.out.println("Testing Deck Creation and Shuffling:");
        Deck deck = new Deck();
        System.out.println("Deck created with " + deck.hasCards() + " cards.");

        // Draw some cards from the deck
        System.out.println("Drawing 5 cards from the deck:");
        for (int i = 0; i < 5; i++) {
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                System.out.println("Drew: " + drawnCard.getRank() + " of " + drawnCard.getSuit());
            } else {
                System.out.println("No more cards to draw.");
            }
        }

        // Check if there are still cards left
        System.out.println("Are there more cards in the deck? " + deck.hasCards());
    }
}