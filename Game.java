public class Game {
    private int id;
    private int score;
    private Map map;
    private Date date;
    private Timer timer;
    private Game_Difficulty difficulty;
    private Game_Status status;

    public Game(Player player){

    }

    public Game(Player player, Map map) {

    }

    public Game(Player player, Map map, Game_Difficulty difficulty){

    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public Game_Difficulty getDifficulty() {
        return difficulty;
    }

    public Game_Status getStatus() {
        return status;
    }

    public void setDifficulty(Game_Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setStatus(Game_Status status) {
        this.status = status;
    }

    public void addFlag(int x, int y){

    }

    public void computeScore(){

    }
}
