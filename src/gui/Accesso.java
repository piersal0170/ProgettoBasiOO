package gui;

import controller.Controller;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accesso {
    private JTextField ssnField;
    private JLabel ssn;
    private JPanel ssnPanel;
    private JPanel panel1;
    private JTextField passwordField;
    private JLabel password;
    private JPanel passwordPanel;
    private JButton OKButton;
    private JButton homeButton;
    public JFrame frame;

    public Accesso(JFrame frameChiamante, Controller controller) {
        frame = new JFrame("Accesso");
        frame.setContentPane(panel1);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);

        OKButton.setEnabled(true);
        homeButton.setEnabled(true);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String errorMessage = controller.login(ssnField.getText(), passwordField.getText());
                if (errorMessage.equals("")) {
                    ProfiloUtente profiloUtente = new ProfiloUtente(controller);
                    profiloUtente.frame.setVisible(true);
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(frame, errorMessage, "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        ssnPanel = new JPanel();
        ssnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(ssnPanel);
        ssn = new JLabel();
        ssn.setText("Codice Fiscale");
        ssnPanel.add(ssn);
        ssnField = new JTextField();
        ssnField.setColumns(10);
        ssnPanel.add(ssnField);
        passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(passwordPanel);
        password = new JLabel();
        password.setText("Password");
        passwordPanel.add(password);
        passwordField = new JTextField();
        passwordField.setColumns(10);
        passwordPanel.add(passwordField);
        OKButton = new JButton();
        OKButton.setText("OK");
        panel1.add(OKButton);
        homeButton = new JButton();
        homeButton.setText("Home");
        panel1.add(homeButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
