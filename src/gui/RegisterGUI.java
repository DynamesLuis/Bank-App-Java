package gui;

import db_objs.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGUI extends BaseFrame {
    public RegisterGUI() {
        super("Register");
    }

    @Override
    protected void addGuiComponents() {
        JLabel musicStoreLabel = new JLabel("Bank App register");
        musicStoreLabel.setBounds(0, 20, super.getWidth(), 40);
        musicStoreLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        musicStoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(musicStoreLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        JLabel rePasswordLabel = new JLabel("Re-type Password:");
        rePasswordLabel.setBounds(20, 320, getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 460, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String rePassword= String.valueOf(rePasswordField.getPassword());
                if (validateUserInput(username, password, rePassword)){
                    if(JDBC.register(username, password)) {
                        RegisterGUI.this.dispose();
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.setVisible(true);
                        JOptionPane.showMessageDialog(loginGUI, "Account created!");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGUI.this, "Error: Username already taken");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGUI.this,
                            "Error: Username must be at least 6 characters\n" +
                                    "and/or Password must match");
                };
            }
        });
        add(registerButton);

        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Login here</a></html>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGUI.this.dispose();
                new LoginGUI().setVisible(true);
            }
        });
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePassword) {
        if (username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;
        if (username.length() < 6) return false;
        if (!password.equals(rePassword)) return false;
        return true;
    }
}
