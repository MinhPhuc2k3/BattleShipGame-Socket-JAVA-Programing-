package utils;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private final URL background;
    private final URL click;
    private Clip clip;
    
    public Sound() {
        this.background = this.getClass().getClassLoader().getResource("assets/audio/background.wav");
        this.click = this.getClass().getClassLoader().getResource("assets/audio/button_click.wav");
    }

    public void soundBackground() {
        playSound(background, -20.0f, true);
    }
    
    public void soundButtonClick() {
        playSound(click, 0f, false);
    }

    public void stop() {
        if(clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
    
    private void playSound(URL url, float volume, boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Điều chỉnh âm lượng
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);  // Thiết lập âm lượng

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Nếu cần lặp lại âm thanh
            }
            audioIn.close();
            clip.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    
    private void playLoop(URL url) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Điều chỉnh âm lượng
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float volume = -20.0f; // Giảm âm lượng (dB). Mặc định 0.0f là âm lượng chuẩn
            volumeControl.setValue(volume);

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }

            });
//            audioIn.close();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
