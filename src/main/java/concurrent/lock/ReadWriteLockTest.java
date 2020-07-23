package concurrent.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Ben Li.
 * @since: 2020/7/23 5:31 下午
 * <p>
 * ReentrantLock加强版，读锁时共享，写锁时独享
 *
 * 执行结果：
 * read over!
 * read over!
 * read over!
 * read over!
 * read over!
 * ...
 * write over!
 * write over!
 * read write lock...3015573496
 * read over!
 * read over!
 * read over!
 * read over!
 * ...
 * reentrantLock.....20084764250
 */
public class ReadWriteLockTest {

    static int value;


    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over!");
            //模拟读取操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
            //模拟写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void test1(String message, Lock readLock, Lock writeLock) {

        //Runnable readR = ()-> read(lock);
        Runnable readR = () -> read(readLock);

        //Runnable writeR = ()->write(lock, new Random().nextInt());
        Runnable writeR = () -> write(writeLock,
                new Random().nextInt());

        Thread[] readers = create(readR,
                18);
        Thread[] writers = create(writeR,
                2);

        for (int i = 0; i < readers.length; i++) {
            readers[i].start();
        }
        for (int i = 0; i < writers.length; i++) {
            writers[i].start();
        }

        long start = System.nanoTime();
        for (int i = 0; i < readers.length; i++) {
            try {
                readers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < writers.length; i++) {
            try {
                writers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(message + (System.nanoTime() - start));
    }


    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        Lock reentrantLock = new ReentrantLock();
        test1("read write lock...",
                readLock,
                writeLock);
        test1("reentrantLock.....",
                reentrantLock,
                reentrantLock);

    }

    private static Thread[] create(Runnable runnable, int num) {
        Thread[] threads = new Thread[num];

        for (int i = 0; i < num; i++) {
            threads[i] = new Thread(runnable);
        }

        return threads;
    }
}
