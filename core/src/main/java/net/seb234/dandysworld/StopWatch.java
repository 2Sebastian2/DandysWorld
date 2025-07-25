package net.seb234.dandysworld;

public class StopWatch {

    private final long startTime;

    private long initTime;

    public StopWatch() {
        startTime = System.nanoTime();
    }

    public void start() {
        initTime = System.nanoTime();
    }

    @SuppressWarnings("DefaultLocale")
    public String getElapsedTime() {
        long elapsedTime = System.nanoTime() - initTime;
        initTime = 0;

        long millis = elapsedTime / 1_000_000;
        long min = millis / 60_000;
        long sec = (millis % 60_000) / 1_000;

        return String.format("%02d min, %02d s and %03d ms", min, sec, millis);
    }

    public long getFromStartTime() {
        return System.nanoTime() - startTime;
    }
}
