package gui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends BaseFrame{
    public LoginGUI() {
        super("Bank App Login");
    }

    @Override
    protected void addGuiComponents() {
        JLabel musicStoreLabel = new JLabel("Music Store");
        musicStoreLabel.setBounds(0, 20, super.getWidth(), 40);
        musicStoreLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        musicStoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(musicStoreLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        TextField usernameField = new TextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        JLabel passWordLabel = new JLabel("Password:");
        passWordLabel.setBounds(20, 280, getWidth() - 50, 24);
        passWordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passWordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(registerLabel);
    }
}
