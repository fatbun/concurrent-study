package concurrent.increment;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 10:08 下午
 */
public class LongAdderTest {

    public static void test() {
        long start = System.nanoTime();

        LongAdder counter = new LongAdder();
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                counter.increment();
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
        System.out.println("counter: " + counter.longValue() + ", times: " + (System.nanoTime() - start)+"...LongAdderTest");
    }

    public static void main(String[] args) {
        test();
    }
}
