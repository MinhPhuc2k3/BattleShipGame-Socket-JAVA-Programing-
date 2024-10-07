package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BattleViewFrm extends JFrame{
    
    private BattleShipGrid leftGrid;  
    private BattleShipGrid rightGrid;  
    private List<Ship> ships;  
    private JPanel pnMain;
    
    public BattleViewFrm(List<Ship> ships) {
        this.ships = ships;
        setTitle("Battleship Battle View");
        setSize(1000, 800);  
        setLayout(new BorderLayout());

        // Panel for both grids
        pnMain = new JPanel();
        pnMain.setLayout(null);  
        add(pnMain, BorderLayout.CENTER);

        // Left grid (showing the ships)
        leftGrid = new BattleShipGrid(20);
        leftGrid.setBounds(20, 20, 200, 200);
//        leftGrid.setPreferredSize(new Dimension(250, 250));  // 10x10 grid with smaller size
        for (Ship ship : ships) {
            System.out.println("Location of ship has size: "+ ship.getLength());
            System.out.println("X: "+ ship.getLocation().getX()+"- Y: "+ ship.getLocation().getY());
            ship.setCellSize(leftGrid.getCellSize());
            ship.setLocation(ship.getX()+leftGrid.getX(), ship.getY()+leftGrid.getY());
//            pnMain.setComponentZOrder(ship, 0);
            pnMain.repaint();
        }
        
        pnMain.add(leftGrid);
        leftGrid.setLocation(10, 10);
        // Right grid (for gameplay)
        rightGrid = new BattleShipGrid(50);
        rightGrid.setBounds(50, 50, 500, 500);
//        rightGrid.setPreferredSize(new Dimension(500, 500));  // Full size 10x10 grid for gameplay
        pnMain.add(rightGrid);
        Point centerPoint = new Point((getWidth()/2)-(rightGrid.getWidth()/2), (getHeight()/2)-(rightGrid.getHeight()/2));
        rightGrid.setLocation(centerPoint);
        // Add panel to frame
        
    }

    public void showWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
