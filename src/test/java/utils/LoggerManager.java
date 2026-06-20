package utils;

public final class LoggerManager {
    private static final ThreadLocal<TestLogger> LOGGER = new ThreadLocal<>();

    private LoggerManager() {
    }

    public static void setLogger(TestLogger testLogger) {
        LOGGER.set(testLogger);
    }

    public static TestLogger getLogger() {
        TestLogger testLogger = LOGGER.get();
        if (testLogger == null) {
            throw new IllegalStateException("TestLogger is not initialized for the current thread");
        }
        return testLogger;
    }

    public static void unload() {
        LOGGER.remove();
    }
}
