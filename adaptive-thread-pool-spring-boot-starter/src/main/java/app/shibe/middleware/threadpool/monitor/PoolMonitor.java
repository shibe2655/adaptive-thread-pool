package app.shibe.middleware.threadpool.monitor;

import app.shibe.middleware.threadpool.executor.AdaptiveThreadPoolExecutor;

public class PoolMonitor {

    private final AdaptiveThreadPoolExecutor executor;

    public PoolMonitor(AdaptiveThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public synchronized void updatePoolSize(int coreSize, int maxSize) {
    }
}
