package concurrent.threadstate;

import java.util.concurrent.locks.LockSupport;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 starting...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
                "t1");

        // NEW
        System.out.println(t1.getState());
        // RUNNABLE
        t1.start();
        ;
        System.out.println(t1.getState());
        // TIMED_WAITING
        Thread.sleep(100);
        System.out.println(t1.getState());
        // TERMINATED
        Thread.sleep(1100);
        System.out.println(t1.getState());


        Thread t2 = new Thread(() -> {
            System.out.println("t2 starting...");
            LockSupport.park();
        },
                "t2");
        t2.start();
        // WAITING
        Thread.sleep(100);
        System.out.println(t2.getState());
    }
}
