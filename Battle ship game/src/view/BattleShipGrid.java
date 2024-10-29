package view;

import battle.ship.model.Ship;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BattleShipGrid extends JPanel {

    private static final int GRID_SIZE = 10;
    private int cellSize;
    private int [][] gridState; // 0 = empty, 1 = occupied by a ship
    private List<Ship> ships;
    JPanel[][] cells;
    
    public BattleShipGrid(int cellSize) {
        this.cellSize = cellSize;
        this.gridState = new int[GRID_SIZE][GRID_SIZE];
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setPreferredSize(new Dimension(GRID_SIZE * cellSize, GRID_SIZE * cellSize));
        cells = new JPanel[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for(int j=0; j< GRID_SIZE; j++){
                cells[i][j] = new JPanel();
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[i][j].setPreferredSize(new Dimension(cellSize, cellSize));  // Adjust cell size
                add(cells[i][j]);
            }
        }
    }

    public JPanel[][] getCells() {
        return cells;
    }

    public void setCells(JPanel[][] cells) {
        this.cells = cells;
    }

    public void setGridState(int[][] gridState) {
        this.gridState = gridState;
    }

    public int[][] getGridState() {
        return gridState;
    }
    
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public int getCellSize() {
        return cellSize;
    }
    
    public int shooteArea(int x, int y){
        return gridState[x][y];
    }
}
