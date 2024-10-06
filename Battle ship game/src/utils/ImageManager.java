package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageManager {
    
    public static final String LOGIN_IMAGE = "Login_background_resized.jpg";
    
    public static BufferedImage getImage(String filename) {
        BufferedImage img = null;
        InputStream is = ImageManager.class.getResourceAsStream("/assets/images/"+filename);
        try {
            img = ImageIO.read(is);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return img;
    }
    
}
