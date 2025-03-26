import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoginForm extends JFrame{

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginForm() {

        setTitle("eGrade Parent Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2)); // Basicly damo screen na 2


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(230, 242, 255)); //light-modra

        JLabel welcomeLabel = new JLabel("Welcome to eGrade!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setBounds(50, 40, 300, 30);
        leftPanel.add(welcomeLabel);

        JLabel descLabel = new JLabel("Parent Access Portal");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setBounds(50, 75, 300, 25);
        leftPanel.add(descLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 130, 80, 25);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 130, 200, 25);
        leftPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 80, 25);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 170, 200, 25);
        leftPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 220, 100, 30);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        leftPanel.add(loginButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(50, 270, 300, 25);
        statusLabel.setForeground(Color.RED);
        leftPanel.add(statusLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Please enter email and password.");
                } else if (isValidCredentials(email, password)) {
                    statusLabel.setForeground(new Color(0, 128, 0));
                    statusLabel.setText("Login successful! 🎉");
                    // TODO: Redirect to next screen
                } else {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setText("Invalid email or password.");
                }
            }
        });


        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(245, 250, 255));

        try {
            // Load image from src/IMG/loginimg.jpg
            ImageIcon icon = new ImageIcon(getClass().getResource("/IMG/loginimg.jpg"));


            //scaling kurwa
            Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            rightPanel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception ex) {
            JLabel fallbackLabel = new JLabel("Image not found 😢", SwingConstants.CENTER);
            fallbackLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            fallbackLabel.setForeground(Color.GRAY);
            rightPanel.add(fallbackLabel, BorderLayout.CENTER);
        }

        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    private boolean isValidCredentials(String email, String password) {
        return email.equals("parent@egrade.com") && password.equals("parent123");
    }


}
