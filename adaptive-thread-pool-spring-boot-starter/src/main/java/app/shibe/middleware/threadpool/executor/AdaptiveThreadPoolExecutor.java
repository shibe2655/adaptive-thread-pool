package app.shibe.middleware.threadpool.executor;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 對原生 {@link ThreadPoolExecutor} 的一個包裝類
 */
public class AdaptiveThreadPoolExecutor implements ExecutorService {

    /**
     * 執行緒池的唯一識別名稱
     */
    private final String poolName;

    /**
     * 被外層呼叫，作為任務代理的原生執行緒池
     */
    private final ThreadPoolExecutor internalExecutor;

    public AdaptiveThreadPoolExecutor(String poolName,
                                      int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      @NotNull TimeUnit unit,
                                      @NotNull BlockingQueue<Runnable> workQueue,
                                      @NotNull ThreadFactory threadFactory,
                                      @NotNull RejectedExecutionHandler handler) {
        this.poolName = poolName;
        this.internalExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

    @Override
    public void execute(@NotNull Runnable command) {
        internalExecutor.execute(command);
    }

    @Override
    public void shutdown() {
        internalExecutor.shutdown();
    }

    @NotNull
    @Override
    public List<Runnable> shutdownNow() {
        return internalExecutor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return internalExecutor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return internalExecutor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return internalExecutor.awaitTermination(timeout, unit);
    }

    @NotNull
    @Override
    public <T> Future<T> submit(@NotNull Callable<T> task) {
        return internalExecutor.submit(task);
    }

    @NotNull
    @Override
    public <T> Future<T> submit(@NotNull Runnable task, T result) {
        return internalExecutor.submit(task, result);
    }

    @NotNull
    @Override
    public Future<?> submit(@NotNull Runnable task) {
        return internalExecutor.submit(task);
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return internalExecutor.invokeAll(tasks);
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return internalExecutor.invokeAll(tasks, timeout, unit);
    }

    @NotNull
    @Override
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return internalExecutor.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return internalExecutor.invokeAny(tasks, timeout, unit);
    }

    public String getPoolName() {
        return poolName;
    }

    public int getCorePoolSize() {
        return internalExecutor.getCorePoolSize();
    }

    public int getMaximumPoolSize() {
        return internalExecutor.getMaximumPoolSize();
    }

    public int getPoolSize() {
        return internalExecutor.getPoolSize();
    }

    public int getActiveCount() {
        return internalExecutor.getActiveCount();
    }

    public int getLargestPoolSize() {
        return internalExecutor.getLargestPoolSize();
    }

    public long getTaskCount() {
        return internalExecutor.getTaskCount();
    }

    public long getCompletedTaskCount() {
        return internalExecutor.getCompletedTaskCount();
    }

    public BlockingQueue<Runnable> getQueue() {
        return internalExecutor.getQueue();
    }
}
