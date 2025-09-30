package gui;

import db_objs.JDBC;
import db_objs.Transaction;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGUI bankingAppGUI;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField,enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(User user, BankingAppGUI bankingAppGUI) {
        setSize(400, 400);
        setModal(true);
        setLocationRelativeTo(bankingAppGUI);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.bankingAppGUI = bankingAppGUI;
        this.user = user;
    }

    public void addCurrentBalanceAndAmout() {
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionButtonType) {
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        add(enterUserField);
    }

    public void handleTransaction(String transactionType, float amountVal) {
        Transaction transaction;
        if (transactionType.equalsIgnoreCase("Deposit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        } else {
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }

        if(JDBC.addTransactionToDatabase(transaction) && JDBC.updateCurrentBalance(user)){
            // show success dialog
            JOptionPane.showMessageDialog(this, transactionType + " Successfully!");

            // reset the fields
            resetFieldsAndUpdateCurrentBalance();
        }else{
            // show failure dialog
            JOptionPane.showMessageDialog(this, transactionType + " Failed...");
        }
    }

    private void resetFieldsAndUpdateCurrentBalance(){
        // reset fields
        enterAmountField.setText("");

        // only appears when transfer is clicked
        if(enterUserField != null){
            enterUserField.setText("");
        }

        // update current balance on dialog
        balanceLabel.setText("Balance: $" + user.getCurrentBalance());

        // update current balance on main gui
        bankingAppGUI.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, float amount){
        // attempt to perform transfer
        if(JDBC.transfer(user, transferredUser, amount)){
            // show success dialog
            JOptionPane.showMessageDialog(this, "Transfer Success!");
            resetFieldsAndUpdateCurrentBalance();
        }else{
            // show failure dialog
            JOptionPane.showMessageDialog(this, "Transfer Failed...");
        }
    }

    public void addPastTransactionComponents(){
        pastTransactionPanel = new JPanel();
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        pastTransactions = JDBC.getPastTransaction(user);
        for(int i = 0; i < pastTransactions.size(); i++){
            Transaction pastTransaction = pastTransactions.get(i);
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());
            JLabel transactionTypeLabel = new JLabel(pastTransaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST); // place this on the west side
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST); // place this on the east side
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH); // place this on the south side
            pastTransactionContainer.setBackground(Color.WHITE);
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pastTransactionPanel.add(pastTransactionContainer);
        }

        add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String buttonPressed = actionEvent.getActionCommand();
        float amountVal = Float.parseFloat(enterAmountField.getText());
        if (buttonPressed.equalsIgnoreCase("Deposit")) {
            handleTransaction(buttonPressed, amountVal);
        } else {
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));
            if (result < 0) {
                // display error dialog
                JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                return;
            }

            // check to see if withdraw or transfer was pressed
            if (buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountVal);
            } else {
                // transfer
                String transferredUser = enterUserField.getText();

                // handle transfer
                handleTransfer(user, transferredUser, amountVal);
            }
        }
    }
}
