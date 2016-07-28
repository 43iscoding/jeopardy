package jeopardy;

import com.skype.*;
import jeopardy.game.Config;
import jeopardy.game.Game;

import java.util.Map;

/**
 * Created by XLIII on 12/10/2015.
 */
public class Bot {

    private Chat chat;

    public Bot(Map<String, String> users, final Game game) {
        String chatId = Saves.retrieveChatId(users.keySet());
        if (Config.FORCE_NEW_CHAT || chatId == null) {
            chat = createGroupChatWith(users.keySet().toArray(new String[users.size()]));
            System.out.println("Created new chat");
            Saves.saveChatId(users.keySet(), chat.getId());
        } else {
            //chat = Skype.groupChat(chatId);
            System.out.println("Reused existing chat");

        }

        if (chat == null) {
            System.out.println("Chat not created. Abort");
            return;
        }

        try {
            if (users.size() > 1) {
                chat.setTopic("Jeopardy Game");
            }

            registerUsers(game, users);
            registerListener(game);
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Object message) {
        try {
            if (chat == null) {
                System.out.println("Cannot send message: chat is null");
                return;
            }
            chat.send(message.toString());
            if (Config.LOG_BOT_MESSAGES) {
                System.out.println("Bot: " + message);
            }
        } catch (SkypeException e) {
            System.out.println("Failed to send message - " + e.getMessage());
        }
    }

    private void registerUsers(final Game game, Map<String, String> users) throws SkypeException {
        for (User user : chat.getAllMembers()) {
            if (!users.containsKey(user.getId())) {
                continue;
            }
            String newName = users.get(user.getId());

            if (!user.getDisplayName().equals(newName)) {
                user.setDisplayName(newName);
            }

            game.registerPlayer(user.getDisplayName());
        }
    }

    private void registerListener(final Game game) throws SkypeException {
        Skype.addChatMessageListener(new ChatMessageListener() {
            public void chatMessageReceived(ChatMessage message) throws SkypeException {
                if (!message.getChat().equals(chat)) {
                    //skip other chats
                    return;
                }

                if (message.getType() != ChatMessage.Type.SAID) {
                    //we only want text messages to process
                    return;
                }

                game.receiveMessage(message.getSender().getDisplayName(), message.getContent());
            }

            public void chatMessageSent(ChatMessage message) throws SkypeException {}
        });
    }

    private Chat createGroupChatWith(String[] users) {
        try {
            chat = Skype.chat(users);
            return chat;
        } catch (SkypeException e) {
            System.out.println("Could not create new group chat: " + e.getMessage());
            return null;
        }
    }

}
