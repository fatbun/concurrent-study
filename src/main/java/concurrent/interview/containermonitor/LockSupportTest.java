package concurrent.interview.containermonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/24 9:15 下午
 * <p>
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2等待（不占用cpu）线程1添加个数到5个时，线程2给出提示并结束
 * <p>
 * 执行结果:
 * T2 启动...
 * T1 启动...
 * T1 1...
 * T1 2...
 * T1 3...
 * T1 4...
 * T1 5...
 * T2 结束...
 * T1 6...
 * T1 7...
 * T1 8...
 * T1 9...
 * T1 10...
 */
public class LockSupportTest {
    static volatile Container container = new Container();

    public static void main(String[] args) {


        Thread t2 = new Thread(() -> {
            System.out.println("T2 启动...");
            LockSupport.park();
            System.out.println("T2 结束...");
        });

        Thread t1 = new Thread(() -> {
            System.out.println("T1 启动...");

            for (int i = 0; i < 10; i++) {
                container.add();
                System.out.println("T1 " + container.size() + "...");

                if (container.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        });

        t2.start();
        t1.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(t1);
    }

    static class Container {
        /*volatile*/ List<String> list = Collections.synchronizedList(new ArrayList<>());

        public void add() {
            list.add("a");
        }

        public int size() {
            return list.size();
        }

    }
}
