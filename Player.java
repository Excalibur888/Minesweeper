import java.util.ArrayList;

public class Player {
    private String name;
    private int level;
    private ArrayList<Game> games;

    public Player(String name){
        this.name = name;
        this.level = 0;
        this.games = new ArrayList<Game>();
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

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public void computeLevel(int level) {
        this.level = level;
    }

    public Game createGame(){
        return null;
    }
    public Game loadGame(int id){
        return null;
    }
    public void saveGame(Game game){

    }
}
