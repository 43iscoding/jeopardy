package jeopardy;

import jeopardy.game.Round;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static final String CAPTURE_DEVICE = "Line 1 (Virtual Audio Cable)";

    private static final String CAPTURE_DESCRIPTION = "Direct Audio Device: DirectSound Playback";

    private static Mixer.Info mixer;

    private Clip clip;

    public Sound() {
    }

    static {
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            if (!info.getDescription().equals(CAPTURE_DESCRIPTION)) continue;

            if (info.getName().equals(CAPTURE_DEVICE)) {
                mixer = info;
            }
        }
    }

    private Sound(File file) {
        try {
            clip = AudioSystem.getClip(mixer);
            //clip = AudioSystem.getClip();
            //AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public static Sound playMusic(Round round) {
        File file = new File("src/main/resources/music/" + round.toMusicPath() + ".wav");
        if (!file.exists()) {
            System.out.println("No music found for " + round);
            return new Sound();
        }

        return new Sound(file);
    }

    public static void playSound(String sound) {
        playSound(sound, 0f);
    }

    public static void playSound(String sound, float db) {
        File file = new File("src/main/resources/sounds/" + sound + ".wav");
        if (!file.exists()) {
            System.out.println("No sound found at " + sound);
            return;
        }

        Sound s = new Sound(file);
        if (db != 0) {
            FloatControl gainControl = (FloatControl) s.clip.getControl(FloatControl.Type.MASTER_GAIN);
            db = Math.min(db, gainControl.getMaximum());
            db = Math.max(db, gainControl.getMinimum());
            gainControl.setValue(db);
        }
        s.play();
    }

    public void play() {
        if (clip == null) return;

        clip.start();
    }

    public void stop(){
        if (clip == null) return;

        clip.stop();
    }
}