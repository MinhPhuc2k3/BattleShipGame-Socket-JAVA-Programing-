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

public class ReadyFrm extends JFrame{
    private BattleShipGrid grid;
    private List<Ship> ships;
    private JPanel pnMain;
    private JButton btnReturn;

    public ReadyFrm() {
        setTitle("Battleship Game");
        setLayout(new BorderLayout());
        setSize(800, 700);
        
        btnReturn = new JButton("Quay lại");
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnTop.setPreferredSize(new Dimension(getWidth(), 60));
        pnTop.add(btnReturn);
        btnReturn.addActionListener(new ActionListener(){
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
        
        grid = new BattleShipGrid();
        grid.setBounds(50, 50, 500, 500);
        pnMain.add(grid);
        grid.setLocation(this.getWidth()-grid.getWidth(), 5);
        System.out.println("Location of gird: "+ grid.getX()+"-"+grid.getY());
        // Ship panel where ships are placed initially
        JPanel shipPanel = new JPanel();
        shipPanel.setLayout(null);
        
        // Add ships
        ships = new ArrayList<>();
        ships.add(new Ship(5, grid));
        ships.add(new Ship(4, grid));
        ships.add(new Ship(3, grid));
        ships.add(new Ship(3, grid));
        ships.add(new Ship(2, grid));

        int offset= 10;
        int xPlace = 5;
        int yPlace = 5;
        for (Ship ship : ships) {
            pnMain.add(ship);
            ship.setSize(50*ship.getLength(), 50);
            ship.setLocation(xPlace, yPlace+15);
            ship.setInitialPosition(new Point(ship.getX(), ship.getY()));
//            ship.setGrid(grid);
            yPlace += ship.getHeight()+offset;
            pnMain.repaint();
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
//        setResizable(false);
        setVisible(true);
    }
    
}
