package concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 11:17 上午
 * <p>
 * 需求：观察线程池执行shutdown方法后，但仍有任务在执行时的线程池状态
 * <p>
 * 执行结果：
 * ================before shutdown==============
 * isShutdown...false
 * ================after shutdown==============
 * isShutdown....true
 * isTerminated..false
 * java.util.concurrent.ThreadPoolExecutor@5f184fc6[Shutting down, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * pool-1-thread-3
 * pool-1-thread-2
 * pool-1-thread-5
 * pool-1-thread-1
 * pool-1-thread-4
 * pool-1-thread-2
 * pool-1-thread-1
 * pool-1-thread-4
 * pool-1-thread-5
 * pool-1-thread-3
 * isShutdown....true
 * isTerminated..true
 * java.util.concurrent.ThreadPoolExecutor@5f184fc6[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 10]
 */
public class ThreadPoolShutDownTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
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

        System.out.println("================before shutdown==============");
        System.out.println("isShutdown..." + executorService.isShutdown());
        executorService.shutdown();
        System.out.println("================after shutdown==============");
        System.out.println("isShutdown...." + executorService.isShutdown());
        System.out.println("isTerminated.." + executorService.isTerminated());
        System.out.println(executorService);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("isShutdown...." + executorService.isShutdown());
        System.out.println("isTerminated.." + executorService.isTerminated());
        System.out.println(executorService);
    }
}
