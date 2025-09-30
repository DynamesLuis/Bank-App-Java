import db_objs.User;
import gui.BankingAppGUI;
import gui.LoginGUI;
import gui.RegisterGUI;

import javax.swing.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI().setVisible(true);
                //new RegisterGUI().setVisible(true);
                //new BankingAppGUI(new User(1, "dynames", "1234", new BigDecimal("20.00"))).setVisible(true);
            }
        });
    }
}