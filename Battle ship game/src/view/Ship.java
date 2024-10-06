package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

public class Ship extends DraggableComponent {

    private int length;
    private boolean isHorizontal = true;  // Initial orientation
    private Point initialPosition;
    private BattleShipGrid grid;

    public Ship(int length, BattleShipGrid grid) {
        this.length = length;
        this.grid = grid;
        setOpaque(true);
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(length * 50, 50));  // Horizontal size

        // Store the initial position of the ship
        initialPosition = getLocation();

        // Enable Drag and Drop
        setTransferHandler(new TransferHandler("ship"));

        // Right-click for rotating the ship
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    rotate();
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                handleDrop();
            }
        });
    }

    public void setGrid(BattleShipGrid grid) {
        this.grid = grid;
    }

    public void setInitialPosition(Point initialPosition) {
        this.initialPosition = initialPosition;
    }

    public int getLength() {
        return length;
    }

    // Rotate the ship between horizontal and vertical
    public void rotate() {
        if (isHorizontal) {
            setPreferredSize(new Dimension(50, length * 50));  // Vertical size
        } else {
            setPreferredSize(new Dimension(length * 50, 50));  // Horizontal size
        }
        isHorizontal = !isHorizontal;
        setSize(getPreferredSize());
        revalidate();
        repaint();
    }

    private void handleDrop() {
        // Get the current position of the ship
        Point shipPosition = getLocation();
        System.out.println("Location of Component: "+shipPosition.getX()+"-"+shipPosition.getY());
        int cellSize = 50;
        
        int gridx = (int) ((shipPosition.getX() - grid.getX())/cellSize)*cellSize + grid.getX();
        int gridy = (int) ((shipPosition.getY() - grid.getY())/cellSize)*cellSize + grid.getY();
        System.out.println("gridx: "+ gridx + "- gridy: "+gridy);
        setLocation(gridx, gridy);
        
        // Calculate the center of the ship
//        double gx = shipPosition.getX() + (getWidth() / 2);
//        double gy = shipPosition.getY() + (getHeight() / 2);
//        Point centerPoint = new Point((int) gx, (int) gy);
//
//        // Calculate the nearest grid position based on the center
//        Point nearestPoint = getNearestPoint(centerPoint.x, centerPoint.y);
//
//        if (isInsideGrid(centerPoint)) {
//            // Snap to the nearest point (grid)
//            snapToGrid(nearestPoint);
//        } else {
//            // Reset to the original position if dropped outside
//            setLocation(initialPosition);
//        }
    }

    private Point getNearestPoint(int x, int y) {
        List<Point> cordinates = grid.getCoordinations();
        double minDistance = Double.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < cordinates.size(); i++) {
            double distance = Math.sqrt(Math.pow(cordinates.get(i).getX() - x, 2) + Math.pow(cordinates.get(i).getY() - y, 2));
//            System.out.println("Distance: " + distance);
            if (distance < minDistance) {
                minDistance = distance;
                idx = i;
            }
        }
        return cordinates.get(idx);
    }

    // Check if the drop point is inside the grid area
    private boolean isInsideGrid(Point dropPoint) {
        Rectangle gridBounds = grid.getBounds();
        return gridBounds.contains(dropPoint);
    }

    // Snap the ship to the nearest grid cell
    private void snapToGrid(Point nearestPoint) {
        int gridX = (int) nearestPoint.getX();
        int gridY = (int) nearestPoint.getY();

        // Adjust for ship orientation and length
        if (isHorizontal) {
            if (gridX + getWidth() > grid.getWidth()) {
                gridX = grid.getWidth() - getWidth();  // Prevent overflow
            }
        } else {
            if (gridY + getHeight() > grid.getHeight()) {
                gridY = grid.getHeight() - getHeight();  // Prevent overflow
            }
        }

        // Set new location in grid, snapping the ship to the nearest grid cell
        setLocation(gridX, gridY);
    }
}
