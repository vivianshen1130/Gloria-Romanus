package unsw.gloriaromanus.music;

import java.io.File;
import java.net.URL;

// import javax.print.DocFlavor.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class MusicApplication extends Application {

    private static MediaPlayer menuMusic = null;
    private static MediaPlayer gameMusic = null;
    private static MediaPlayer victoryMusic = null;

    /**
     * Initialises music player
     */
    @Override
    public void start(Stage primaryStage) {
        if (menuMusic != null) {
            stopMenuMusic();
        }

        URL resource = getClass().getResource("music.mp3");
        Media media = new Media(resource.toString());
        gameMusic = new MediaPlayer(media);
        gameMusic.play();

        gameMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                gameMusic.seek(Duration.ZERO);
            }
        });
    }

    public void stopGameMusic() {
        gameMusic.stop();
    }

    public void resumeGameMusic() {
        if (gameMusic == null) {
            return;
        }

        gameMusic.play();

        gameMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                gameMusic.seek(Duration.ZERO);
            }
        });
    }

    public void startMenuMusic() {
        if (gameMusic != null) {
            stopGameMusic();
        }

        if (victoryMusic != null) {
            stopVictoryMusic();
        }

        URL resource = getClass().getResource("menuMusic.mp3");
        Media media = new Media(resource.toString());
        menuMusic = new MediaPlayer(media);
        menuMusic.play();

        menuMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                menuMusic.seek(Duration.ZERO);
            }
        });
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }

    public void startVictoryMusic() {
        if (gameMusic != null) {
            stopGameMusic();
        }

        URL resource = getClass().getResource("victoryMusic.mp3");
        Media media = new Media(resource.toString());
        victoryMusic = new MediaPlayer(media);
        victoryMusic.play();

        victoryMusic.setOnEndOfMedia(new Runnable() {
            public void run() {
                victoryMusic.seek(Duration.ZERO);
            }
        });
    }

    public void stopVictoryMusic() {
        victoryMusic.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
