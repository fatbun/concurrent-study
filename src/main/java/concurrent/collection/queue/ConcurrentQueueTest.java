package concurrent.collection.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Ben Li.
 * @since: 2020/7/30 9:44 上午
 *
 * 执行结果：
 *
 * add: true
 * offer: true
 * poll: a
 * remove: b
 * peek: null
 * Exception in thread "main" java.util.NoSuchElementException
 * 	at java.util.AbstractQueue.element(AbstractQueue.java:136)
 * 	at concurrent.collection.queue.ConcurrentQueueTest.main(ConcurrentQueueTest.java:21)
 *
 * Process finished with exit code 1
 */
public class ConcurrentQueueTest {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        System.out.println("add: " + queue.add("a"));
        System.out.println("offer: " + queue.offer("b"));
        // retrieve and remove, not throw exceptiopn
        System.out.println("poll: " + queue.poll());
        // retrieve and remove, throw exception
        System.out.println("remove: " + queue.remove());
        // retrieve but not remove, not throw exceptiopn
        System.out.println("peek: " + queue.peek());
        // retrieve but not remove, throw exception
        System.out.println("element: " + queue.element());
    }
}
