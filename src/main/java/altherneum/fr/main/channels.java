package altherneum.fr.main;

import java.util.ArrayList;

public class channels {
    public static ArrayList<String> channelsGlobal() {
        ArrayList<String> channels = new ArrayList<>();

        channels.addAll(server1());
        channels.addAll(server2());

        return channels;
    }

    public static ArrayList<String> server1() {
        ArrayList<String> channels = new ArrayList<>();
        channels.add("1234567891234567890");
        channels.add("1234567891234567890");
        channels.add("1234567891234567890");
        return channels;
    }

    public static ArrayList<String> server2() {
        ArrayList<String> channels = new ArrayList<>();
        channels.add("1234567891234567890");
        channels.add("1234567891234567890");
        channels.add("1234567891234567890");
        return channels;
    }
}