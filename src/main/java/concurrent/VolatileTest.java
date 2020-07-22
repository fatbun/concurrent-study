package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author Ben Li.
 * @since: 2020/7/22 5:10 下午
 */
public class VolatileTest {

    /*volatile*/ boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别

    void m() {
        System.out.println("m start");
        while (running) {
        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        VolatileTest t = new VolatileTest();

        new Thread(t::m,
                "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;
    }
}
