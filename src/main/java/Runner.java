import service.RateLimiter;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import service.impl.SlidingWindowRateLimiter;

public class Runner {

    public static void main(String[] args) {
        RateLimiter rateLimiter = new SlidingWindowRateLimiter(5, 5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0;i < 10;i++) {
                Thread.sleep(1000);
                executorService.submit(() -> {
                    if (rateLimiter.allowRequest()) {
                        System.out.println("Request allowed " + System.currentTimeMillis()/1000);
                    } else {
                        System.out.println("Request rejected " + System.currentTimeMillis()/1000);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
