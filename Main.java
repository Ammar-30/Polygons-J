import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RegPolygon implements Comparable<RegPolygon> {
    private int sides;
    private double startingAngle;
    private double radius;
    private int id;
    private Color color;

    public RegPolygon(int sides, double startingAngle, double radius, int id, Color color) {
        this.sides = sides;
        this.startingAngle = startingAngle;
        this.radius = radius;
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }


    public void draw(Graphics g, int centerX, int centerY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        int[] xPoints = new int[sides];
        int[] yPoints = new int[sides];
        for (int i = 0; i < sides; i++) {
            double angle = startingAngle + 2 * Math.PI * i / sides;
            xPoints[i] = (int) (centerX + radius * Math.cos(angle));
            yPoints[i] = (int) (centerY + radius * Math.sin(angle));
        }
        g2d.drawPolygon(xPoints, yPoints, sides);
    }

    @Override
    public int compareTo(RegPolygon other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "RegPolygon{id=" + id + ", sides=" + sides + ", startingAngle=" + startingAngle +
                ", radius=" + radius ;
    }
}

class ContainerPanel extends JPanel {
    private RegPolygon currentPolygon;

    public void drawPolygon(RegPolygon polygon) {
        currentPolygon = polygon;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentPolygon != null) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            currentPolygon.draw(g, centerX, centerY);
        }
    }
}
