package jeopardy;

import jeopardy.game.Config;
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
            clip = Config.USE_VIRTUAL_AUDIO ? AudioSystem.getClip(mixer) : AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    public static Sound playMusic(Round round) {
        return playMusic(round, round.getVolume());
    }

    public static Sound playMusic(Round round, float db) {
        return playSound("music/" + round.toMusicPath(), db);
    }

    public static void playSound(String sound) {
        playSound(sound, 0f);
    }

    public static Sound playSound(String sound, float db) {
        File file = new File("src/main/resources/" + sound + ".wav");
        if (!file.exists()) {
            System.out.println("No sound found at " + sound);
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

    public void play() {
        if (clip == null) return;

        clip.start();
    }

    public void stop(){
        if (clip == null) return;

        clip.stop();
    }
}