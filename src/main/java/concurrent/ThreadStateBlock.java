package concurrent;

public class ThreadStateBlock implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ThreadStateBlock(),
                "t1");
        Thread t2 = new Thread(new ThreadStateBlock(),
                "t2");
        t1.start();
        t2.start();
        Thread.sleep(100);
        // RUNNABLE
        System.out.println("t1..." + t1.getState());
        // BLOCKED
        System.out.println("t2..." + t2.getState());
    }

    @Override
    public void run() {
        synchronized (ThreadStateBlock.class) {
            System.out.println("i am a lock...");
            while (true) {

            }
        }
    }
}
