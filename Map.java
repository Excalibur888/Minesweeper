public class Map {
    private final int height;

    private final int width;

    private final Box[][] boxes;

    /**
     * Create a map with the entered size and mine count.
     *
     * @param height    height of the map
     * @param width     width of the map
     * @param mineCount number of mines in the map
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
     * Mark if the box is not revealed, unmark if it is already marked.
     *
     * @param x position x of the box to mark/unmark.
     * @param y position y of the box to mark/unmark.
     */
    public void mark(final int x, final int y) {
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position");
            return;
        }
        if (!this.boxes[x][y].isRevealed()) {
            System.out.println("Box already revealed");
        } else {
            this.boxes[x][y].setMarked(!this.boxes[x][y].isMarked());
        }
    }

    /**
     * Reveal the box in entered position.
     * If the box is empty, reveal adjacent boxes.
     * If the box is a mine, reveal all boxes and end the game.
     *
     * @param x position x of the box to reveal
     * @param y position y of the box to reveal
     */
    public void reveal(final int x, final int y, Game game) {
        // Invalid position if the box is outside the map
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Error : Invalid position.");
        }
        else {
            //reveal the box
            this.boxes[x][y].reveal();
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

            // Check if the game is won

        }
    }

    /**
     * Print the map in the console.
     * X : box not revealed
     * - : box revealed and empty
     * F : box marked
     * * : box revealed and mine
     * 1-8 : box revealed and indication
     */
    public void print() {
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boxes[i][j].isRevealed()) {
                    if (this.boxes[i][j].getType() == BoxType.INDICATION) {
                        printNumbers((this.boxes[i][j]).getMineCount());
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
     * Print the number with emotes in the console.
     * @param i number to print
     */
    public void printNumbers(int i) {
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
        }
    }
}
