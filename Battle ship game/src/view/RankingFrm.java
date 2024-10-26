package view;

import controller.client.ClientControl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import utils.ImageManager;
import utils.Sound;

public class RankingFrm extends JFrame{
    
    private JLabel lblReturn;
    private JLabel lblRank;
    private MainFrm mainFrm;
    private Sound sound;
    private BufferedImage backgroundImg;
    private BufferedImage rankBg;
    private JTable tblRank;
    private ClientControl control;
    public RankingFrm(ClientControl control, MainFrm frm) {
        super("Bảng xếp hạng");
        this.mainFrm = frm;
        this.control = control;
        this.setSize(600, 500);
        this.sound = new Sound();
        this.backgroundImg = ImageManager.getImage(ImageManager.HISTORY_BACKGROUND_IMAGE);
        this.rankBg = ImageManager.getImage(ImageManager.RANK_BACKGROUND_IMAGE);
        this.setContentPane(new CustomPanel());
        setLayout(new BorderLayout(0, 10));
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        lblReturn = new JLabel("");
        ImageIcon returnIcon = new ImageIcon(getClass().getResource("/assets/icons/back.png"));
        lblReturn.setIcon(returnIcon);
        lblReturn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblReturn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                lblReturnActionPerformed();
            }
        });
        pnTop.add(lblReturn);
        pnTop.setOpaque(false);
        add(pnTop, BorderLayout.NORTH);
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        lblRank = new JLabel("Bảng xếp hạng");
        lblRank.setFont(new Font("Serif", Font.BOLD, 30));
        lblRank.setAlignmentX(CENTER_ALIGNMENT);
        pnMain.add(lblRank);
        
        JPanel pnTable = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTable.setOpaque(false);
        pnTable.setBorder(new EmptyBorder(10, 50, 30, 50));
        pnTable.add(lblRank);
        String[] columnNames = {"#","Nguời chơi", "Số trận thắng", "Số trận thua", "Tổng điểm"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        tblRank = new JTable(dtm);
        int tableWidth = rankBg.getWidth() * 80 / 100;
        int tableHeight = rankBg.getHeight() * 45 / 100;
        tblRank.setPreferredScrollableViewportSize(new Dimension(tableWidth, tableHeight));
        tblRank.setBackground(new Color(0,0,0,0));
        tblRank.getTableHeader().setBackground(new Color(0, 0, 0, 0)); 
        
        JScrollPane scrollPane = new JScrollPane(tblRank);
        scrollPane.getViewport().setOpaque(false);  // Đặt viewport trong suốt
        scrollPane.setBackground(new Color(0,0,0,0));
        pnTable.add(scrollPane);
        pnMain.add(pnTable);
        pnMain.setOpaque(false);
        
        add(pnMain, BorderLayout.CENTER);
    }
    
    private void lblReturnActionPerformed() {
        this.dispose();
        sound.soundButtonClick();
        mainFrm.setVisible(true);
    }
    
    public void showWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    private class CustomPanel extends javax.swing.JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // Calls the default painting of components
            // Draw the background image
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            int centerX = (getWidth()/2) - (rankBg.getWidth()*120/100 / 2);
            g.drawImage(rankBg, centerX, 10, rankBg.getWidth()*120/100, rankBg.getHeight()*85/100,null);
        }
    }
    
}
