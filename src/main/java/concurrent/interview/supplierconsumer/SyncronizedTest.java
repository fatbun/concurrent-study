package concurrent.interview.supplierconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/24 10:45 下午
 * <p>
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 容器最多容纳10个元素
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class SyncronizedTest {

    static Container container = new Container();

    public static void main(String[] args) {

        Thread[] producer = new Thread[2];
        Thread[] consumer = new Thread[10];

        for (int i = 0; i < producer.length; i++) {
            producer[i] = new Thread(() -> {
                for (int m = 0; m < 25; m++) {
                    container.put(Thread.currentThread()
                            .getName() + "_" + m);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },
                    "producer" + i);
        }

        for (int i = 0; i < consumer.length; i++) {
            consumer[i] = new Thread(() -> {
                for (int n = 0; n < 5; n++) {
                    String s = container.get();
                    System.out.println(Thread.currentThread()
                            .getName() + " get..." + s);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },
                    "consumer" + i);
        }

        for (int i = 0; i < consumer.length; i++) {
            consumer[i].start();
        }

        for (int i = 0; i < producer.length; i++) {
            producer[i].start();
        }
    }

    static class Container {
        private /*volatile*/ List<String> list = new ArrayList<>();
        private final int MAX = 10;
        private int count = 0;


        public Container() {
        }

        public synchronized void put(String s) {
            while (list.size() == MAX) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(s);
            ++count;
            this.notifyAll();

        }

        public synchronized String get() {
            String result = "";

            while (list.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            result = list.get(0);
            list.remove(0);
            count--;

            this.notifyAll();

            return result;
        }

    }
}
