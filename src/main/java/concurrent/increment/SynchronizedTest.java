package concurrent.increment;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 10:02 下午
 * <p>
 * AtomicXXX原子操作
 */
public class SynchronizedTest {

    static int counter = 0;

    public static void test() {
        long start = System.nanoTime();

        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                m();
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

        System.out.println("counter: " + counter + ", times: " + (System.nanoTime() - start)+"...SynchronizedTest");

    }

    synchronized static void m() {
        counter++;
    }

    public static void main(String[] args) {
        test();
    }
}
