import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Class for the main menu
public class MainMenu extends JPanel {
    private Image backgroundImage;
    private JPanel[] panels; // Array to hold the panels
    
    public MainMenu() {
        // Load the background image
        backgroundImage = new ImageIcon("C:\\Users\\Botoy\\Downloads\\cmsc22finalproj-main\\cmsc22finalproj-main\\logic\\297.png").getImage();
    
        // Check if the image is loaded correctly
        if (backgroundImage.getWidth(null) == -1 || backgroundImage.getHeight(null) == -1) {
            System.err.println("Image not found or failed to load!");
        }
    
        // Set layout for the main panel
        setLayout(new GridLayout(3, 3)); // 3x3 grid layout
    
        // Initialize the panel array
        panels = new JPanel[9];
    
        // Create the first six panels in a loop
        for (int i = 0; i < 6; i++) {
            panels[i] = new JPanel();
            panels[i].setOpaque(false); // Set the panels to be transparent
            panels[i].setLayout(new BorderLayout()); // Use BorderLayout for positioning
            add(panels[i]); // Add the panel to the grid
        }

        // Manually create and add clickable labels to the bottom three panels
        panels[6] = createClickableLabelPanel("Lets Go Gambling");
        panels[7] = createClickableLabelPanel("Show Profile");
        panels[8] = createClickableLabelPanel("Exit");
    }
    
    private JPanel createClickableLabelPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setOpaque(false); // Set the panel to be transparent
        panel.setLayout(new BorderLayout()); // Use BorderLayout for positioning
        
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE); // Set label color
        label.setFont(new Font("Garamond", Font.BOLD, 30));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        
        // Add mouse listener to handle clicks
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleLabelClick(labelText);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.YELLOW); // Change color for glow effect
                label.setFont(label.getFont().deriveFont(35f)); // Increase font size for glow effect
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.WHITE); // Reset color
                label.setFont(label.getFont().deriveFont(30f)); // Reset font size
            }
        });
        
        panel.add(label, BorderLayout.CENTER); // Add label to the center
        add(panel); // Add the panel to the grid
        
        return panel;
    }
    
    private void handleLabelClick(String labelText) {
        JFrame frame; 

        switch (labelText) {
            case "Lets Go Gambling":
                // Dispose of the current frame
                frame = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
                if (frame != null) {
                    frame.dispose(); // Dispose the main menu frame
                }
                // Show the GameScreen
                new Game();
                break;
            case "Show Profile":
                // Action for the profile label
                frame = (JFrame) SwingUtilities.getWindowAncestor(MainMenu.this);
                if (frame != null) {
                    frame.dispose(); // Dispose the main menu frame
                }
                // Show the ProfileScreen
                new ProfileScreen().createProfileScreen();
                break;
            case "Exit":
                // Action for the exit label
                int confirm = JOptionPane.showConfirmDialog(MainMenu.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit the application
                }
                break;
            default:
                System.out.println("Unknown label clicked: " + labelText);
                break;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    public static JFrame createMainMenuFrame() {
        JFrame frame = new JFrame("Baraha ni Kulot");
        MainMenu panel = new MainMenu();
        
        ImageIcon icon = new ImageIcon("C:\\Users\\Botoy\\Downloads\\cmsc22finalproj-main\\cmsc22finalproj-main\\logic\\images.jpg");//icon
        frame.setIconImage(icon.getImage());
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }
}