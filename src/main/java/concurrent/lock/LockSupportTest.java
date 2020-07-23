package concurrent.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Ben Li.
 * @since: 2020/7/23 9:49 上午
 * <p>
 * LockSupport.park()
 * 阻塞当前线程后面的操作，直到其他线程调用LockSupport.unpark()，或Thread.interupt()释放
 */
public class LockSupportTest {
    static MyThread t1 = new MyThread("T1");
    static MyThread t2 = new MyThread("T2");

    public static void main(String[] args) {
        t1.start();
        t2.start();

        LockSupport.unpark(t1);
        t2.interrupt();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class MyThread extends Thread {

        private String name;

        public MyThread(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("线程" + name + "启动...");
            LockSupport.park();
            if (Thread.currentThread()
                    .isInterrupted()) {
                System.out.println("线程" + name + "被中断...");
            }
            System.out.println("线程" + name + "继续执行...");
        }
    }
}
