package concurrent.increment;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 10:02 下午
 * <p>
 * AtomicXXX原子操作
 */
public class AtomicTest {

    public static void test() {
        long start = System.nanoTime();

        AtomicInteger counter = new AtomicInteger(0);
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                counter.incrementAndGet();
            });
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("counter: " + counter.get() + ", times: " + (System.nanoTime() - start) + "...AtomicTest");

    }

    public static void main(String[] args) {
        test();
    }
}
