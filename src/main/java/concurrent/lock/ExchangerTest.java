package concurrent.lock;

import java.util.concurrent.Exchanger;

/**
 * @author Ben Li.
 * @since: 2020/7/23 4:18 下午
 *
 * 线程T1 与 线程T2 交换信息
 *
 * 执行结果：
 * T1 exchanged: T2的信物
 * T2 exchanged: T1的信物
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread t1 = new Thread(() -> {
            try {
                String e = exchanger.exchange("T1的信物");
                System.out.println(Thread.currentThread()
                        .getName() + " exchanged: " + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
                "T1");

        Thread t2 = new Thread(() -> {
            try {
                String e = exchanger.exchange("T2的信物");
                System.out.println(Thread.currentThread()
                        .getName() + " exchanged: " + e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
                "T2");

//        Thread t3 = new Thread(() -> {
//            try {
//                String e = exchanger.exchange("T3的信物");
//                System.out.println(Thread.currentThread()
//                        .getName() + " exchanged: " + e);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },
//                "T3");

        t1.start();
        t2.start();
//        t3.start();

    }
}
