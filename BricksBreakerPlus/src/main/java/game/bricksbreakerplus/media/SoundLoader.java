package game.bricksbreakerplus.media;

import game.bricksbreakerplus.Loader;
import game.bricksbreakerplus.Resource;
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

        Media explosionMedia = new Media(new File(Loader.getExplosionSound()).toURI().toString());
        explosionPlayer = new MediaPlayer(explosionMedia);

//        mediaPlayer = new MediaPlayer(explosionMedia);

        Media touchMedia = new Media(new File(Loader.getTouchSound()).toURI().toString());
        touchPlayer = new MediaPlayer(touchMedia);
    }
    public void playOnRepeat(){
        if(!Resource.isPlayMusic())
            return;
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }
    public void playExplosion(){
        explosionPlayer.play();
        explosionPlayer.setOnEndOfMedia(() -> {
            playOnRepeat();
        });
    }
    public void playTouch(){
        touchPlayer.play();
        touchPlayer.setOnEndOfMedia(() -> {
            playOnRepeat();
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
