import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private static final int LIMIT = 3; // max 3 requests
    private static final long TIME_WINDOW = 1000; // 1 second

    private static final Map<String, Integer> requestCount = new HashMap<>();
    private static final Map<String, Long> timeStamp = new HashMap<>();

    public static synchronized boolean allowRequest(String apiKey) {

        long currentTime = System.currentTimeMillis();

        timeStamp.putIfAbsent(apiKey, currentTime);
        requestCount.putIfAbsent(apiKey, 0);

        long startTime = timeStamp.get(apiKey);

        if (currentTime - startTime > TIME_WINDOW) {
            // Reset window
            timeStamp.put(apiKey, currentTime);
            requestCount.put(apiKey, 1);
            return true;
        }

        int count = requestCount.get(apiKey);

        if (count < LIMIT) {
            requestCount.put(apiKey, count + 1);
            return true;
        }

        return false;
    }
}
