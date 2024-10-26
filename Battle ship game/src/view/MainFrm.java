package view;

import battle.ship.model.Player;
import controller.client.ClientControl;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.ImageManager;
import utils.Sound;

/**
 *
 * @author win
 */
public class MainFrm extends javax.swing.JFrame {

    private ClientControl control;
    private Player player;
    private List<Player> playerList;
    private Map<Integer, Player> getPlayerById;
    private Set<String> markOnline;

    private Sound sound;
    private BufferedImage backgroundImg;
    private DefaultTableModel onlineTableModel;
    private DefaultTableCellRenderer renderer;

    public MainFrm(ClientControl control, Player player) {
        initComponents();
        this.control = control;
        this.player = player;
        this.lblAvarta.setText(player.getUsername());
        markOnline = new HashSet<>();
        backgroundImg = ImageManager.getImage(ImageManager.MAIN_BACKGROUND_IMAGE);
        onlineTableModel = (DefaultTableModel) playerTable.getModel();
        this.control.setMainFrm(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                control.closeConnect();
            }

        });
        renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Gọi phương thức gốc để giữ nguyên tính năng của bảng
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Kiểm tra giá trị của cột trạng thái ("Online"/"Offline")
                String status = (String) table.getValueAt(row, 1); // Cột trạng thái (giả định là cột thứ 2)

                if (status.equals("Online")) {
                    c.setBackground(Color.GREEN);  // Đặt nền màu xanh cho các dòng online
                } else {
                    c.setBackground(Color.LIGHT_GRAY);  // Đặt nền màu xám cho các dòng offline
                }

                // Nếu hàng được chọn, giữ màu nền khi chọn
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }

                return c;
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMain = pnMain = new CustomPanel();
        lblAvarta = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();
        lblRank = new javax.swing.JLabel();
        lblSetting = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAvarta.setBackground(new java.awt.Color(204, 204, 204));
        lblAvarta.setForeground(new java.awt.Color(255, 255, 255));
        lblAvarta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvarta.setText("Avatar");
        lblAvarta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lblAvarta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAvarta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvartaMouseClicked(evt);
            }
        });

        playerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Trạng thái", "Điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(playerTable);

        lblRank.setBackground(new java.awt.Color(255, 255, 255));
        lblRank.setForeground(new java.awt.Color(255, 255, 255));
        lblRank.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/ranking.png"))); // NOI18N
        lblRank.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRank.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRankMouseClicked(evt);
            }
        });

        lblSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/setting.png"))); // NOI18N
        lblSetting.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSettingMouseClicked(evt);
            }
        });

        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/logout.png"))); // NOI18N
        lblLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        btnPlay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons/fight.png"))); // NOI18N
        btnPlay.setText("Chiến");
        btnPlay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnMainLayout = new javax.swing.GroupLayout(pnMain);
        pnMain.setLayout(pnMainLayout);
        pnMainLayout.setHorizontalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnMainLayout.createSequentialGroup()
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblAvarta, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRank, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lblSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(lblLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addContainerGap(195, Short.MAX_VALUE)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        pnMainLayout.setVerticalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPlay)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAvarta, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRank)
                            .addComponent(lblSetting)
                            .addComponent(lblLogout))
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updatePlayerTable(List<Player> players, List<Player> playersOnline) {
        playerList = players;
        markOnline.clear();
        getPlayerById = new HashMap<>();
        onlineTableModel.setRowCount(0);
        for (Player player : playersOnline) {
            markOnline.add(player.getUsername());
        }
        Collections.sort(players, (o1, o2) -> {
            if (markOnline.contains(o1.getUsername()) == true) {
                return -1;
            }
            return 1;
        });
        for (Player tmpPlayer : players) {
            if(tmpPlayer.getId()!=this.player.getId())
            onlineTableModel.addRow(new Object[]{
                tmpPlayer.getUsername(),
                (markOnline.contains(tmpPlayer.getUsername()) == true ? "Online" : "Offline"),
                tmpPlayer.getDiem()});
        }
        for(Player player: playerList){
            getPlayerById.put(player.getId(), player);
            System.out.println(getPlayerById.get(player.getId()).getUsername());
        }
        
        for (int i = 0; i < playerTable.getColumnCount(); i++) {
            playerTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public int showRequestBattle(Player opponent) {
        return JOptionPane.showConfirmDialog(this, opponent.getUsername() + " muốn chơi với bạn!", "Xác nhận", JOptionPane.YES_NO_OPTION);
    }

    public void playGame(boolean accept) {
        if (accept) {
            ReadyFrm readyFrm = new ReadyFrm(control, this);
            readyFrm.showWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Đối thủ không chấp nhận chơi");
        }
    }

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        sound.stop();
        sound.soundButtonClick();
        int row = playerTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showConfirmDialog(this, "Vui lòng chọn một đối thủ trước");
        } else {
            Player opponent = playerList.get(row);
            System.out.println(opponent.getUsername());
            control.battleRequire(opponent);
        }
    }//GEN-LAST:event_btnPlayActionPerformed

    private void lblRankMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRankMouseClicked
        sound.soundButtonClick();
        RankingFrm ranking = new RankingFrm(control, this);
        ranking.showWindow();
    }//GEN-LAST:event_lblRankMouseClicked

    private void lblSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingMouseClicked
        sound.soundButtonClick();
    }//GEN-LAST:event_lblSettingMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        sound.stop();
        sound.soundButtonClick();
        LoginFrm login = new LoginFrm(control);
        control.logout();
        login.showWindow();
        this.dispose();
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblAvartaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvartaMouseClicked
        this.setVisible(false);
        sound.stop();
        MatchHistoryFrm history = new MatchHistoryFrm(control, this, getPlayerById);
        history.showWindow();
    }//GEN-LAST:event_lblAvartaMouseClicked

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), null);
        pnMain.paintComponents(g);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g); 
//        draw(g);
////        repaint();
//    }
    // Custom panel for painting the background
    private class CustomPanel extends javax.swing.JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // Calls the default painting of components
            // Draw the background image
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void showWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        sound = new Sound();
        //sound.soundBackground();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvarta;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblRank;
    private javax.swing.JLabel lblSetting;
    private javax.swing.JTable playerTable;
    private javax.swing.JPanel pnMain;
    // End of variables declaration//GEN-END:variables
}
