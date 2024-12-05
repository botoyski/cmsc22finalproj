import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;


// Main Game Class
    public class Game {
        private GameLogic gameLogic;
        
        public Game() {
            this.gameLogic = new GameLogic(1000.0, "user", "password");
            createGameFrame(); // Create the game interface frame
        }
    
        // Method to create the game frame
        private void createGameFrame() {
            JFrame frame = new JFrame("Game Interface");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize.width, screenSize.height);
            frame.setLayout(new GridBagLayout());
            frame.setResizable(true);
        
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
    
            // Add components using factories
            gbc.gridy = 0;
            gbc.weighty = 0.5;
            frame.add(createGamePanel(), gbc);
    
            gbc.gridy = 1;
            gbc.weighty = 0.2;
            frame.add(new ButtonPanelFactory(gameLogic).createButtonPanel1(), gbc);
    
            gbc.gridy = 2;
            gbc.weighty = 0.05;
            frame.add(new ButtonPanelFactory(gameLogic).createButtonPanel2(), gbc);
    
            gbc.gridy = 3;
            gbc.weighty = 0.03;
            frame.add(new TopBar(gameLogic).render(), gbc);
    
            gbc.gridy = 4;
            gbc.weighty = 0.01;
            frame.add(new HistoryBar().render(), gbc);
    
            frame.setVisible(true);
        }
    
        private JPanel createGamePanel() {
            JPanel gamePanel = new JPanel(new GridBagLayout());
            GridBagConstraints gameGbc = new GridBagConstraints();
            gameGbc.fill = GridBagConstraints.BOTH;
            gameGbc.weightx = 0.5;
            gameGbc.weighty = 1.0;
    
            // Left Panel
            JPanel leftPanel = new JPanel(new GridBagLayout());
            leftPanel.setBackground(new Color(0, 100, 0));
            GridBagConstraints leftGbc = new GridBagConstraints();
            leftGbc.fill = GridBagConstraints.BOTH;
    
            // Row 1: Player Label
            leftGbc.gridy = 0;
            leftGbc.weighty = 0.05;
            leftGbc.weightx = 1.0;
            leftGbc.gridx = 1;
            JLabel playerLabel = new JLabel("Player's Hand");
            playerLabel.setFont(new Font("Garamond", Font.BOLD, 24));
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            leftPanel.add(playerLabel, leftGbc);
    
            // Row 2: Placeholder Content
            leftGbc.gridy = 1;
            leftGbc.weighty = 0.95;
            leftGbc.gridx = 0;
            leftGbc.gridwidth = 3;
            JPanel leftRow2Content = new JPanel();
            leftRow2Content.setBackground(new Color(0, 80, 0));
            leftPanel.add(leftRow2Content, leftGbc);
    
            gameGbc.gridx = 0;
            gamePanel.add(leftPanel, gameGbc);
    
            // Right Panel
            JPanel rightPanel = new JPanel(new GridBagLayout());
            rightPanel.setBackground(new Color(100, 0, 0));
            GridBagConstraints rightGbc = new GridBagConstraints();
            rightGbc.fill = GridBagConstraints.BOTH;
    
            // Row 1: Banker Label
            rightGbc.gridy = 0;
            rightGbc.weighty = 0.05;
            rightGbc.weightx = 1.0;
            rightGbc.gridx = 1;
            JLabel bankerLabel = new JLabel("Banker's Hand");
            bankerLabel.setFont(new Font("Garamond", Font.BOLD, 24));
            bankerLabel.setForeground(Color.WHITE);
            bankerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            rightPanel.add(bankerLabel, rightGbc);
    
            // Row 2: Placeholder Content
            rightGbc.gridy = 1;
            rightGbc.weighty = 0.95;
            rightGbc.gridx = 0;
            rightGbc.gridwidth = 3;
            JPanel rightRow2Content = new JPanel();
            rightRow2Content.setBackground(new Color(80, 0, 0));
            rightPanel.add(rightRow2Content, rightGbc);
    
            gameGbc.gridx = 1;
            gamePanel.add(rightPanel, gameGbc);
    
            return gamePanel;
        }
    }
    

// Factory Class for Creating Button Panels
class ButtonPanelFactory {
    private GameLogic gameLogic;

