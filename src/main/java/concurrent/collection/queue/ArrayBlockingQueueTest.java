package concurrent.collection.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Ben Li.
 * @since: 2020/7/30 2:47 下午
 * <p>
 * 需求：在满载的arrayBlockingQueue队列中，再放入一个元素
 * <p>
 * 执行结果：
 * false
 * [abc, abc, abc, abc, abc, abc, abc, abc, abc, abc]
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        for (int i = 0; i < 10; i++) {
            try {
                queue.put("abc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println(queue.offer("c",
                    1,
                    TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(queue);
    }
}
