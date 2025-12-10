package altherneum.fr.main;

import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class main {
    public static DiscordApi api = null;

    public static void SetDiscordApi() throws ExecutionException, InterruptedException {
        api = new DiscordApiBuilder().setToken(token.token).setAccountType(AccountType.CLIENT).login().join();
    }

    public static void main(String[] args) throws LoginException, ExecutionException, InterruptedException, IOException {
        // Merci de configurer les class avant de lancer

        boolean pubMethode = false;
        if(pubMethode == true){
            start.spamScheduled();
        }
        else{
            CustomSpamFunctions();
        }
    }

    public static void CustomSpamFunctions() throws LoginException, ExecutionException, InterruptedException, IOException {
        SetDiscordApi();

        //renameAllChannels("0000000000000000000", "﹒", "▪️");

        // spamUser("0000000000000000000", 10, "ABC", false);
        
        // spamUser("0000000000000000000", 50, GifPicker(true, null), true); // Random GIF from array list

        // spamUser("000000000000000000", 1, GifPicker(false, getRandomEmoji("ABC")), false); //GIF from Query with API

        // spamUser("000000000000000000", 100, URL, true);

        // spamUser("000000000000000000", 100, "null", true);

        // dumpVideoFromChannel("0000000000000000000");

        // ListServerTextChannel("0000000000000000", true);

        // spamChannel("0000000000000000", 100, "ABC");

        // spamAllMessageEmoji("0000000000000000", "📧", 12345, "0000000000000000");

        // spamAllMessageEmojiForUser("0000000000000000", "📧", 12345, "0000000000000000", "0000000000000000");

        // cleanServerMessage("0000000000000000");

        // CleanChannelMessage("0000000000000000");

        // CleanUserMessage("0000000000000000");

        // spamGroupMessage("0000000000000000", 25, "oups, pas fait exprès :shush:");

        // spamAllChannelInServer("0000000000000000000");
    }

    public static void renameAllChannels(String serverID, String charToReplace, String newChar) throws InterruptedException, ExecutionException{
        Server server = main.api.getServerById(serverID).get();
        List<ServerChannel> serverChannelList = server.getChannels();

        for(ServerChannel serverChannel : serverChannelList){
            System.out.println(serverChannel.getName());
            String ChannelName = serverChannel.getName();
            if(ChannelName.contains(charToReplace)){
                System.out.print(" : Starting to replace the name !");
                String newName = ChannelName.replace(charToReplace, newChar);
                serverChannel.updateName(newName).get();
                System.out.println("New name : " + serverChannel.getName());
            }
        }
    }

    public static ArrayList<String> list = new ArrayList<>(Arrays.asList("kadava-gif-16228050557131628755", "spammer-no-spamming-dora-gif-19107257", "junk-mail-spam-mail-messages-the-bagheads-gif-11565788", "spam-spam-not-funny-gif-22146813", "spam-spam-button-pressing-button-insert-controller-here-smash-gif-17122318", "checking-inbox-mail-steve-harvey-inbox-email-gif-17917048", "pepegachat-pepega-chat-chatting-gif-19762143", "discord-mod-use-bots-in-bot-command-spam-bot-command-spam-discord-mod-bots-gif-22177336", "domain-expansion-will-spam-domain-expansion-wills-spam-gif-7021287657582182369", "discord-ping-discord-ping-pings-mass-ping-gif-2196332929166408226", "ping-gif-20035980", "lit-silly-tasty-hello-banana-phone-gif-5555120", "arch-linux-user-femboy-arch-linux-gif-7369320239770547824", "linux-chad-arch-arch-linux-chad-user-gif-21904978", "windows11-windows-leak-windows-microsoft-windows11-windows11meme-gif-22092213", "windows-users-windows10-windows-update-windows-linux-gif-25806046", "windows-users-gif-26552946"));
     
    public static String GifPicker(boolean fixURL, String URL){

        System.out.println(fixURL + " ; " + URL);

        if(fixURL == true){
            int ListSize = list.size();
            Random random = new Random();
            int valueToPick = random.nextInt(ListSize);
            return "https://tenor.com/view/" + list.get(valueToPick);
        }
        else if(fixURL == false) {
            return "https://tenor.com/view/" + URL;
        }

        return "";
    }

    public static String getRandomEmoji(String QueryURL) throws MalformedURLException, IOException, InterruptedException{
        final String API_KEY = "LIVDSRZULELA";
        
        final String searchTerm = QueryURL;

        final String urlPath = String.format("https://g.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s", searchTerm, API_KEY, 1);
        
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlPath))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        //System.out.println(response.body().toString());

        Pattern p = Pattern.compile("\"itemurl\": \"https://tenor.com/view/(.*)\"");
        Matcher m = p.matcher(response.body().toString());
        if(m.find()){
            return m.group(1);
        }
        return "";
    }

    public static void dumpVideoFromChannel(String channelID) throws InterruptedException, ExecutionException{
        ServerTextChannel serverTextChannel = main.api.getServerTextChannelById(channelID).get();
        Pattern p = Pattern.compile(".*youtu(.*).*");
        for(Message message : serverTextChannel.getMessages(9999).get()){
            String messageText = message.getEmbeds().getFirst().getDescription().get().toString();
            if(messageText.contains("https://www.youtube.com/") || messageText.contains("https://youtu.be/") || messageText.contains("https://youtube.com/shorts/")){
                Matcher m = p.matcher(messageText);
                if(m.find()){
                    System.out.println(m.group(1));
                    //message.delete().get();
                }
            }
        }
    }

    public static void getAllEmojiID(String serverID) {
        Server server = api.getServerById(serverID).get();
        for (CustomEmoji customEmoji : server.getCustomEmojis()) {
            System.out.println("<:" + customEmoji.getName() + ":" + customEmoji.getId() + ">");
        }
    }

    public static void spamUser(String ID, int count, String message, boolean RandomGifSpamer) throws InterruptedException, ExecutionException {
        Thread t0 = new Thread(() -> {
            try {
                int i = 0;
                while (i < count) {
                    String messageFinal = message;
                    if(RandomGifSpamer){
                        messageFinal = GifPicker(true, "");
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


    public static void spamAllChannelInServer(String ServerID) throws InterruptedException, ExecutionException{
        for(ServerChannel serverChannel : main.api.getServerById(ServerID).get().getChannels()){
            System.out.println(serverChannel.getName());
            if(serverChannel.getType().isTextChannelType()){
            serverChannel.asServerTextChannel().get().sendMessage("t").get();
            } 
        }
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
            CleanChannelMessage(serverTextChannel.getIdAsString());
        }
    }

    public static void CleanChannelMessage(String channelID) throws InterruptedException, ExecutionException{
        ServerTextChannel serverTextChannel = main.api.getServerTextChannelById(channelID).get();
        System.out.println(serverTextChannel.getName());
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

     public static void CleanUserMessage(String channelID) throws InterruptedException, ExecutionException{
        TextChannel textChannel = main.api.getTextChannelById(channelID).get();
        for (Message message : textChannel.getMessages(0).get()) {
            System.out.println("Message check : " + message.getContent());
            if (message.getAuthor().getIdAsString().equals(main.api.getYourself().getIdAsString())) {
                message.delete().get();
                System.out.println("Message delete");
            }
        }
    }
    
    public static void ListServerTextChannel(String ServerID, boolean ShowHidden) {
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