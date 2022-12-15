public class Timer {
    Timer() {
        startTime = System.currentTimeMillis();
    }

    synchronized public long GetCurrentTime() {
        return System.currentTimeMillis() - startTime;
    }
    synchronized public String GetCurrentTimeInFormat() {
        long current = System.currentTimeMillis() - startTime;
        String ms = Long.toString(current % 1000);
        String s = Long.toString(current / 1000);
        return s + "s" + ":" + ms + "ms";
    }


    long startTime;
}
