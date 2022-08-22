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
                Thread.sleep(500);
                executorService.submit(() -> {
                    if (rateLimiter.allowRequest()) {
                        System.out.println("Request allowed");
                    } else {
                        System.out.println("Request rejected");
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
