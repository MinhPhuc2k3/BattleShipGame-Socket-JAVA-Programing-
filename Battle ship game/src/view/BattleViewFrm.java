package view;

import battle.ship.model.Ship;
import controller.client.ClientControl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleViewFrm extends JFrame {

    private ClientControl control;
    private int turn; //1 is your turn
    private BattleShipGrid leftGrid;
    private BattleShipGrid rightGrid;
    private List<Ship> ships;
    private JPanel pnMain;
    private JButton btnAction;
    private int hp = 17;
    private int hpOpponent = 17;
    private boolean[][] shooted = new boolean[10][10];
     private JLabel lblTurnStatus; // Thêm JLabel cho trạng thái lượt
    
    
    
    public BattleViewFrm(ClientControl control, List<Ship> ships, int turn) {
        this.ships = ships;
        this.turn = turn;
        this.control = control;
        setTitle("Battleship Battle View");
        setSize(1000, 800);
        setLayout(new BorderLayout());
        control.setBattleViewFrm(this);
        btnAction = new JButton("Action");
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnTop.add(btnAction);
        add(pnTop, BorderLayout.NORTH);
                this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                control.closeConnect();
            }
            
        });
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++) shooted[i][j] = false;
        }
        
              // Khởi tạo JLabel cho trạng thái lượt
        lblTurnStatus = new JLabel();
                try {
            // Nạp phông chữ từ file .ttf
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/font/Lalezar-Regular.ttf")).deriveFont(30f);
            lblTurnStatus.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            lblTurnStatus.setFont(new Font("Arial", Font.BOLD, 18)); // Dùng phông mặc định nếu có lỗi
        }
        lblTurnStatus.setHorizontalAlignment(JLabel.CENTER);
        updateTurnStatus(); // Cập nhật nội dung JLabel theo trạng thái lượt ban đầu

        // Thêm JLabel vào JPanel phía trên form
        pnTop.setLayout(new BorderLayout());
        pnTop.add(lblTurnStatus, BorderLayout.CENTER);

        
        
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
            leftGrid.setGridState(ship.getGrid().getGridState());
            // Set the new cell size for the ship to match the smaller grid
            ship.setCellSize(20);

            // Convert the ship's position from the previous grid to the new grid system
            int newWidth = ship.getWidth() / ship.getGrid().getCellSize() * ship.getCellSize();
            int newHeight = ship.getHeight() / ship.getGrid().getCellSize() * ship.getCellSize();
            Point p = ship.getLocation();
            int px = (int) ((p.getX() - ship.getGrid().getLocation().getX()) / ship.getGrid().getCellSize()) * leftGrid.getCellSize();
            int py = (int) ((p.getY() - ship.getGrid().getLocation().getY()) / ship.getGrid().getCellSize()) * leftGrid.getCellSize();

            // Set the correct size for the ship on the left grid
            ship.setSize(new Dimension(newWidth, newHeight));

            // Set the location of the ship on the left grid
            ship.setLocation(px + leftGrid.getX(), py + leftGrid.getY());

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
//        for(int i = 0; i < 10; i++) {
//            for(int j = 0; j < 10; j++) {
//                System.out.print(leftGrid.getGridState()[i][j]+ " ");
//            }
//            System.out.println("");
//        }
        // Right grid (for gameplay)
        rightGrid = new BattleShipGrid(50);
        rightGrid.setBounds(50, 50, 500, 500);
//        rightGrid.setPreferredSize(new Dimension(500, 500));  // Full size 10x10 grid for gameplay
        pnMain.add(rightGrid);
        Point centerPoint = new Point((getWidth() / 2) - (rightGrid.getWidth() / 2), (getHeight() / 2) - (rightGrid.getHeight() / 2));
        rightGrid.setLocation(centerPoint);
        rightGrid.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickedActionPerformed(e);
            }
        });
        // Add panel to frame
        add(pnMain, BorderLayout.CENTER);
    }

        private void updateTurnStatus() {
        // Cập nhật nội dung của JLabel theo trạng thái của biến turn
        if (turn == 1) {
            lblTurnStatus.setText("Lượt của bạn");
        } else {
            lblTurnStatus.setText("Lượt của đối thủ");
        }
    }
    
    public int getHp() {
        return hp;
    }
    
    public void mouseClickedActionPerformed(MouseEvent e){
        if(turn == 1){
            Point clickPoint = e.getPoint();
            int cellSize = rightGrid.getCellSize();
            int gridX = (int) (clickPoint.getY() / cellSize);
            int gridY = (int) (clickPoint.getX() / cellSize);
            System.out.println("Click point: "+ gridX+"-"+gridY);
            if(!shooted[gridX][gridY]) {
                control.shootRequest(gridX, gridY);
                shooted[gridX][gridY] = true;
                turn = 0;
            }else{
                JOptionPane.showMessageDialog(this, "Chọn ô khác");
            }
            
        }
    }
    
    public void showWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void loseHandle(){
        JOptionPane.showMessageDialog(this, "Bạn thua");
        endGame();
    }
    
    public int getHitStatus(int x, int y){
        JPanel cell = leftGrid.getCells()[x][y];
        if(leftGrid.shooteArea(x, y)){
            hp--;
            System.out.println(hp);
            cell.setBackground(Color.red);
            updateTurnStatus();
            if(hp == 0){
                return 0;
            }
            return 1;
        }else{
            turn = 1;
            updateTurnStatus();
            cell.setBackground(Color.blue);
            return 2;
        }
        
    }
    
    public void showHitStatus(int x, int y, boolean hit){
        JPanel cell = rightGrid.getCells()[x][y];
        if(hit){
            turn = 1;
            hpOpponent--;
            cell.setBackground(Color.red);
            if(hpOpponent == 0){
                //Xu li chien thang
                JOptionPane.showMessageDialog(this, "Bạn thắng");
                endGame();
            }
        }else{
            turn  = 0;
            cell.setBackground(Color.blue);
        }
        updateTurnStatus();
    }
    
    public void endGame(){
        control.getMainFrm().setVisible(true);
        control.updateListUserRequest();
        this.dispose();
    }
}
