package jeopardy;

import jeopardy.game.Player;

import java.io.*;
import java.util.*;

/**
 * Created by XLIII on 2015-12-13.
 */
public class Saves {

    private static Map<List<String>, String> chatIds;

    public static final String chatsFile = "src/main/resources/chatIds.dat";
    public static final String scoresTemplate = "src/main/resources/scores/player";
    public static final String namesTemplate = "src/main/resources/names/player";

    private static String getScoresFile(Player player) {
        return scoresTemplate + player.getId() + ".txt";
    }

    private static String getNamesFile(Player player) {
        return namesTemplate + player.getId() + ".txt";
    }

    private static void saveText(String path, Object value) {
        try (
            FileWriter file = new FileWriter(path, false);
        ) {
            file.write(String.valueOf(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveName(Player player) {
        saveText(getNamesFile(player), player.getName());
    }

    public static void saveScore(Player player) {
        saveText(getScoresFile(player), player.getScore());
    }

    static {
        //System.out.println("Initializing saves");
        try (
            InputStream file = new FileInputStream(chatsFile);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInputStream objStream = new ObjectInputStream(buffer);
        ) {
            chatIds = (Map<List<String>,String>) objStream.readObject();
            //System.out.println("Saves size: " + chatIds.size());
        } catch (IOException e) {
            //System.out.println("No saves found: initializing new");
            chatIds = new HashMap<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String retrieveChatId(Collection<String> users) {

        return chatIds.get(new ArrayList<>(users));
    }

    public static void saveChatId(Collection<String> users, String chatId) {
        if (chatIds.containsKey(new ArrayList<>(users))) {
            return;
        }

        chatIds.put(new ArrayList<>(users), chatId);

        try (
            OutputStream file = new FileOutputStream(chatsFile, false);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream objStream = new ObjectOutputStream(buffer);
        ) {
            objStream.writeObject(chatIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
