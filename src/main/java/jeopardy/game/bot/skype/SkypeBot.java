package jeopardy.game.bot.skype;

import com.skype.*;
import jeopardy.Saves;
import jeopardy.game.Config;
import jeopardy.game.Game;
import jeopardy.game.bot.AbstractBot;

import java.util.Map;

/**
 * Created by XLIII on 12/10/2015.
 */
public class SkypeBot extends AbstractBot {

    private Chat chat;

    public SkypeBot(Map<String, String> users) {
        super(users);
        String chatId = Saves.retrieveChatId(users);
        if (Config.FORCE_NEW_CHAT || chatId == null) {
            chat = createGroupChatWith(users.keySet().toArray(new String[users.size()]));
            System.out.println("Created new chat");
            Saves.saveChatId(users, chat.getId());
        } else {
            chat = Skype.groupChat(chatId);
            System.out.println("Reused existing chat");

        }

        if (chat == null) {
            System.out.println("Chat not created. Abort");
            return;
        }

        try {
            if (users.size() > 1 && Config.BEAUTIFY) {
                chat.setTopic("Jeopardy Game");
            }
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void sendMessage(String message) {
        try {
            if (chat == null) {
                System.out.println("Cannot send message: chat is null");
                return;
            }
            chat.send(message);

        } catch (SkypeException e) {
            System.out.println("Failed to send message - " + e.getMessage());
        }
    }

    @Override
    protected boolean userExists(String userId) {
        return getUser(userId) != null;
    }

    private User getUser(String userId) {
        try {
            for (User user : chat.getAllMembers()) {
                if (user.getId().equals(userId)) return user;
            }
            return null;
        } catch (SkypeException e) {
            System.out.println("Error when getting user: " + userId + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    protected String updateName(String userId, String displayName) {
        User user = getUser(userId);
        if (user == null) {
            System.out.println("User not found: " + userId);
            return "";
        }
        try {
            user.setDisplayName(displayName);
        } catch (SkypeException e) {
            System.out.println("Error while setting name for user: " + userId + " - " + e.getMessage());
            return "";
        }
        return displayName;
    }

    public void registerListener(final Game game) {
        try {
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
        } catch (SkypeException e) {
            System.out.println("Error while registering chat message listener: " + e.getMessage());
        }
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
