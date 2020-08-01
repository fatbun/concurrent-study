package concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 2:52 下午
 */
public class SingleThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread()
                        .getName());
            });
        }

        executorService.shutdown();
    }
}
