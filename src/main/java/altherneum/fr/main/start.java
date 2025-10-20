package altherneum.fr.main;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static altherneum.fr.main.main.SetDiscordApi;

public class start {
    public static String YourUserID = "123456789123456789";
    public static String CustomMessage = "Super message that will be spammed :3 \nSecond line with 😎\nAnother line";
    
    public static int totalMessage = 0;
    public static int totalMaxMessage;
    public static int totalMaxMessageDivider = 4;
    public static boolean isMaxMessageSet = false;
    public static int totalErrorAlreadySpam = 0;
    public static int totalErrorMissingChan = 0;
    public static int MnsPeriod = 120;
    public static int limitNum = 2;// oldMsgCheck
    public static String message = CustomMessage;
    public static String channelID = "0000000000000000000";

    public static int timeMilisSpam = 1000; // ms
    public static int rngMinTime = 1; // s
    public static int rngMaxTime = 3; // s

    public static void spamScheduled() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    SetDiscordApi();
                    CheckAndSpam(message, channels.channelsGlobal()); 
                    main.api.getServerTextChannelById(channelID).get().sendMessage(CustomMessage).get();
                    System.out.println("Finish");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, (60 * 1000) * MnsPeriod);
    }

    public static void CheckAndSpam(String message, ArrayList<String> channels)
            throws ExecutionException, InterruptedException {

        ArrayList<String> missingChansList = new ArrayList<>();
        totalMaxMessage = channels.size() / totalMaxMessageDivider;
        System.out.print(totalMaxMessage);

        Thread t0 = new Thread(() -> {
            try {
                System.out.println("Start");
                Collections.shuffle(channels);

                for (String string : channels) {

                    if ((totalMessage + totalErrorAlreadySpam + totalErrorMissingChan) >= totalMaxMessage
                            && isMaxMessageSet) {
                        System.out.println("Stop max MSG reached");
                        break;
                    }

                    Thread t2 = new Thread(() -> {
                        try {

                            String name = "";
                            if (main.api.getServerTextChannelById(string).isPresent()) {
                                ServerTextChannel serverTextChannel = main.api.getServerTextChannelById(string).get();
                                name = serverTextChannel.getName();

                                System.out
                                        .println("\n" + (totalErrorAlreadySpam + totalErrorMissingChan + totalMessage)
                                                + "/" + channels.size() + "   : " + name + "   : ");

                                if (!serverTextChannel.canYouSee() || !serverTextChannel.canYouWrite()
                                        || !serverTextChannel.canYouReadMessageHistory()) {
                                    totalErrorMissingChan++;
                                    System.out.print("Cant see/write/HistoryViewMsg");
                                    missingChansList.add(string);
                                } else {
                                    if (!checkLatestMessage(serverTextChannel)) {
                                        serverTextChannel.sendMessage(message).get();
                                        System.out.print("Sended message !");

                                        // Check 5s latter if message successfully sended
                                        // else add to error list
                                        // Display list at end with missing list ;)
                                        totalMessage++;
                                    } else {
                                        totalErrorAlreadySpam++;
                                        System.out.print("Already spamed chan");
                                    }
                                }
                            } else {
                                totalErrorMissingChan++;
                                System.out.print("Missing chan");
                                missingChansList.add(string);
                            }

                            if ((totalErrorAlreadySpam + totalErrorMissingChan + totalMessage) % 10 == 0) {
                                System.out.println("\nTotal : " + totalMessage + "\nAlready : " + totalErrorAlreadySpam
                                        + "\nMissings : " + totalErrorMissingChan);
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t2.start();
                    Random random = new Random();
                    int nextTry = random.nextInt(rngMinTime, rngMaxTime);
                    long timeToWait = (nextTry * 1000) + timeMilisSpam;
                    System.out.println("\nPause de " + (timeToWait / 1000L) + " s");
                    Thread.sleep(timeToWait);
                }

                System.out.println("Missing chans");
                for (String string : missingChansList) {
                    System.out.println(string);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t0.start();
    }

    public static boolean checkLatestMessage(ServerTextChannel serverTextChannel)
            throws ExecutionException, InterruptedException {
        MessageSet messages = serverTextChannel.getMessages(limitNum).get();
        for (Message message : messages) {
            if (message.getAuthor().getIdAsString().equals(YourUserID)) {
                return true;
            }
        }
        return false;
    }
}