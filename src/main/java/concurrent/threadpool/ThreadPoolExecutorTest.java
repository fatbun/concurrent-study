package concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 10:41 上午
 * <p>
 * ThreadPoolExecutor，
 * 1、生产者往线程池丢任务时，线程池先从分配给空闲的核心线程处理。
 * 2、当所有核心线程都在忙，则会把任务放到任务阻塞队列中等待。
 * 3、当阻塞队列已满，线程池会创建新的worker线程执行任务，创建worker线程数量不超过最大线程数。
 * 4、当核心线程忙、阻塞队列满、worker线程达到最大线程数，仍有新任务进来，此时走拒绝策略处理新任务。
 * 5、高峰已过，线程池根据空闲线程存活时间，回收核心线程以外的worker线程。
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {

        /**
         * ThreadPoolExecutor 七大参数
         *
         * @param coolPoolSize：核心线程数
         * @param maximumPoolSize：最大线程数
         * @param keepAliveTime：空闲线程存活时间
         * @param timeunit：空闲存活时间单位
         * @param queue：任务队列
         * @param threadFactory：线程工厂
         * @param handle：拒绝策略
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
                6,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {

                System.out.println(Thread.currentThread()
                        .getName() + "..." + finalI);
            });

        }

        threadPoolExecutor.shutdown();
    }
}
