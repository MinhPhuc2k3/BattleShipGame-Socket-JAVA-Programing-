
package dto;

import java.io.Serializable;

/**
 *
 * @author MINH PHUC
 */
public class ExitGameDTO implements Serializable{
    private boolean exit;

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
    
}
