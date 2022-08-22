package service.impl;

import java.util.Queue;
import service.RateLimiter;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int timeGap;
    private final int maxRequestsPerTimeGap;
    private final Queue<Long> slidingWindow;

    public SlidingWindowRateLimiter(int timeGap, int maxRequestsPerTimeGap) {
        this.timeGap = timeGap;
        this.maxRequestsPerTimeGap = maxRequestsPerTimeGap;
        this.slidingWindow = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean allowRequest() {
        adjustSlidingWindow();

        if (slidingWindow.size() < maxRequestsPerTimeGap) {
            slidingWindow.add(System.currentTimeMillis());
            return true;
        }

        return false;
    }

    private void adjustSlidingWindow() {
        if (this.slidingWindow.size() == 0) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long currentTimeGap = (currentTime - slidingWindow.peek()) / 1000;

        while (currentTimeGap > timeGap) {
            this.slidingWindow.remove();

            if (this.slidingWindow.size() == 0) {
                return;
            }

            currentTimeGap = (currentTime - slidingWindow.peek()) / 1000;
        }
    }
}
