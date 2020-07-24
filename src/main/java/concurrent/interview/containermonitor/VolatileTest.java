package concurrent.interview.containermonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/24 9:15 下午
 * <p>
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 执行结果：
 * T1_1 启动...
 * T2 启动...
 * T1_1 1...
 * T1_1 2...
 * T1_1 3...
 * T1_1 4...
 * T2 结束...
 * T1_1 5...
 * T1_1 6...
 * T1_1 7...
 * T1_1 8...
 * T1_1 9...
 * T1_1 10...
 */
public class VolatileTest {
    static volatile Container container = new Container();

    public static void main(String[] args) {

        Thread t1_1 = new Thread(() -> {
            System.out.println("T1_1 启动...");
            for (int i = 0; i < 10; i++) {
                container.add();
                System.out.println("T1_1 " + container.size() + "...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("T2 启动...");
            while (true) {
                if (container.size() == 5) {
                    System.out.println("T2 结束...");
                    break;
                }
            }
        });

        t1_1.start();
        t2.start();

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
