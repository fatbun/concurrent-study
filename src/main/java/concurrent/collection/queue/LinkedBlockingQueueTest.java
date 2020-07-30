package concurrent.collection.queue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Ben Li.
 * @since: 2020/7/30 10:34 上午
 * <p>
 * 需求：1个生产者往队列放元素100次，5个消费者不断地从队列中取数，直到队列空阻塞
 * <p>
 * 执行结果：
 * <p>
 * supplier put: element0
 * consumer take: element0
 * supplier put: element1
 * consumer take: element1
 * supplier put: element2
 * consumer take: element2
 * supplier put: element3
 * consumer take: element3
 * supplier put: element4
 * consumer take: element4
 * ...
 */
public class LinkedBlockingQueueTest {

    public static void main(String[] args) {
        // 默认"无界队列"，实际上是有界的，最大长度是Integer.MAX_VALUE
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    String p = "element" + i;
                    queue.put(p);
                    System.out.println(Thread.currentThread()
                            .getName() + " put: " + p);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },
                "supplier").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    String take = queue.take();
                    System.out.println(Thread.currentThread()
                            .getName() + " take: " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            },
                    "consumer").start();
        }
    }
}
