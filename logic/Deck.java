public class Deck {
    private Card[] cards;   // Array of cards
    private int currentIndex;   // Tracks current position in the deck

    public Deck() {
        cards = new Card[52];   // 52 slots for cards
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        
        int index = 0;
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                int value = (i >= 9) ? 0 : i + 1;  // Baccarat values (A=1, 2-9=face, 10/J/Q/K=0)
                cards[index++] = new Card(suit, ranks[i], value);
            }
        }
        shuffle();   // Shuffle deck on creation
        currentIndex = 0;   // Reset the index
    }

    public void shuffle() {
        for (int i = cards.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    public Card drawCard() {
        if (hasCards()) {
            return cards[currentIndex++];   // Return and advance index
        }
        System.out.println("Deck is empty, reshuffling...");
        shuffle();
        currentIndex = 0;   // Reshuffle if empty
        return drawCard();
    }

    public boolean hasCards() {
        return currentIndex < cards.length;
    }
}
