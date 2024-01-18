/**
 * Class representing the game map in a Minesweeper game.
 */
public class Map {
    private final int height;

    private final int width;

    private final Box[][] boxes;

    /**
     * Constructs a map with a specified height, width, and mine count.
     *
     * @param height    The height of the map.
     * @param width     The width of the map.
     * @param mineCount The number of mines on the map.
     */
    public Map(final int height, final int width, final int mineCount) {
        // Initialize map
        this.height = height;
        this.width = width;
        this.boxes = new Box[height][width];
        // Generate empty boxes
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.boxes[i][j] = new Box();
            }
        }
        // Check if mine count is possible for the size of the map
        if (mineCount > height * width) {
            System.out.println("Error : Too many mines");
            return;
        }
        // Generate mines at random position and indications around them
        for (int i = 0; i < mineCount; i++) {
            int x = (int) (Math.random() * height);
            int y = (int) (Math.random() * width);
            if (this.boxes[x][y].getType() == BoxType.MINE) {
                i--;
            } else {
                this.boxes[x][y].setType(BoxType.MINE);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && nx < this.height && ny >= 0 && ny < this.width) {
                            if (this.boxes[nx][ny].getType() == BoxType.INDICATION) {
                                this.boxes[nx][ny].addMine();
                            } else if (this.boxes[nx][ny].getType() == BoxType.EMPTY) {
                                this.boxes[nx][ny].setType(BoxType.INDICATION);
                                this.boxes[nx][ny].addMine();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the height of the map.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the width of the map.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the box at the specified position on the map.
     *
     * @param x The x-coordinate of the box.
     * @param y The y-coordinate of the box.
     * @return The box at the specified position.
     */
    public Box getBoxes(int x, int y) {
        return boxes[x][y];
    }

    /**
     * Marks or unmarks the box at the specified position.
     *
     * @param x The x-coordinate of the box.
     * @param y The y-coordinate of the box.
     */
    public void mark(final int x, final int y) {
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position");
            return;
        }
        if (this.boxes[x][y].isRevealed()) {
            System.out.println("Box already revealed");
        } else {
            this.boxes[x][y].mark(!this.boxes[x][y].isMarked());
        }
    }

    /**
     * Reveals the box at the specified position and updates the game status accordingly.
     *
     * @param x    The x-coordinate of the box.
     * @param y    The y-coordinate of the box.
     * @param game The current game instance.
     */
    public void reveal(final int x, final int y, final Game game) {
        // Invalid position if the box is outside the map
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Error : Invalid position.");
        } else {
            //reveal the box
            this.boxes[x][y].reveal();
            //check if the player win
            checkWin(game);
            // Game over if the box is a mine
            if (this.boxes[x][y].getType() == BoxType.MINE) {
                // Reveal all boxes if the box is a mine
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        this.boxes[i][j].reveal();
                    }
                }
                game.setStatus(GameStatus.LOOSE);
            } else if (this.boxes[x][y].getType() == BoxType.EMPTY) {
                // Reveal adjacent boxes if the box is empty
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && nx < this.height && ny >= 0 && ny < this.width) {
                            if (!this.boxes[nx][ny].isRevealed()) {
                                reveal(nx, ny, game);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints the current state of the map, including revealed, marked, and unrevealed boxes.
     */
    public void print() {
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boxes[i][j].isRevealed()) {
                    if (this.boxes[i][j].getType() == BoxType.INDICATION) {
                        printNumbers((this.boxes[i][j]).getAdjacentMineCount());
                    } else if (this.boxes[i][j].getType() == BoxType.MINE) {
                        System.out.print("\uD83D\uDCA3");
                    } else {
                        System.out.print("\uD83D\uDFEB");
                    }
                } else if (this.boxes[i][j].isMarked()) {
                    System.out.print("\uD83D\uDEA9");
                } else {
                    System.out.print("\uD83D\uDFE9");
                }
            }
            System.out.println();
        }
    }

    /**
     * Prints numbers based on the adjacent mine count for a given box.
     *
     * @param i The adjacent mine count.
     */
    public void printNumbers(final int i) {
        switch (i) {
            case 1:
                System.out.print("1️⃣");
                break;
            case 2:
                System.out.print("2️⃣");
                break;
            case 3:
                System.out.print("3️⃣");
                break;
            case 4:
                System.out.print("4️⃣");
                break;
            case 5:
                System.out.print("5️⃣");
                break;
            case 6:
                System.out.print("6️⃣");
                break;
            case 7:
                System.out.print("7️⃣");
                break;
            case 8:
                System.out.print("8️⃣");
                break;
            default:
                System.out.print("❓");
                break;
        }
    }

    /**
     * Checks if the player has won the game by revealing all non-mine boxes.
     *
     * @param game The current game instance.
     */
    public void checkWin(final Game game) {
        // Win condition : all boxes revealed except mines.
        int count = 0;
        int minesCount = 0;
        //Count the number of boxes on the map.
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boxes[i][j].getType() == BoxType.MINE) {
                    minesCount++;
                }
            }
        }
        //Count the number of boxes not revealed.
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (!this.boxes[i][j].isRevealed()) {
                    count++;
                }
            }
        }
        //If the number of boxes not revealed is equal to the number of mines, the player win.
        if (count == minesCount) {
            game.setStatus(GameStatus.WIN);
        }
    }
}
