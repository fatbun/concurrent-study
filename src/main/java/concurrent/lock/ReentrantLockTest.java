package concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ben Li.
 * @since: 2020/7/23 4:32 下午
 * <p>
 * 可重入锁(cas)，可以用于替换synchronized
 * <p>
 * lock.lock() 与 lock.lockInterruptibly()区别
 * lock()：线程被interrupt，不会立即执行，等到线程拿到锁才会执行中断；
 * lockInterruptibly()：线程被interrupt，立即执行；
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        //
        //        Thread t1 = new MyThread("t1",
        //                lock);
        //        Thread t2 = new MyThread("t2",
        //                lock);
        //
        //        t1.start();
        //        t2.start();
        //
        //        try {
        //            t1.join();
        //            t2.join();
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }

        Thread t3 = new Thread(() -> {
            lock.lock();
            System.out.println("t3 acquire成功...");
            //            lock.unlock();
        });

        Thread t4 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t4 acquire成功...");
        });
        t3.start();
        t4.start();
        t4.interrupt();
    }

    static class MyThread extends Thread {
        String name;
        Lock lock;

        public MyThread(String name, Lock lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                if (lock.tryLock()) {
                    System.out.println(name + " acquire成功，然后睡1秒...");
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    System.out.println(name + " acquire失败...");
                    lock.lock();
                    System.out.println(name + " acquire成功...");
                }

            } catch (Exception e) {
                //ignore
            } finally {
                lock.unlock();
            }
        }
    }
}
