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
}
