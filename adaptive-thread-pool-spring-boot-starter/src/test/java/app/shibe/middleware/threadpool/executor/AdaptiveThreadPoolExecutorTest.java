package app.shibe.middleware.threadpool.executor;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdaptiveThreadPoolExecutor Core Functionality Tests")
class AdaptiveThreadPoolExecutorTest {

    @Mock
    private ThreadPoolExecutor internalExecutor;

    private AdaptiveThreadPoolExecutor adaptiveExecutor;

    private static final String TEST_THREAD_POOL_NAME = "test-thread-pool";

    @BeforeEach
    void setUp() {
        adaptiveExecutor = new AdaptiveThreadPoolExecutor(TEST_THREAD_POOL_NAME, internalExecutor);
    }

    @Test
    @DisplayName("Constructor should set pool name correctly")
    void constructor_shouldSetPoolNameCorrectly() {
        assertEquals(TEST_THREAD_POOL_NAME, adaptiveExecutor.getPoolName());
    }

    @Nested
    @DisplayName("Task Delegation Tests")
    class TaskDelegationTests {

        /**
         * Test Objective:
         * Verify that when adaptiveExecutor.execute() is called, it passes the exact same Runnable
         * object to internalExecutor.execute().
         */
        @Test
        @DisplayName("execute(Runnable) should delegate to the internal executor")
        void execute_shouldDelegateToInternalExecutor() {
            // 1. Arrange: Create a Runnable instance
            Runnable task = () -> {};

            // 2. Act
            adaptiveExecutor.execute(task);
            
            // 3. Assert: Verify that internalExecutor.execute() was called once with the exact same Runnable instance.
            verify(internalExecutor).execute(task);
        }

        /**
         * Test Objective:
         * Verify that the submit(Callable) method not only delegates the call but also
         * correctly returns the Future object from the internalExecutor.
         */
        @Test
        @DisplayName("submit(Callable) should delegate call and return the same Future instance")
        @SuppressWarnings("unchecked")
        void submit_withCallable_shouldDelegateAndReturnSameFuture() {
            // 1. Arrange: Prepare test data and configure mock behavior
            Callable<String> task = () -> "result";
            Future<String> mockFuture = mock(Future.class);
            when(internalExecutor.submit(task)).thenReturn(mockFuture);

            // 2. Act: Call the method under test
            Future<String> returnedFuture = adaptiveExecutor.submit(task);

            // 3. Assert
            // Verify that internalExecutor.submit(Callable) was called correctly
            verify(internalExecutor).submit(task);
            // Verify that the Future returned by adaptiveExecutor is the same one returned by internalExecutor
            assertSame(mockFuture, returnedFuture, "The returned Future should be the same instance as the one from the internal executor");
        }

        /**
         * Test Objective:
         * Verify that the submit(Runnable) method not only delegates the call but also
         * correctly returns the Future object from the internalExecutor.
         */
        @Test
        @DisplayName("submit(Runnable) should delegate call and return the same Future instance")
        @SuppressWarnings("unchecked")
        void submit_withRunnable_shouldDelegateAndReturnSameFuture() {
            // 1. Arrange: Prepare test data and configure mock behavior
            Runnable task = () -> {};
            var mockFuture = mock(Future.class);
            when(internalExecutor.submit(task)).thenReturn(mockFuture);

            // 2. Act: Call the method under test
            Future<?> returnedFuture = adaptiveExecutor.submit(task);

            // 3. Assert
            // Verify that internalExecutor.submit(Runnable) was called correctly
            verify(internalExecutor).submit(task);
            // Verify that the Future returned by adaptiveExecutor is the same one returned by internalExecutor
            assertSame(mockFuture, returnedFuture, "The returned Future should be the same instance as the one from the internal executor");
        }

        /**
         * Test Objective:
         * Verify that the submit(Runnable, T) method not only delegates the call but also
         * correctly returns the Future object from the internalExecutor.
         */
        @Test
        @DisplayName("submit(Runnable, T) should delegate call and return the same Future instance")
        @SuppressWarnings("unchecked")
        void submit_withRunnableAndResult_shouldDelegateAndReturnSameFuture() {
            // 1. Arrange: Prepare test data and configure mock behavior
            Runnable task = () -> {};
            String mockResult = "result";
            var mockFuture = mock(Future.class);
            when(internalExecutor.submit(task, mockResult)).thenReturn(mockFuture);

            // 2. Act: Call the method under test
            Future<String> returnedFuture = adaptiveExecutor.submit(task, mockResult);

            // 3. Assert
            // Verify that internalExecutor.submit(Runnable, T) was called correctly
            verify(internalExecutor).submit(task, mockResult);
            // Verify that the Future returned by adaptiveExecutor is the same one returned by internalExecutor
            assertSame(mockFuture, returnedFuture, "The returned Future should be the same instance as the one from the internal executor");
        }
    }

    @Nested
    @DisplayName("State Exposure Tests")
    class StateExposureTests {

