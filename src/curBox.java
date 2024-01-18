/**
 * Represents a wrapper class for a Minesweeper game box with its coordinates.
 */
public class curBox {
    public Box box;
    public int x;
    public int y;

    public curBox(Box box, int x, int y) {
        this.box = box;
        this.x = x;
        this.y = y;
    }
}
