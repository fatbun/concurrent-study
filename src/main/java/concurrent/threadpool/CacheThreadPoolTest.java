package concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 3:12 下午
 *
 * 1、确认现有worker线程忙时，线程池对新来的任务分配新worker执行；
 * 2、确认worker线程闲时（60s），线程池回收worker线程;
 *
 * 执行结果：
 * java.util.concurrent.ThreadPoolExecutor@5e2de80c[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
 * java.util.concurrent.ThreadPoolExecutor@5e2de80c[Running, pool size = 4, active threads = 4, queued tasks = 0, completed tasks = 0]
 * pool-1-thread-2
 * pool-1-thread-4
 * pool-1-thread-3
 * pool-1-thread-1
 * java.util.concurrent.ThreadPoolExecutor@5e2de80c[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 4]
 *
 */
public class CacheThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println(executorService);
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread()
                        .getName());
            });
        }

        System.out.println(executorService);

        try {
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(executorService);

        executorService.shutdown();
    }
}
