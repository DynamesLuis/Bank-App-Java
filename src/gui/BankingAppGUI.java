package gui;

import db_objs.User;

import javax.swing.*;
import java.awt.*;

public class BankingAppGUI extends BaseFrame {
    private JTextField totalMusicField;
    public JTextField getTotalMusicField(){return totalMusicField;}

    public BankingAppGUI(User user) {
        super("Banking App", user);
    }

    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html>" +
                "<body style='text-align:center'>" +
                "<b>Hello " + user.getName() + "</b><br>" +
                "What would you like to do today?</body></html>";
        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel totalMusicLabel = new JLabel("Current Balance");
        totalMusicLabel.setBounds(0, 80, getWidth() - 10, 30);
        totalMusicLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        totalMusicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(totalMusicLabel);

        totalMusicField = new JTextField("$" + user.getCurrentBalance());
        totalMusicField.setBounds(15, 120, getWidth() - 50, 40);
        totalMusicField.setFont(new Font("Dialog", Font.BOLD, 28));
        totalMusicField.setHorizontalAlignment(SwingConstants.RIGHT);
        totalMusicField.setEditable(false);
        add(totalMusicField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 180, getWidth() - 50, 50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 22));
        depositButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 250, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 22));
        withdrawButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Past Transaction");
        pastTransactionButton.setBounds(15, 320, getWidth() - 50, 50);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 22));
        pastTransactionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 390, getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 22));
        transferButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(transferButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 500, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 22));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(logoutButton);

    }
}
