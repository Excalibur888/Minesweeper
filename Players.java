import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Players {
    private String path;
    private ArrayList<Player> players;

    public Players(String path) {
        this.path = path;
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                System.out.println("New player file created.");
                this.players = new ArrayList<>();
            } else {
                System.out.println("Player file already exists.");
                this.players = readFromJsonFile(path);
            }
        } catch (IOException e) {
            System.out.println("Error opening the file.");
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        for (Player p : this.players){
            if (p.getName().equals(player.getName())){
                System.out.println("Error this name is already taken.");
                return;
            }
        }
        this.players.add(player);
        saveToJsonFile(this.players, this.path);
        System.out.println("Player added.");
    }

    public void removePlayer(String name) {
        for (Player p : this.players){
            if (p.getName().equals(name)){
                this.players.remove(p);
                saveToJsonFile(this.players, this.path);
                System.out.println("Player removed.");
                return;
            }
        }
        System.out.println("Error this player does not exist.");
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public int getNumberOfPlayers() {
        return this.players.size();
    }


    private static void saveToJsonFile(ArrayList<Player> players, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(players, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Player> readFromJsonFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.length() > 0) {
            ArrayList<Player> players = new ArrayList<>();
            try (Reader reader = new FileReader(fileName)) {
                Type type = new TypeToken<ArrayList<Player>>() {
                }.getType();
                Gson gson = new Gson();
                players = gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return players;
        } else return new ArrayList<Player>();
    }
}
