package fx.games.ninjafrog;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Effect {
    AudioClip clip;
    double volume;


    public Effect(String url,double volume){
        this.volume=volume;
        clip = new AudioClip(getClass().getResource(url).toString());
        clip.setVolume(volume);
        clip.play();
    }
}
