import java.time.LocalDate;
import java.util.Date;

public class Game {
    private String name;
    private int score;
    private Map map;
    private LocalDate date;
    private Timer timer;
    private Game_Difficulty difficulty;
    private Game_Status status;

    public Game(String name, Game_Difficulty difficulty) {
        this.name = name;
        this.score = 0;
        this.difficulty = difficulty;
        this.date = LocalDate.now();
        this.status = Game_Status.WAITING;
    }

    public Game(Map map) {

    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Game_Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Game_Status getStatus() {
        return this.status;
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
