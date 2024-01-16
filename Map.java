public class Map {
    final int height;
    final int width;
    final Box[][] boxes;
    /**
     * Create a map with the entered size and mine count.
     * @param height height of the map
     * @param width width of the map
     * @param mineCount number of mines in the map
     */
    public Map(int height, int width, int mineCount) {
        // Initialize map
        this.height = height;
        this.width = width;
        this.boxes = new Box[height][width];
        // Generate empty boxes
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                this.boxes[i][j] = new Box();
            }
        }
        // Check if mine count is possible for the size of the map
        if(mineCount > height * width) {
            System.out.println("Too many mines");
            return;
        }
        // Generate mines at random position and indications around them
        for(int i = 0; i < mineCount; i++) {
            int x = (int) (Math.random() * height);
            int y = (int) (Math.random() * width);
            if(this.boxes[x][y] instanceof Mine) {
                i--;
            } else {
                this.boxes[x][y] = new Mine();
                for(int dx=-1; dx<=1; dx++) {
                    for(int dy=-1; dy<=1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if(nx >= 0 && nx < this.height && ny >= 0 && ny < this.width && !(this.boxes[nx][ny] instanceof Mine)) {
                            if(this.boxes[nx][ny] instanceof Indication){
                                ((Indication) this.boxes[nx][ny]).addMine();
                            } else if(!(this.boxes[nx][ny] instanceof Mine)) {
                                this.boxes[nx][ny] = new Indication();
                                ((Indication) this.boxes[nx][ny]).addMine();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Mark if the box is not revealed, unmark if it is already marked.
     * @param x position x of the box to mark/unmark.
     * @param y position y of the box to mark/unmark.
     */
    public void mark(int x, int y) {
        if(x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position");
            return;
        }
        if(!this.boxes[x][y].isRevealed()){
            System.out.println("Box already revealed");
        } else {
            this.boxes[x][y].setMarked(!this.boxes[x][y].isMarked());
        }
    }

    /**
     * Reveal the box in entered position.
     * If the box is empty, reveal adjacent boxes.
     * If the box is a mine, reveal all boxes and end the game.
     * @param x position x of the box to reveal
     * @param y position y of the box to reveal
     */
    public void reveal(int x, int y) {
        // Invalid position if the box is outside the map
        if(x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position.");
            return;
        }
        //reveal the box
        this.boxes[x][y].reveal();
        // Game over if the box is a mine
        if (this.boxes[x][y] instanceof Mine) {
            // Reveal all boxes if the box is a mine
            for(int i = 0; i < this.height; i++) {
                for(int j = 0; j < this.width; j++) {
                    this.boxes[i][j].reveal();
                }
            }
        } else if (!(this.boxes[x][y] instanceof Indication)){
            // Reveal adjacent boxes if the box is empty
            for(int dx = -1; dx <= 1; dx++) {
                for(int dy = -1; dy <= 1; dy++) {
                    int nx = x + dx;
                    int ny = y + dy;
                    if(nx >= 0 && nx < this.height && ny >= 0 && ny < this.width) {
                        if(!this.boxes[nx][ny].isRevealed()) {
                            reveal(nx, ny);
                        }
                    }
                }
            }
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
        for(int i = 0; i < this.height; i++) {
            for(int j = 0; j < this.width; j++) {
                if(this.boxes[i][j].isRevealed()) {
                    if(this.boxes[i][j] instanceof Indication) {
                        System.out.print(((Indication) this.boxes[i][j]).getMineCount());
                    } else if(this.boxes[i][j] instanceof Mine){
                        System.out.print("*");
                    } else {
                        System.out.print("-");
                    }
                } else  if (this.boxes[i][j].isMarked()) {
                    System.out.print("F");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
}
