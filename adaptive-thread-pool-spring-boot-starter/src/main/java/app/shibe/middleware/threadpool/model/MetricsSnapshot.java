package app.shibe.middleware.threadpool.model;

import java.time.Instant;

/**
 * Encapsulates a complete snapshot of performance metrics for a single thread pool
 * at a specific point in time.
 */
public final class MetricsSnapshot {

    /**
     * The unique name for identifying this thread pool.
     */
    private final String poolName;

    private final Instant timestamp;

    private final int corePoolSize;

    private final int maximumPoolSize;

    private final int poolSize;

    private final int activeThreads;

    private final int queueSize;

    private final int queueRemainingCapacity;

    private final long completedTaskCount;

    private final long rejectedTaskCount;

    public MetricsSnapshot(String poolName, int corePoolSize, int maximumPoolSize, int poolSize, int activeThreads,
                           int queueSize, int queueRemainingCapacity, long completedTaskCount, long rejectedTaskCount) {
        this.poolName = poolName;
        this.timestamp = Instant.now();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.poolSize = poolSize;
        this.activeThreads = activeThreads;
        this.queueSize = queueSize;
        this.queueRemainingCapacity = queueRemainingCapacity;
        this.completedTaskCount = completedTaskCount;
        this.rejectedTaskCount = rejectedTaskCount;
    }
}
