package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BattleShipGrid extends JPanel {

    private static final int GRID_SIZE = 10;
    private List<Point> coordinations;
    
    public BattleShipGrid() {
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setPreferredSize(new Dimension(GRID_SIZE * 50, GRID_SIZE * 50));
        coordinations = new ArrayList<>();
//        int idxX = 0, idxY = 0;
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setPreferredSize(new Dimension(50, 50));  // Adjust cell size
            add(cell);
//            idxX++;
//            if(idxX%10==0) {
//                idxX = 0;
//                idxY += 1;
//            }
        }
        getCellCoordinates();
    }

    
    
    private void getCellCoordinates() {
//        List<Point> coordinates = new ArrayList<>();

        // Ensure components have been laid out before printing coordinates
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Component[] cells = getComponents();  // Get all child components (cells)
//                System.out.println("Location of grid: " + getX() + "-" + getY());
                int gridX = getX();
                int gridY = getY();
                for (int i = 0; i < cells.length; i++) {
                    Component cell = cells[i];
                    Point location = cell.getLocation();  // Get the location of each cell
//                    System.out.println("Cell " + i + " - X: " + location.x + ", Y: " + location.y);
                    coordinations.add(new Point(location.x + gridX, location.y + gridY));
//                    System.out.println("Location of cell: "+ (location.x + gridX)+"-"+(location.y + gridY));
                }

            }
        });
//        return coordinations;
    }

    public List<Point> getCoordinations() {
        return coordinations;   
    }
}
