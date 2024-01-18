import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Players {
    DataSavingUtils save;

    private ArrayList<Player> players;

    public Players(String path) {
        save = new DataSavingUtils(path);
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                System.out.println("New player file created.");
                this.players = new ArrayList<>();
            } else {
                System.out.println("Player file already exists.");
                this.players = save.readFromJsonFile();
            }
        } catch (IOException e) {
            System.out.println("Error opening the file.");
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        if (!player.getName().isEmpty()) {
            for (Player p : this.players) {
                if (p.getName().equals(player.getName())) {
                    System.out.println("Error this name is already taken.");
                    return;
                }
            }
            this.players.add(player);
            System.out.println("Player added.");
            return;
        }
        System.out.println("Error empty name.");
    }

    public void removePlayer(String name) {
        if (!name.isEmpty()) {
            for (Player p : this.players) {
                if (p.getName().equals(name)) {
                    this.players.remove(p);
                    System.out.println("Player removed.");
                    return;
                }
            }
            System.out.println("Error this player does not exist.");
            return;
        }
        System.out.println("Error empty name.");
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void saveGame() {
        save.saveToJsonFile(players);
    }
}
