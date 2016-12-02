package jeopardy;

import jeopardy.game.Round;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static final String CAPTURE_DEVICE = "Line 1 (Virtual Audio Cable)";

    private static final String CAPTURE_DESCRIPTION = "Direct Audio Device: DirectSound Playback";

    private static Mixer.Info mixer;

    private static Clip clip;
    private static long position = 0;
    private static Round currentRound;

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
            clip = Config.USE_VIRTUAL_AUDIO ? AudioSystem.getClip(mixer) : AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public static Sound playMusic(Round round, Sound current) {
        if (round.equals(currentRound) && clip != null) {
            play();
            return current;
        }
        position = 0;
        currentRound = round;
        return playMusic(round, round.getVolume());
    }

    private static Sound playMusic(Round round, float db) {
        return playSound("music/" + round.musicPath(), db);
    }

    public static void playSound(String sound) {
        playSound(sound, 0f);
    }

    public static Sound playSound(String sound, float db) {
        File file = new File("src/main/resources/" + sound + ".wav");
        if (!file.exists()) {
            System.err.println("No sound found at " + sound);
            return new Sound();
        }

        Sound s = new Sound(file);
        if (db != 0) {
            FloatControl gainControl = (FloatControl) s.clip.getControl(FloatControl.Type.MASTER_GAIN);
            db = Math.min(db, gainControl.getMaximum());
            db = Math.max(db, gainControl.getMinimum());
            gainControl.setValue(db);
        }
        s.play();
        return s;
    }

    public static void play() {
        if (clip == null) return;

        clip.setMicrosecondPosition(position);
        clip.start();
    }

    public static void stop(){
        if (clip == null) return;

        position = clip.getMicrosecondPosition();
        clip.stop();
    }
}