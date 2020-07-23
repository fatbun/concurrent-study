package concurrent.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Ben Li.
 * @since: 2020/7/23 10:44 上午
 * <p>
 * CyclicBarrier初始化设定一组5个成员，并定义回调触发Runnable方法。
 * 当5个成员进组（线程调用await()），能触发执行一次Runnable回调；
 * 当成员进组但不够5人时，设置await超时参数中断等待。
 * <p>
 * 执行结果：
 * 5个进程await，开始执行...
 * 5个进程await，开始执行...
 * 5个进程await，开始执行...
 * java.util.concurrent.TimeoutException
 * at java.util.concurrent.CyclicBarrier.dowait(CyclicBarrier.java:257)
 * at java.util.concurrent.CyclicBarrier.await(CyclicBarrier.java:435)
 * at concurrent.lock.CyclicBarrierTest.lambda$main$1(CyclicBarrierTest.java:24)
 * at java.lang.Thread.run(Thread.java:745)
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5,
                () -> {
                    System.out.println("5个进程await，开始执行...");
                });

        Thread[] threads = new Thread[16];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    barrier.await(1,
                            TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            },
                    "T" + i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        System.out.println(barrier.getNumberWaiting());
    }
}
