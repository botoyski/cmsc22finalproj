import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.LineBorder;

class RoundedButton extends JButton {
    private static final int ROUNDED_CORNER_RADIUS = 20; // Adjust the radius as needed

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setForeground(Color.BLACK); // Set text color
        setFont(new Font("Garamond", Font.BOLD, 12)); // Set font
        setBackground(Color.WHITE); // Default background color
        setOpaque(false); // Make the button opaque
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding

        // Add mouse listener for hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(220, 220, 220)); // Change background on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE); // Reset background
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_RADIUS));
        super.paintComponent(g);
    }
}

public class ProfileScreen {
    // Define the main color
    private static final Color MAIN_COLOR = new Color(24, 66, 40); // #184228
    private static final Color PANEL_COLOR = new Color(34, 76, 50); // A lighter shade for panels

    public void createProfileScreen() {
        // Create the profile screen
        JFrame profileFrame = new JFrame("Baccarat");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setResizable(false);
        
        // Set the frame size to the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        profileFrame.setSize(screenSize.width, screenSize.height); // Set to screen size

        // Create the main panel (rectangle) for the profile
        JPanel mainPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        mainPanel.setLayout(gridBagLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the main panel
        mainPanel.setBackground(MAIN_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 10, 10, 10); // Add some padding

        // Create a panel for the name at the top
        JPanel namePanel = new JPanel();
        namePanel.setOpaque(false); // Make the panel transparent
        JLabel nameLabel = new JLabel("Profile Name", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Garamond", Font.BOLD, 28));
        nameLabel.setForeground(Color.WHITE); // White text
        namePanel.add(nameLabel);
        
        // Add name panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1; // Give it some height
        mainPanel.add(namePanel, gbc);
        
        // Create the middle panel for the ID card layout using GridLayout
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, with gaps
        middlePanel.setOpaque(false); // Make the panel transparent

        // Profile picture panel (0,0 )
        JPanel profilePanel = new JPanel();
        profilePanel.setBorder(new LineBorder(MAIN_COLOR, 2)); // Add a border to the profile panel
        profilePanel.setLayout(new BorderLayout()); // Use BorderLayout to position the label
        profilePanel.add(new JLabel("Profile Picture Here", SwingConstants.CENTER), BorderLayout.CENTER); // Placeholder for profile picture

        // Add a label for the profile picture panel
        JLabel profileLabel = new JLabel("Profile Picture", SwingConstants.CENTER);
        profileLabel.setFont(new Font("Garamond", Font.BOLD, 16));
        profilePanel.add(profileLabel, BorderLayout.NORTH);

        // Stats panel (0, 1) - occupies all three remaining cells
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(new LineBorder(MAIN_COLOR, 2)); // Add a border to the stats panel
        statsPanel.setLayout(new GridLayout(4, 1)); // Vertical layout for stats
        statsPanel.add(new JLabel("Stats:", SwingConstants.CENTER));
        statsPanel.add(new JLabel("Wins: 10", SwingConstants.CENTER));
        statsPanel.add(new JLabel("Losses: 5", SwingConstants.CENTER));
        
        // Add a label for the stats panel
        JLabel statsLabel = new JLabel("Player Stats", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Garamond", Font.BOLD, 16));
        statsPanel.add(statsLabel, 0); // Add at the top of the stats panel

        // Add panels to the middle panel
        middlePanel.add(profilePanel); // Add profile panel to the middle panel
        middlePanel.add(statsPanel); // Add stats panel to the middle panel

        // Add the middle panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Increased height for the middle panel
        mainPanel.add(middlePanel, gbc);

        // Create a panel for the bottom section
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible positioning
        bottomPanel.setOpaque(false); // Make the bottom panel transparent

        GridBagConstraints bottomGbc = new GridBagConstraints();
        bottomGbc.fill = GridBagConstraints.BOTH;
        bottomGbc.insets = new Insets(5, 10, 10, 10); // Reduced top padding to move buttons up

        // Create the first button panel
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel1.setOpaque(false); // Make the panel transparent
        buttonPanel1.add(createStyledButton("Edit Profile"));
        buttonPanel1.add(createStyledButton("Save Profile"));
        buttonPanel1.add(createStyledButton("Load Profile"));

        // Create the second button panel
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 3, 10, 10)); // Match the layout of buttonPanel1
        buttonPanel2.setOpaque(false); // Make the panel transparent
        buttonPanel2.add(createStyledButton("View Activity"));
        buttonPanel2.add(createStyledButton("Change Password"));

        // Back to Main Menu Button
        RoundedButton backToMainMenuButton = createStyledButton("Back to Main Menu");
        backToMainMenuButton.addActionListener(e -> {
            profileFrame.dispose(); // Close the profile frame
            MainMenu.createMainMenuFrame(); // Open the main menu
        });
        buttonPanel2.add(backToMainMenuButton); // Add to the second button panel

        // Add buttonPanel1 under the profile panel, spanning across the width
        bottomGbc.gridx = 0;
        bottomGbc.gridy = 0;
        bottomGbc.weightx = 1.0;
        bottomGbc.weighty = 0.1; // Give it some height
        bottomPanel.add(buttonPanel1, bottomGbc);

        // Add buttonPanel2 to the bottom panel
        bottomGbc.gridy = 1; // Move to the next row
        bottomGbc.weighty = 0.1; // Give it some height
        bottomPanel.add(buttonPanel2, bottomGbc);

        // Add the bottom panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1; // Give it some height
        mainPanel.add(bottomPanel, gbc);

        // Add the main panel to the frame
        profileFrame.add(mainPanel);
        profileFrame.setVisible(true);
    }

    private RoundedButton createStyledButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setBackground(Color.WHITE); // Use the main color
        button.setFont(new Font("Garamond", Font.BOLD, 12)); // Make the font a bit smaller
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileScreen profileScreen = new ProfileScreen();
            profileScreen.createProfileScreen();
        });
    }
}