        /**
         * Test Objective:
         * Verify that the getCorePoolSize() method correctly queries and returns the corePoolSize
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getCorePoolSize should return value from internal executor")
        void getCorePoolSize_shouldReturnInternalValue() {
            // 1. Arrange
            final int EXPECTED_CORE_POOL_SIZE = 10;
            when(internalExecutor.getCorePoolSize()).thenReturn(EXPECTED_CORE_POOL_SIZE);
            // 2. Act
            int actual = adaptiveExecutor.getCorePoolSize();
            // 3. Assert
            assertEquals(EXPECTED_CORE_POOL_SIZE, actual);
        }

        /**
         * Test Objective:
         * Verify that the getMaximumPoolSize() method correctly queries and returns the maximumPoolSize
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getMaximumPoolSize should return value from internal executor")
        void getMaximumPoolSize_shouldReturnInternalValue() {
            // 1. Arrange
            final int EXPECTED_MAXIMUM_POOL_SIZE = 10;
            when(internalExecutor.getMaximumPoolSize()).thenReturn(EXPECTED_MAXIMUM_POOL_SIZE);
            // 2. Act
            int actual = adaptiveExecutor.getMaximumPoolSize();
            // 3. Assert
            assertEquals(EXPECTED_MAXIMUM_POOL_SIZE, actual);
        }

        /**
         * Test Objective:
         * Verify that the getPoolSize() method correctly queries and returns the poolSize
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getPoolSize should return value from internal executor")
        void getPoolSize_shouldReturnInternalValue() {
            // 1. Arrange
            final int EXPECTED_POOL_SIZE = 10;
            when(internalExecutor.getPoolSize()).thenReturn(EXPECTED_POOL_SIZE);
            // 2. Act
            int actual = adaptiveExecutor.getPoolSize();
            // 3. Assert
            assertEquals(EXPECTED_POOL_SIZE, actual);
        }

        /**
         * Test Objective:
         * Verify that the getActiveCount() method correctly queries and returns the activeCount
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getActiveCount should return value from internal executor")
        void getActiveCount_shouldReturnInternalValue() {
            // 1. Arrange
            final int EXPECTED_ACTIVE_COUNT = 10;
            when(internalExecutor.getActiveCount()).thenReturn(EXPECTED_ACTIVE_COUNT);
            // 2. Act
            int actual = adaptiveExecutor.getActiveCount();
            // 3. Assert
            assertEquals(EXPECTED_ACTIVE_COUNT, actual);
        }

        /**
         * Test Objective:
         * Verify that the getLargestPoolSize() method correctly queries and returns the largestPoolSize
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getLargestPoolSize should return value from internal executor")
        void getLargestPoolSize_shouldReturnInternalValue() {
            // 1. Arrange
            final int EXPECTED_LARGEST_POOL_SIZE = 10;
            when(internalExecutor.getLargestPoolSize()).thenReturn(EXPECTED_LARGEST_POOL_SIZE);
            // 2. Act
            int actual = adaptiveExecutor.getLargestPoolSize();
            // 3. Assert
            assertEquals(EXPECTED_LARGEST_POOL_SIZE, actual);
        }

        /**
         * Test Objective:
         * Verify that the getTaskCount() method correctly queries and returns the taskCount
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getTaskCount should return value from internal executor")
        void getTaskCount_shouldReturnInternalValue() {
            // 1. Arrange
            final long EXPECTED_TASK_COUNT = 10L;
            when(internalExecutor.getTaskCount()).thenReturn(EXPECTED_TASK_COUNT);
            // 2. Act
            long actual = adaptiveExecutor.getTaskCount();
            // 3. Assert
            assertEquals(EXPECTED_TASK_COUNT, actual);
        }

        /**
         * Test Objective:
         * Verify that the getCompletedTaskCount() method correctly queries and returns the completedTaskCount
         * from the internalExecutor.
         */
        @Test
        @DisplayName("getCompletedTaskCount should return value from internal executor")
        void getCompletedTaskCount_shouldReturnInternalValue() {
            // 1. Arrange
            final long EXPECTED_COMPLETED_TASK_COUNT = 10L;
            when(internalExecutor.getCompletedTaskCount()).thenReturn(EXPECTED_COMPLETED_TASK_COUNT);
            // 2. Act
            long actual = adaptiveExecutor.getCompletedTaskCount();
            // 3. Assert
            assertEquals(EXPECTED_COMPLETED_TASK_COUNT, actual);
        }

        /**
         * Test Objective:
         * Verify that getQueue() returns the same queue instance held by the internal executor.
         */
        @Test
        @DisplayName("getQueue should return the internal executor's queue")
        @SuppressWarnings("unchecked")
        void getQueue_shouldReturnInternalQueue() {
            // 1. Arrange
            BlockingQueue<Runnable> mockQueue = mock(BlockingQueue.class);
            when(internalExecutor.getQueue()).thenReturn(mockQueue);

            // 2. Act
            BlockingQueue<Runnable> actualQueue = adaptiveExecutor.getQueue();

            // 3. Assert
            assertSame(mockQueue, actualQueue, "The returned queue should be the same instance as the internal one.");
        }
    }
}
