package lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author Ben Li.
 * @since: 2020/7/23 10:19 上午
 * <p>
 * CountDownLatch初始化倒数值，latch.await()阻塞等待 倒数到0为止；
 * <p>
 * 执行结果：
 * t0 count down...
 * t3 count down...
 * t2 count down...
 * t1 count down...
 * t4 count down...
 * done...
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread()
                        .getName() + " count down...");
                latch.countDown();
            },
                    "t" + i);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        try {
            latch.await();
            System.out.println("done...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
