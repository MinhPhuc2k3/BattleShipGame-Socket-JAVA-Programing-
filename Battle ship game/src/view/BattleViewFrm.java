package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BattleViewFrm extends JFrame {

    private BattleShipGrid leftGrid;
    private BattleShipGrid rightGrid;
    private List<Ship> ships;
    private JPanel pnMain;
    private JButton btnAction;

    public BattleViewFrm(List<Ship> ships) {
        this.ships = ships;
        setTitle("Battleship Battle View");
        setSize(1000, 800);
        setLayout(new BorderLayout());

        btnAction = new JButton("Action");
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnTop.add(btnAction);
        add(pnTop, BorderLayout.NORTH);
        
        // Panel for both grids
        pnMain = new JPanel();
        pnMain.setLayout(null);
//        pnMain.setSize(new Dimension(1000, 800));
        // Left grid (showing the ships)
        leftGrid = new BattleShipGrid(20);
        leftGrid.setBounds(20, 20, 200, 200);
        pnMain.add(leftGrid);
        leftGrid.setLocation(10, 10);
        for (Ship ship : ships) {
            System.out.println("Location of ship has size: " + ship.getLength());

            // Set the new cell size for the ship to match the smaller grid
            ship.setCellSize(20);

            // Convert the ship's position from the previous grid to the new grid system
            int newWidth = ship.getWidth() / ship.getGrid().getCellSize() * ship.getCellSize();
            int newHeight = ship.getHeight() / ship.getGrid().getCellSize() * ship.getCellSize();
            System.out.println("new width: " + newWidth);
            System.out.println("new height: " + newHeight);
            Point p = ship.getLocation();
            int px = (int) ((p.getX() - ship.getGrid().getLocation().getX()) / ship.getGrid().getCellSize()) * leftGrid.getCellSize();
            int py = (int) ((p.getY() - ship.getGrid().getLocation().getY()) / ship.getGrid().getCellSize()) * leftGrid.getCellSize();

            // Set the correct size for the ship on the left grid
            ship.setSize(new Dimension(newWidth, newHeight));

            // Set the location of the ship on the left grid
            ship.setLocation(px + leftGrid.getX(), py + leftGrid.getY());
            System.out.println("X: " + ship.getX() + "- Y: " + ship.getY());

            // Set the grid to the left grid
            ship.setGrid(leftGrid);

            for (MouseMotionListener mml : ship.getMouseMotionListeners()) {
                ship.removeMouseMotionListener(mml);
            }

            for (MouseListener ml : ship.getMouseListeners()) {
                ship.removeMouseListener(ml);
            }

            // Add the ship to the panel and repaint
            pnMain.add(ship);
            pnMain.setComponentZOrder(ship, 0);
            pnMain.repaint();
        }

        // Right grid (for gameplay)
        rightGrid = new BattleShipGrid(50);
        rightGrid.setBounds(50, 50, 500, 500);
//        rightGrid.setPreferredSize(new Dimension(500, 500));  // Full size 10x10 grid for gameplay
        pnMain.add(rightGrid);
        Point centerPoint = new Point((getWidth() / 2) - (rightGrid.getWidth() / 2), (getHeight() / 2) - (rightGrid.getHeight() / 2));
        rightGrid.setLocation(centerPoint);
        // Add panel to frame
        add(pnMain, BorderLayout.CENTER);
    }

    public void showWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
