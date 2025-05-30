
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class TrainGUI extends JFrame {
    private TrainManager manager;
    private JTextArea displayArea;
    private JTextField nameField, startField, endField, stopsField;
    private JTextField searchStartField, searchEndField;

    public TrainGUI() {
        manager = new TrainManager();
        setTitle("Train Route Manager");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        nameField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();
        stopsField = new JTextField();
        inputPanel.add(new JLabel("Train Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Start Station:"));
        inputPanel.add(startField);
        inputPanel.add(new JLabel("End Station:"));
        inputPanel.add(endField);
        inputPanel.add(new JLabel("Stops (comma separated):"));
        inputPanel.add(stopsField);

        JButton addButton = new JButton("Add Train");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String start = startField.getText();
            String end = endField.getText();
            List stops = (List) Arrays.asList(stopsField.getText().split(","));
            manager.addTrain(new Train(name, start, end, stops));
            updateDisplay();
        });
        inputPanel.add(addButton);

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(e -> {
            try {
                manager.saveToFile("trains.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        searchStartField = new JTextField();
        searchEndField = new JTextField();
        searchPanel.add(new JLabel("Search From:"));
        searchPanel.add(searchStartField);
        searchPanel.add(new JLabel("Search To:"));
        searchPanel.add(searchEndField);

        JButton searchButton = new JButton("Search Route");
        searchButton.addActionListener(e -> {
            String from = searchStartField.getText();
            String to = searchEndField.getText();
            List route = (List) manager.findRoute(from, to);
            displayArea.setText("Route: " + route);
        });

        add(searchPanel, BorderLayout.SOUTH);
        add(searchButton, BorderLayout.WEST);

        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(e -> {
            try {
                manager.loadFromFile("trains.txt");
                updateDisplay();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(loadButton, BorderLayout.EAST);
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Train t : manager.getTrains()) {
            sb.append(t).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrainGUI().setVisible(true);
        });
    }
}
