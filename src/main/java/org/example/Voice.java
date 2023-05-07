package org.example;

import org.example.model.unit.MilitaryUnit;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public enum Voice {
    ARCHER("VoiceData/3.wav"),
    CROSSBOW_MEN("VoiceData/4.wav"),
    SPEAR_MEN("VoiceData/5.wav"),
    PIKE_MEN("VoiceData/6.wav"),
    MACE_MEN("VoiceData/7.wav"),
    SWORDSMEN("VoiceData/8.wav"),
    KNIGHT("VoiceData/9.wav"),
    TUNNELER("VoiceData/10.wav"),
    LADDER_MEN("VoiceData/11.wav"),
    ENGINEER("VoiceData/12.wav"),
    BLACK_MONK("VoiceData/13.wav"),
    ARCHER_BOW("VoiceData/14.wav"),
    SLAVES("VoiceData/15.wav"),
    SLINGERS("VoiceData/16.wav"),
    ASSASSINS("VoiceData/17.wav"),
    HORSE_ARCHER("VoiceData/18.wav"),
    ARABIAN_SWORSMEN("VoiceData/19.wav"),
    FIRE_THROWERS("VoiceData/20.wav");

    private final File file;
    Voice (String file) {
        this.file = new File(file);
    }

    public void playVoice(Voice voice) {

        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(voice.getFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audioStream);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        clip.start();
        //String response = Main.getScanner().next();
    }

    public File getFile() {
        return file;
    }



}
