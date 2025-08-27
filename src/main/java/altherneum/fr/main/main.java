package altherneum.fr.main;

import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import javax.security.auth.login.LoginException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class main {
    public static DiscordApi api = null;

    public static void SetDiscordApi() throws ExecutionException, InterruptedException {
        api = new DiscordApiBuilder().setToken(token.token).setAccountType(AccountType.CLIENT).login().join();
    }

    public static void main(String[] args) throws LoginException, ExecutionException, InterruptedException, IOException {
        // start.spamScheduled();
        SetDiscordApi();
        //spamUser("0000000000000000000", 10, "ABC", false);
        //spamUser("0000000000000000000", 25, GifPicker(), true);
        // ListServerTextChannel("0000000000000000", true);
        //spamChannel("0000000000000000", 100, "ABC");
        // spamAllMessageEmoji("0000000000000000", "📧", 12345, "0000000000000000");
        // spamAllMessageEmojiForUser("0000000000000000", "📧", 12345, "0000000000000000", "0000000000000000");
        // cleanServerMessage("0000000000000000");
        // spamGroupMessage("1326436358176116766", 25, "oups, pas fait exprès :shush:");
    }

    public static ArrayList<String> list = new ArrayList<>(Arrays.asList("spammer-no-spamming-dora-gif-19107257", "junk-mail-spam-mail-messages-the-bagheads-gif-11565788", "spam-spam-not-funny-gif-22146813", "spam-spam-button-pressing-button-insert-controller-here-smash-gif-17122318", "checking-inbox-mail-steve-harvey-inbox-email-gif-17917048", "pepegachat-pepega-chat-chatting-gif-19762143", "discord-mod-use-bots-in-bot-command-spam-bot-command-spam-discord-mod-bots-gif-22177336", "domain-expansion-will-spam-domain-expansion-wills-spam-gif-7021287657582182369", "discord-ping-discord-ping-pings-mass-ping-gif-2196332929166408226", "ping-gif-20035980", "lit-silly-tasty-hello-banana-phone-gif-5555120", "arch-linux-user-femboy-arch-linux-gif-7369320239770547824", "linux-chad-arch-arch-linux-chad-user-gif-21904978", "windows11-windows-leak-windows-microsoft-windows11-windows11meme-gif-22092213", "windows-users-windows10-windows-update-windows-linux-gif-25806046", "windows-users-gif-26552946"));
     
    public static String GifPicker(){
        int ListSize = list.size();

        Random random = new Random();
        int valueToPick = random.nextInt(ListSize);
        return "https://tenor.com/view/" + list.get(valueToPick);
    } 

    public static void getAllEmojiID(String serverID) {
        Server server = api.getServerById(serverID).get();
        for (CustomEmoji customEmoji : server.getCustomEmojis()) {
            System.out.println("<:" + customEmoji.getName() + ":" + customEmoji.getId() + ">");
        }
    }

 public static void spamUser(String ID, int count, String message, boolean GifSpamer) throws InterruptedException, ExecutionException {
        Thread t0 = new Thread(() -> {
            try {
                int i = 0;
                while (i < count) {
                    String messageFinal = message;
                    if(GifSpamer){
                        messageFinal = GifPicker();
                    }
                    main.api.getUserById(ID).get().sendMessage(messageFinal).get();
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
                        message.delete().get();
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