    public ButtonPanelFactory(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    // Create Button Panel 1 for main bets (Player, Tie, Banker)
    public JPanel createButtonPanel1() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 6, 6));
        buttonPanel.setBackground(new Color(46, 83, 57));

        String[] labels = {"PLAYER", "TIE", "BANKER"};
        Color[][] gradientColors = {
            {new Color(102, 110, 113), new Color(19, 28, 43)}, // Blue
            {new Color(41, 93, 18), new Color(14, 38, 14)},    // Green
            {new Color(111, 37, 20), new Color(41, 12, 14)}    // Red
        };

        addGradientButtons(buttonPanel, labels, gradientColors);

        return buttonPanel;
    }

    // Create Button Panel 2 for side bets
    public JPanel createButtonPanel2() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 6, 6));
        buttonPanel.setBackground(new Color(46, 83, 57));

        String[] labels = {"P BONUS", "DOUBLE PERFECT PAIR", "B BONUS", "P PAIR", "EITHER PAIR", "B PAIR"};
        Color[][] gradientColors = {
            {new Color(102, 110, 113), new Color(19, 28, 43)}, // Blue
            {new Color(102, 62, 44), new Color(56, 41, 20)},   // Brown
            {new Color(111, 37, 20), new Color(41, 12, 14)},   // Red
            {new Color(102, 110, 113), new Color(19, 28, 43)}, // Blue
            {new Color(102, 62, 44), new Color(56, 41, 20)},   // Brown
            {new Color(111, 37, 20), new Color(41, 12, 14)}    // Red
        };

        addGradientButtons(buttonPanel, labels, gradientColors);

        return buttonPanel;
    }

    // Add Gradient Buttons with betting logic
    private void addGradientButtons(JPanel panel, String[] labels, Color[][] gradientColors) {
        for (int i = 0; i < labels.length; i++) {
            int currentIndex = i;
            JButton button = new GradientButton(labels[i], gradientColors[i]);
            button.setFont(new Font("Garamond", Font.BOLD, 18));
            button.setForeground(Color.WHITE);

            // Add ActionListener for betting logic
            button.addActionListener(e -> {
                String betType = labels[currentIndex].toUpperCase(); // Assuming button labels map to bet types
                double betAmount = 100; // Example bet amount; can be adjusted or user-defined

                try {
                    Bet bet = new Bet(Bet.BetType.valueOf(betType), betAmount);
                    gameLogic.placeBet(betType,betAmount); // Place the bet via GameLogic
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid bet type: " + betType, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            panel.add(button);
        }
    }
}


// TopBar Component
// TopBar Component
class TopBar {
    private GameLogic gameLogic;
    
    public TopBar(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public JPanel render() {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(6, 57, 112));
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Total Bet Label
        JLabel totalBetLabel = new JLabel("Total Bet: $0.00");
        totalBetLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalBetLabel.setForeground(Color.WHITE);
        totalBetLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Update the total bet dynamically (use gameLogic to get current bet)
        Timer timer = new Timer(500, e -> totalBetLabel.setText("Total Bet: $" + gameLogic.getTotalBet()));
        timer.start();

        topBar.add(totalBetLabel, BorderLayout.CENTER);
        return topBar;
    }
}

// HistoryBar Component
class HistoryBar {
    public JPanel render() {
        JPanel historyBar = new JPanel();
        historyBar.setBackground(new Color(21, 21, 21));
        historyBar.setLayout(new BorderLayout(10, 0));

        JLabel balance = new JLabel("   Balance: ");
        balance.setForeground(Color.WHITE);
        balance.setFont(new Font("Garamond", Font.BOLD, 14));
        historyBar.add(balance, BorderLayout.WEST);

        return historyBar;
    }
}

// ButtonFactory: Encapsulates button creation logic
class ButtonFactory {
    public static JButton createBetButton(int increment, JLabel totalBetLabel) {
        JButton button = new JButton("+" + increment);
        button.setFont(new Font("Garamond", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(25, 25, 25));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            String text = totalBetLabel.getText();
            int currentBet = Integer.parseInt(text.replaceAll("[^0-9]", ""));
            int newBet = currentBet + increment;
            totalBetLabel.setText("   Total Bet: " + newBet + " php");
        });
        return button;
    }
}

// GradientButton Class: Custom button with gradient rendering
class GradientButton extends JButton {
    private final Color[] gradientColors;
    private final int cornerRadius;

    public GradientButton(String text, Color[] gradientColors) {
        super(text);
        this.gradientColors = gradientColors;
        this.cornerRadius = 15;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D roundedRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        GradientPaint gradient = new GradientPaint(0, 0, gradientColors[0], 0, getHeight(), gradientColors[1]);
        g2d.setPaint(gradient);
        g2d.fill(roundedRect);

        super.paintComponent(g2d);
        g2d.dispose();
    }
}

// Main method should be in a separate file