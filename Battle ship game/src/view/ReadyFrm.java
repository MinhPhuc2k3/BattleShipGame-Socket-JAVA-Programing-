package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.Sound;

public class ReadyFrm extends JFrame {

    private BattleShipGrid grid;
    private List<Ship> ships;
    private JPanel pnMain;
    private JButton btnReturn;
    private JButton btnReady;
    private JButton btnRandom;
    private Sound sound;

    public ReadyFrm() {
        setTitle("Battleship Game");
        setLayout(new BorderLayout());
        setSize(800, 700);
        sound = new Sound();
        btnReturn = new JButton("Quay lại");
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnTop.setPreferredSize(new Dimension(getWidth(), 60));
        pnTop.add(btnReturn);
        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnActionPerformed();
            }

        });

        btnReturn.setPreferredSize(new Dimension(80, 40));
        btnReturn.setLocation(5, 5);
        this.add(pnTop, BorderLayout.NORTH);
        pnMain = new JPanel();
        pnMain.setLayout(null);
        this.add(pnMain, BorderLayout.CENTER);

        grid = new BattleShipGrid(50);
        grid.setBounds(50, 50, 500, 500);
        pnMain.add(grid);
        grid.setLocation(this.getWidth() - grid.getWidth(), 5);
//        System.out.println("Location of gird: " + grid.getX() + "-" + grid.getY());
        // Ship panel where ships are placed initially
        JPanel shipPanel = new JPanel();
        shipPanel.setLayout(null);

        // Add ships
        ships = new ArrayList<>();
        ships.add(new Ship(5, grid, this, 50));
        ships.add(new Ship(4, grid, this, 50));
        ships.add(new Ship(3, grid, this, 50));
        ships.add(new Ship(3, grid, this, 50));
        ships.add(new Ship(2, grid, this, 50));

        int offset = 10;
        int xPlace = 5;
        int yPlace = 5;
        for (Ship ship : ships) {
            pnMain.add(ship);
            ship.setSize(50 * ship.getLength(), 50);
            ship.setLocation(xPlace, yPlace + 15);
            ship.setInitialPosition(new Point(ship.getX(), ship.getY()));
            yPlace += ship.getHeight() + offset;
            pnMain.repaint();
        }
        JPanel pnBottom = new JPanel();
        pnBottom.setLayout(new FlowLayout());
        btnRandom = new JButton("Ngẫu nhiên");
        btnRandom.setPreferredSize(new Dimension(100, 40));
        btnReady = new JButton("Sẵn sàng");
        btnReady.setPreferredSize(new Dimension(100, 40));
        pnBottom.add(btnRandom);
        pnBottom.add(btnReady);
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeShips();
                sound.soundDrag();
                pnMain.repaint();
            }

        });
        btnReady.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Ship> coordinationsOnScreen = new ArrayList<>();
                for(Ship s: ships) {
                    Point p = s.getLocation();
                    int px = (int) (p.getX()-grid.getLocation().getX())/grid.getCellSize();
                    int py = (int) (p.getY()-grid.getLocation().getY())/grid.getCellSize();
                    Ship newShip = new Ship();
                    newShip.setLocation(px, py);
                    newShip.setHorizontal(s.isHorizontal());
                    coordinationsOnScreen.add(newShip);
                }
                BattleViewFrm battleView = new BattleViewFrm(coordinationsOnScreen);
                battleView.showWindow();
                ReadyFrm.this.dispose();  
                sound.stop();
            }
        });
        this.add(pnBottom, BorderLayout.SOUTH);
    }

    public boolean isOverlap(Ship newShip) {
        for (Ship existingShip : ships) {
            if (existingShip != newShip && existingShip.getBounds().intersects(newShip.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private void randomizeShips() {
        int cellSize = 50;
        int gridSize = 10;  // Assuming a 10x10 grid

        for (Ship ship : ships) {
            boolean placed = false;

            while (!placed) {
                // Randomize orientation (horizontal/vertical)
                boolean isHorizontal = Math.random() > 0.5;
                ship.setHorizontal(isHorizontal);

                // Calculate random position within the grid
                int maxX = isHorizontal ? gridSize - ship.getLength() : gridSize;
                int maxY = isHorizontal ? gridSize : gridSize - ship.getLength();

                int randomX = (int) (Math.random() * maxX) * cellSize;
                int randomY = (int) (Math.random() * maxY) * cellSize;

                ship.setLocation(randomX + grid.getX(), randomY + grid.getY());
                // Check if the ship is placed without overlap
                if (ship.isInsideGrid(ship.getLocation()) && !isOverlap(ship)) {
                    pnMain.setComponentZOrder(ship, 0);
                    placed = true;  // Ship placed successfully
                    pnMain.repaint();
                }
            }
        }
    }

    private void btnActionPerformed() {
        this.dispose();
        MainFrm main = new MainFrm();
        main.showWindow();
    }

    public void showWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900, 700);
        setResizable(false);
        setVisible(true);
    }

}
