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
    private int cellSize;
    
    public BattleShipGrid(int cellSize) {
        this.cellSize = cellSize;
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setPreferredSize(new Dimension(GRID_SIZE * cellSize, GRID_SIZE * cellSize));
        coordinations = new ArrayList<>();
//        int idxX = 0, idxY = 0;
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            JPanel cell = new JPanel();
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setPreferredSize(new Dimension(cellSize, cellSize));  // Adjust cell size
            add(cell);
        }
    }

    public int getCellSize() {
        return cellSize;
    }

}
