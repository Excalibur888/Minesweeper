public class Map {
    final int height;
    final int width;
    final Box[][] boxes;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.boxes = new Box[height][width];
    }

}
