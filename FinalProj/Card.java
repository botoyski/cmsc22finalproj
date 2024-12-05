public class Card {
    private String suit;  // Corrected spelling from 'suite' to 'suit'
    private String rank;  // Added rank property
    private int value;    // The Baccarat value of the card
    private int id;       // Unique identifier for the card
    private double posX, posY;  // Position for GUI rendering

    // Constructor
    public Card(String suit, String rank, int id) {  // Added rank as a parameter
        this.suit = suit;
        this.rank = rank;
        this.id = id;
        this.value = calculateValue(rank);  // Calculate value based on rank
    }

    // Method to calculate the value of the card based on its rank
    private int calculateValue(String rank) {
        if (rank.equals("A")) return 1;  // Ace value
        if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) return 0;  // Face cards value 0
        return Integer.parseInt(rank);  // Convert rank to integer for numbered cards
    }

    // Getters
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;  // Added getter for rank
    }

    public int getValue() {
        return value;  // Return the calculated value
    }

    public int getId() {
        return id;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    // Optionally, you can add setters if you need to modify these properties
}