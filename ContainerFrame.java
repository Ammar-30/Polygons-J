import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContainerFrame extends JFrame {
    private List<RegPolygon> polygonList;
    private JTextField sidesField, angleField, radiusField, idField, colorField;
    private JButton addButton, searchButton, sortButton;
    private ContainerPanel containerPanel;

    public ContainerFrame() {

        setSize(700,1000);
        setTitle("Polygons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        polygonList = new ArrayList<>();

        createInputPanel();
        createDrawingPanel();
        createButtonsPanel();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 5));

        JLabel sidesLabel = new JLabel("Sides");
        sidesField = new JTextField();
        JLabel angleLabel = new JLabel("Starting Angle");
        angleField = new JTextField();
        JLabel radiusLabel = new JLabel("  Radius");
        radiusField = new JTextField();
        JLabel idLabel = new JLabel("P-ID");
        idField = new JTextField();
        JLabel colorLabel = new JLabel("Colour");
        colorField = new JTextField();

        inputPanel.add(sidesLabel);
        inputPanel.add(angleLabel);
        inputPanel.add(radiusLabel);
        inputPanel.add(idLabel);
        inputPanel.add(colorLabel);
        inputPanel.add(sidesField);
        inputPanel.add(angleField);
        inputPanel.add(radiusField);
        inputPanel.add(idField);
        inputPanel.add(colorField);

        add(inputPanel, BorderLayout.NORTH);
    }

    private void createDrawingPanel() {
        containerPanel = new ContainerPanel();
        add(containerPanel, BorderLayout.CENTER);
    }

    private void createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();

        addButton = new JButton("Add Polygon");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPolygon();
            }
        });

        searchButton = new JButton("Search by P-ID");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPolygonById();
            }
        });

        sortButton = new JButton("Sort and Display");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortAndDisplay();
            }
        });

        buttonsPanel.add(addButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sortButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void addPolygon() {
        // Retrieve user input
        int sides = Integer.parseInt(sidesField.getText());
        double angle = Double.parseDouble(angleField.getText());
        double radius = Double.parseDouble(radiusField.getText());
        int id = Integer.parseInt(idField.getText());

        String colorName = JOptionPane.showInputDialog(this, "Enter Color (e.g., black, blue, red):");


        if (colorName == null || colorName.trim().isEmpty()) {
            System.out.println("Color not provided. Please enter a color.");
            return;
        }

        Color color;
        try {
            color = (Color) Color.class.getField(colorName.toUpperCase()).get(null);
        } catch (Exception e) {
            System.out.println("Invalid color name. Please enter a valid color name.");
            return;
        }

        RegPolygon polygon = new RegPolygon(sides, angle, radius, id, color);

        polygonList.add(polygon);

        System.out.println("Polygon added successfully.");
    }



    private void searchPolygonById() {

        int searchId = Integer.parseInt(idField.getText());

        // Search for the polygon in the list
        RegPolygon foundPolygon = null;
        for (RegPolygon polygon : polygonList) {
            if (polygon.getId() == searchId) {
                foundPolygon = polygon;
                break;
            }
        }

        // Draw the polygon on the container panel
        if (foundPolygon != null) {
            containerPanel.drawPolygon(foundPolygon);
        } else {
            System.out.println("Polygon with ID " + searchId + " not found.");
        }
    }

    private void sortAndDisplay() {
        // Sort the list of polygons based on their IDs
        Collections.sort(polygonList);

        for (RegPolygon polygon : polygonList) {
            System.out.println(polygon);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContainerFrame();
            }
        });
    }
}
