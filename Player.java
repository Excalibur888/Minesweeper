import java.util.ArrayList;

public class Player {
    private String name;
    private int level;
    private ArrayList<Game> games;

    public Player(String name){

    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void computeLevel(int level) {
        this.level = level;
    }

    public Game loadGame(int id){

    }

    public void saveGame(Game game){

    }
}
