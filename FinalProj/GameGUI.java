import java.awt.*;
import javax.swing.*;

public class GameGUI extends JFrame {
    private GameRules game;
    private GameLogic gameLogic;
    private JLabel playerScoreLabel;
    private JLabel bankerScoreLabel;
    private JLabel balanceLabel;
    private JTextField betAmountField;
    private JComboBox<String> betTypeComboBox;
    private JTextArea betHistoryArea;

    public GameGUI(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.game = gameLogic.getGameRules();  // Get the GameRules instance from GameLogic
        setTitle("Baccarat Game");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));  // Add spacing between panels
        getContentPane().setBackground(new Color(46, 83, 57));  // Set background color

        // Top panel for displaying scores and balance
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBackground(new Color(46, 83, 57));

        playerScoreLabel = createStyledLabel("Player Score: 0");
        bankerScoreLabel = createStyledLabel("Banker Score: 0");
        balanceLabel = createStyledLabel("Balance: $1000");

        topPanel.add(playerScoreLabel);
        topPanel.add(bankerScoreLabel);
        topPanel.add(balanceLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for betting input
        JPanel betPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        betPanel.setBackground(new Color(46, 83, 57));
        betPanel.setBorder(BorderFactory.createTitledBorder("Place Your Bet"));

        betTypeComboBox = new JComboBox<>(new String[]{"PLAYER", "BANKER", "TIE"});
        betTypeComboBox.setFont(new Font("Garamond", Font.PLAIN, 16));
        betAmountField = new JTextField();
        betAmountField.setFont(new Font("Garamond", Font.PLAIN, 16));

        betPanel.add(new JLabel("Bet Type:", JLabel.RIGHT));
        betPanel.add(betTypeComboBox);
        betPanel.add(new JLabel("Bet Amount:", JLabel.RIGHT));
        betPanel.add(betAmountField);
        add(betPanel, BorderLayout.CENTER);

        // Right panel for bet history
        betHistoryArea = new JTextArea();
        betHistoryArea.setFont(new Font("Garamond", Font.PLAIN, 14));
        betHistoryArea.setEditable(false);
        betHistoryArea.setBackground(new Color(30, 50, 40));
        betHistoryArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(betHistoryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bet History"));
        add(scrollPane, BorderLayout.EAST);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        bottomPanel.setBackground(new Color(46, 83, 57));

        JButton placeBetButton = createStyledButton("Place Bet");
        JButton playRoundButton = createStyledButton("Play Round");
        JButton exitButton = createStyledButton("Exit");

        placeBetButton.addActionListener(e -> handlePlaceBet());
        playRoundButton.addActionListener(e -> handlePlayRound());
        exitButton.addActionListener(e -> System.exit(0));

        bottomPanel.add(placeBetButton);
        bottomPanel.add(playRoundButton);
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Garamond", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Garamond", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 120, 80));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void handlePlaceBet() {
        try {
            double betAmount = Double.parseDouble(betAmountField.getText());
            String betType = (String) betTypeComboBox.getSelectedItem();

            gameLogic.placeBet(betType, betAmount);
            betHistoryArea.append("Bet placed: " + betType + " - Amount: " + betAmount + "\n");

            balanceLabel.setText("Balance: $" + gameLogic.getBalance());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePlayRound() {
        gameLogic.playRound();

        int playerScore = gameLogic.getPlayerScore();
        int bankerScore = gameLogic.getBankerScore();

        playerScoreLabel.setText("Player Score: " + playerScore);
        bankerScoreLabel.setText("Banker Score: " + bankerScore);

        String roundResult = gameLogic.getRoundResult();
        betHistoryArea.append("Round Result: " + roundResult + "\n");

        JOptionPane.showMessageDialog(this, roundResult, "Round Result", JOptionPane.INFORMATION_MESSAGE);
        balanceLabel.setText("Balance: $" + gameLogic.getBalance());
    }

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic(1000.0, "player1", "password");

        SwingUtilities.invokeLater(() -> {
            new GameGUI(gameLogic).setVisible(true);
        });
    }
}
