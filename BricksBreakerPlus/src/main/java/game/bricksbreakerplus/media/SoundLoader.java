package game.bricksbreakerplus.media;

import game.bricksbreakerplus.Loader;
import game.bricksbreakerplus.graphic.SceneLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SoundLoader {
    MediaPlayer mediaPlayer, explosionPlayer, touchPlayer;
    public SoundLoader() {
        Media media = new Media(new File(Loader.getThemeMusic()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Media explosionMedia = new Media(Loader.getExplosionSound());
        explosionPlayer = new MediaPlayer(explosionMedia);

        Media touchMedia = new Media(Loader.getTouchSound());
        touchPlayer = new MediaPlayer(touchMedia);
    }
    public void playOnRepeat(){
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }
    public void playExplosion(){
        explosionPlayer.play();
        explosionPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.play();
        });
    }
    public void playTouch(){
        touchPlayer.play();
        touchPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.play();
            // here for explosion sound error maybe
            if(explosionPlayer.isAutoPlay())
                explosionPlayer.play();
        });
    }
    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }
}
