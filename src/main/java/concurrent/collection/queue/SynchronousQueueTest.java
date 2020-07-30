package concurrent.collection.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Ben Li.
 * @since: 2020/7/30 4:23 下午
 * <p>
 * 需求：
 * 1、线程往SynchronousQueue put一个元素，在线程阻塞等待消费者的时候，主线程看看queue的长度
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {
        /*
         队列容量为0，生产者消费者需要同步执行。

         场景一、生产者：添加操作-》生产者阻塞等待-》消费者：取并移除操作-》生产者释放阻塞、消费者操作完成
         场景二、消费者：取并移除操作-》消费者阻塞等待-》生产者：添加操作-》消费者释放阻塞、生产者操作完成
         */
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                queue.put("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                queue.put("b");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            Thread.sleep(100);
            System.out.println("size:" + queue.size());
            System.out.println("take:" + queue.take());
            System.out.println("size:" + queue.size());
            System.out.println("take:" + queue.take());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
