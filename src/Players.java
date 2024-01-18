import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Players class manages a collection of Player objects and provides methods for adding, removing, and retrieving players.
 * It also uses DataSavingUtils for saving player data to a file.
 */
public class Players {
    DataSavingUtils save;
    private ArrayList<Player> players;

    /**
     * Constructs a Players object with the specified file path.
     * If the file doesn't exist, a new player file is created.
     * If the file exists, player data is loaded from the file.
     *
     * @param path The file path for storing player data.
     */
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

    /**
     * Adds a new player to the list of players.
     *
     * @param player The Player object to be added.
     */
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

    /**
     * Removes a player with the specified name from the list of players.
     *
     * @param name The name of the player to be removed.
     */
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

    /**
     * Retrieves the list of players.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Saves the current state of player data to the file.
     */
    public void saveGame() {
        save.saveToJsonFile(players);
    }
}
