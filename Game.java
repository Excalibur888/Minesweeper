import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Game {
    private String name;
    private int score;
    private int minesCount;
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

        switch (this.difficulty) {
            case Game_Difficulty.EASY:
                this.map = new Map(10, 10);
                this.minesCount = 10;
                break;
            case Game_Difficulty.NORMAL:
                this.map = new Map(20, 20);
                this.minesCount = 40;
                break;
            case Game_Difficulty.HARD:
                this.map = new Map(30, 30);
                this.minesCount = 99;
                break;
            case Game_Difficulty.PERSONALISED:
                System.out.println("Enter the map size :\n");
                Scanner input = new Scanner(System.in);
                int size = input.nextInt();
                this.map = new Map(size, size);
                System.out.println("Home much mines do you want ?\n");
                this.minesCount = input.nextInt();
                break;
        }
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

    public void setStatus(Game_Status status) {
        this.status = status;
    }

    public void addFlag(int x, int y){

    }

    public void computeScore(){

    }
}
