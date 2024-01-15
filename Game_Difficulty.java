public enum Game_Difficulty {
    EASY(10),
    NORMAL(40),
    HARD(99),
    PERSONALISED(-1);

    public final int mineCount;

    private Game_Difficulty(int label) {
        this.mineCount = label;
    }
}
