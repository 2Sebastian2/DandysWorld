package net.seb234.dandysworld.dataholder;

import net.seb234.dandysworld.screens.GameScreen;

public class NetUtils {

    private static int oldThread = 0;

    @SuppressWarnings("DefaultLocale")
    public static void threadHandler() {
        if (Thread.activeCount() > oldThread) {
            oldThread = Thread.activeCount();
            if (oldThread > 10) {
                GameScreen.console.error("ThreadHandler", String.format("Too Many Threads. Counting: %d", oldThread));
            } else {
                GameScreen.console.log("ThreadHandler", String.format("New Thread Detected. Counting: %d", oldThread));
            }
        }
    }
}

