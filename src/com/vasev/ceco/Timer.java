package com.vasev.ceco;

/**
 * Created by vasev on 22/05/19.
 */
public class Timer {
    private long time;

    float stop() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis- time;
    }

    private long getCurrentTime()
    {
        return System.currentTimeMillis();
    }

    public void start()
    {
        time = getCurrentTime();
    }
}

