import java.util.ArrayList;
import java.io.File;

public class Players {
    private ArrayList<Player> players;
    private int numberOfPlayers;

    public Players(File file){

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
