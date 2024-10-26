package battle.ship.game;

import controller.client.ClientControl;
import view.LoginFrm;

public class BattleShipGame {

    public static void main(String[] args) {
        ClientControl control = new ClientControl();
        LoginFrm frm = new LoginFrm(control);
        frm.showWindow();
    }
    
}
