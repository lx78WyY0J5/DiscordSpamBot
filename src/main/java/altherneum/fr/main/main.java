package altherneum.fr.main;

import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class main {
    public static DiscordApi api = null;

    public static void SetDiscordApi() throws ExecutionException, InterruptedException {
        api = new DiscordApiBuilder().setToken(token.token).setAccountType(AccountType.CLIENT).login().join();
    }

    public static void main(String[] args)
            throws LoginException, ExecutionException, InterruptedException, IOException {
        // start.spamScheduled();
        SetDiscordApi();
        sendGif("https://media1.tenor.com/m/Rb5WeEOwEJUAAAAd/token-grabber-thanos.gif", "1332057126155063336");
        // spamUser("0000000000000000", 100, "?");
        // ListServerTextChannel("0000000000000000", true);
        // spamChannel("0000000000000000", 100, "null");
        // spamAllMessageEmoji("0000000000000000", "📧", 12345, "0000000000000000");
        // spamAllMessageEmojiForUser("0000000000000000", "📧", 12345, "0000000000000000", "0000000000000000");
        // cleanServerMessage("0000000000000000");

        spamGroupMessage("1326436358176116766", 25, "oups, pas fait exprès :shush:");
    }

    public static void getAllEmojiID(String serverID) {
        Server server = api.getServerById(serverID).get();
        for (CustomEmoji customEmoji : server.getCustomEmojis()) {
            System.out.println("<:" + customEmoji.getName() + ":" + customEmoji.getId() + ">");
        }
    }

    public static void sendGif(gifURL, channelID){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(gifURL);
        builder.send(main.api.getTextChannelById(channelID).get());
    }

    public static void spamUser(String ID, int count, String message) throws InterruptedException, ExecutionException {
        Thread t0 = new Thread(() -> {
            try {
                int i = 0;
                while (i < count) {
                    main.api.getUserById(ID).get().sendMessage(message).get();
                    i++;
                    Thread.sleep(150);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t0.start();
    }

    public static void spamGroupMessage(String channelID, int count, String text) {
        Thread t0 = new Thread(() -> {
            try {
                int i = 0;
                while (i < count) {
                    int randomInt = (int) (Math.random() * 100);
                    main.api.getTextChannelById(channelID).get().sendMessage(text + " " + randomInt).get();
                    i++;
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t0.start();
    }

    public static void spamChannel(String channelID, int count, String text) {
        Thread t0 = new Thread(() -> {
            try {
                int i = 0;
                while (i < count) {
                    int randomInt = (int) (Math.random() * 100);
                    main.api.getServerTextChannelById(channelID).get().sendMessage(text + " " + randomInt).get();
                    i++;
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t0.start();
    }
    
    public static void spamAllMessageEmoji(String serverID, String emoji, int count, String channelID)
            throws InterruptedException, ExecutionException {
        Server server = main.api.getServerById(serverID).get();
        // for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
        ServerTextChannel serverTextChannel = server.getTextChannelById(channelID).get();
        if (serverTextChannel.canYouUseExternalEmojis()) {
            for (Message message : serverTextChannel.getMessages(count).get()) {
                System.out.println("hit");
                System.out.println(message.getLink());
                message.addReaction(emoji).get();
            }
        }
        // }
    }
    
    public static void spamAllMessageEmojiForUser(String serverID, String emoji, int count, String channelID, String userID)
            throws InterruptedException, ExecutionException {
        Server server = main.api.getServerById(serverID).get();
        User user = api.getUserById(userID).get();
        // for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
        ServerTextChannel serverTextChannel = server.getTextChannelById(channelID).get();
        if (serverTextChannel.canYouUseExternalEmojis()) {
            for (Message message : serverTextChannel.getMessages(count).get()) {
                if (message.getAuthor().asUser().get().equals(user)) {
                    System.out.println("hit");
                    System.out.println(message.getLink());
                    message.addReaction(emoji).get();
                }
            }
        }
        // }
    }

    public static void cleanServerMessage(String ServerID) throws InterruptedException, ExecutionException
    {
        Server server = main.api.getServerById(ServerID).get();
        for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
            System.out.println(ServerID + " : " + serverTextChannel.getName());
            if (serverTextChannel.canWrite(main.api.getYourself())) {
                for (Message message : serverTextChannel.getMessages(0).get()) {
                    System.out.println("Message check");
                    if (message.getAuthor().getIdAsString().equals(main.api.getYourself().getIdAsString())) {
                        message.delete();
                        System.out.println("Message delete");
                    }
                }
            }
        }
    }
    
    public static void ListServerTextChannel(String ServerID, Boolean ShowHidden) {
        Server server = main.api.getServerById(ServerID).get();
        for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
            if (!serverTextChannel.canSee(main.api.getYourself()) && ShowHidden) {
                System.out.println(serverTextChannel.getName());
            } else if (serverTextChannel.canSee(main.api.getYourself())) {
                System.out.println(serverTextChannel.getName());
            }
        }
    }
}