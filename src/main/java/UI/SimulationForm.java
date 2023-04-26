package UI;

import BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationForm extends JDialog {
    private JTextField tf1;
    private JTextField tf7;
    private JTextField tf4;
    private JTextField tf5;
    private JTextField tf6;
    private JTextField tf3;
    private JTextField tf2;
    private JTextArea textArea1;
    private JButton simulationButton;
    private JPanel panel;

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public SimulationForm(JFrame parent) {
        super(parent);
        setTitle("Simulare");
        setContentPane(panel);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        simulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // SimulationManager simulationManager = new SimulationManager();
                int clienti = Integer.parseInt(tf1.getText());
                int servere = Integer.parseInt(tf2.getText());
                int timpSimulare = Integer.parseInt(tf3.getText());
                int minVenire = Integer.parseInt(tf4.getText());
                int minServire = Integer.parseInt(tf5.getText());
                int maxVenire = Integer.parseInt(tf6.getText());
                int maxServire = Integer.parseInt(tf7.getText());
                SimulationManager sim = new SimulationManager(clienti, servere, timpSimulare, minServire, maxServire, minVenire, maxVenire,SimulationForm.this);
                Thread t =new Thread(sim);
                t.start();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args){
        //SimulationManager gen= new SimulationManager();
        SimulationForm simf= new SimulationForm(null);

    }
}



