package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class ProfiloUtente {
    private JPanel panel1;
    private JButton inserisciPaginaButton;
    private JButton rimuoviPaginaButton;
    private JButton modificaPaginaButton;
    private JButton proponiModificaButton;
    private JButton statoVersioniPaginaButton;
    private JButton modificheInStalloButton;
    private JButton logoutButton;
    private JButton eliminaAccountButton;
    private JButton versioniFraseButton;
    private JButton CollegamentoButton;
    public JFrame frame;

    public ProfiloUtente(Controller controller) {
        frame = new JFrame(controller.getNomeUtente());
        frame.setContentPane(panel1);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);

        inserisciPaginaButton.setEnabled(true);
        rimuoviPaginaButton.setEnabled(true);
        modificaPaginaButton.setEnabled(true);
        CollegamentoButton.setEnabled(true);
        proponiModificaButton.setEnabled(true);
        statoVersioniPaginaButton.setEnabled(true);
        versioniFraseButton.setEnabled(true);
        modificheInStalloButton.setEnabled(true);
        logoutButton.setEnabled(true);
        eliminaAccountButton.setEnabled(true);

        inserisciPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InserisciPagina paginaNuova = new InserisciPagina(frame, controller);
                paginaNuova.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        rimuoviPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> titoliPagine = controller.titoliPagine();
                if (titoliPagine.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Non sono presenti pagine da rimuovere", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    RimuoviPagina rimuoviPagina = new RimuoviPagina(frame, controller);
                    rimuoviPagina.frame.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home();
                home.frame.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        eliminaAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmChoice = JOptionPane.showConfirmDialog(frame, "Vuoi eliminare il tuo account? \n" +
                        "Perderai tutti i dati all'interno del database", "Warning", JOptionPane.YES_NO_OPTION);
                if (confirmChoice == JOptionPane.YES_OPTION) {
                    boolean utenteEliminato = controller.eliminaUtente();
                    if (utenteEliminato) {
                        JOptionPane.showMessageDialog(frame, "Eliminazione eseguita correttamente");
                        Home home = new Home();
                        home.frame.setVisible(true);
                        frame.setVisible(false);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "errore");
                    }
                } else if (confirmChoice == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Eliminazione annullata");
                }
            }
        });
        modificaPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> titoliPagine = controller.titoliPagine();
                if (titoliPagine.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Non sono presenti pagine da modificare", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ModificaPagina modificaPagina = new ModificaPagina(frame, controller);
                    modificaPagina.frame.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });
        proponiModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ricerca = JOptionPane.showInputDialog(frame, "Cerca la pagina che vuoi modificare");
                if (ricerca != null && ricerca.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci il titolo della pagina", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (ricerca != null) {
                    boolean cercaPagina = controller.cercaPagina(ricerca);
                    if (cercaPagina) {
                        boolean controlloSSN = controller.controlloPaginaSSN();
                        if (controlloSSN) {
                            JOptionPane.showMessageDialog(frame, "la pagina esiste");
                            ArrayList<String> frasi = controller.frasiRicerca();
                            if (frasi.isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Non sono presenti frasi da modificare", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                ProponiModificaFrase modificaFrase = new ProponiModificaFrase(frame, controller, frasi);
                                modificaFrase.frame.setVisible(true);
                                frame.setVisible(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Non puoi proporre una modifica a una tua pagina", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "La pagina non esiste", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        versioniFraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> titoliPagine = controller.titoliPagine();
                if (titoliPagine.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Error, non esistono pagine", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    String versioniFrase = "Cronologia versioni frase";
                    ListaPagina listaPagina = new ListaPagina(frame, controller, versioniFrase);
                    listaPagina.frame.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });
        modificheInStalloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> proposte = controller.visualizzaProposte();

                    if (proposte.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Non sono presenti proposte di modifiche a una tua pagina");
                    } else {
                        ModificheInStallo modifiche = new ModificheInStallo(frame, controller);
                        modifiche.frame.setVisible(true);
                        frame.setVisible(false);
                    }

            }
        });
        CollegamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> titoliPagine = controller.titoliPagine();
                if (titoliPagine.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Error, non esistono pagine", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirmChoice = JOptionPane.showConfirmDialog(frame, "Vuoi creare un nuovo collegamento?");
                    if (confirmChoice == JOptionPane.YES_OPTION) {
                        String collegamento = "Crea collegamento";
                        ListaPagina listaPagina = new ListaPagina(frame, controller, collegamento);
                        listaPagina.frame.setVisible(true);
                        frame.setVisible(false);
                    } else if (confirmChoice == JOptionPane.NO_OPTION) {
                        String collegamento = "Visualizza collegamento";
                        ListaPagina listaPagina = new ListaPagina(frame, controller, collegamento);
                        listaPagina.frame.setVisible(true);
                        frame.setVisible(false);
                    }
                }
            }
        });
        statoVersioniPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ricerca = JOptionPane.showInputDialog(frame, "Cerca la pagina di cui vuoi visualizzare lo storico");
                if (ricerca != null && ricerca.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci il titolo della pagina", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (ricerca != null) {
                    boolean cercaPagina = controller.cercaPagina(ricerca);
                    if (cercaPagina) {
                        boolean controlloSSN = controller.controlloPaginaSSN();
                        if (!controlloSSN) {
                            JOptionPane.showMessageDialog(frame, "la pagina esiste");
                            Map<String, Timestamp> versioni = controller.versioniPagina();
                            if (versioni.isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "Non sono presenti modifiche per questa pagina");
                            } else {
                                TimestampPagina timestampPagina = new TimestampPagina(frame, controller);
                                timestampPagina.frame.setVisible(true);
                                frame.setVisible(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Puoi visualizzare lo storico solo di una tua pagina", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "La pagina non esiste", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
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
        inserisciPaginaButton = new JButton();
        inserisciPaginaButton.setText("Inserisci pagina");
        panel1.add(inserisciPaginaButton);
        rimuoviPaginaButton = new JButton();
        rimuoviPaginaButton.setText("Rimuovi pagina");
        panel1.add(rimuoviPaginaButton);
        modificaPaginaButton = new JButton();
        modificaPaginaButton.setText("Modifica pagina");
        panel1.add(modificaPaginaButton);
        CollegamentoButton = new JButton();
        CollegamentoButton.setText("Gestione collegamenti");
        panel1.add(CollegamentoButton);
        proponiModificaButton = new JButton();
        proponiModificaButton.setText("Proponi modifica");
        panel1.add(proponiModificaButton);
        statoVersioniPaginaButton = new JButton();
        statoVersioniPaginaButton.setText("Stato delle versioni della pagina");
        panel1.add(statoVersioniPaginaButton);
        versioniFraseButton = new JButton();
        versioniFraseButton.setText("Cronologia versioni frase");
        panel1.add(versioniFraseButton);
        modificheInStalloButton = new JButton();
        modificheInStalloButton.setText("Modifiche in stallo");
        panel1.add(modificheInStalloButton);
        logoutButton = new JButton();
        logoutButton.setText("Logout");
        panel1.add(logoutButton);
        eliminaAccountButton = new JButton();
        eliminaAccountButton.setText("Elimina account");
        panel1.add(eliminaAccountButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
