package org.example.view.graphicView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    static MediaPlayer clickMediaPlayer;
    public static void playClickSound() {

        String path = "src\\main\\resources\\Sound\\click.mp3";

        Media media = new Media(new File(path).toURI().toString());

        clickMediaPlayer = new MediaPlayer(media);

        clickMediaPlayer.setAutoPlay(true);
    }

    public static void changeMusic(int number){
        clickMediaPlayer.stop();
        String path = "src\\main\\resources\\Music\\"+number+".mp3";
        Media media = new Media(new File(path).toURI().toString());
        clickMediaPlayer = new MediaPlayer(media);
    }
}
