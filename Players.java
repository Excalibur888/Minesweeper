import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    private void saveToJsonFile(ArrayList<Player> players, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();
            gson.toJson(players, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Player> readFromJsonFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.length() > 0) {
            ArrayList<Player> players = new ArrayList<>();
            try (Reader reader = new FileReader(fileName)) {
                Type type = new TypeToken<ArrayList<Player>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();                players = gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return players;
        } else return new ArrayList<Player>();
    }

    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(DATE_FORMATTER.format(date));
        }

        @Override
        public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(element.getAsString(), DATE_FORMATTER);
        }
    }
}
