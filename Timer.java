public class Timer {
    private long elapsedTime;
    private long startTime;

    public Timer() {
        this.elapsedTime = 0;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.elapsedTime += System.currentTimeMillis() - startTime;
    }

    public long elapsed() {
        return ((System.currentTimeMillis() - this.startTime) + elapsedTime) / 1000;
    }

    public long elapsedSeconds() {
        return this.elapsed() % 60;
    }

    public long elapsedMinutes() {
        return this.elapsed() / 60;
    }

    public void display() {
        System.out.format("%d : %02d\t", elapsedMinutes(), elapsedSeconds());
    }
}
