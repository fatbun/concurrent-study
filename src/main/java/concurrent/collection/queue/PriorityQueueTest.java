package concurrent.collection.queue;

import java.util.PriorityQueue;

/**
 * @author Ben Li.
 * @since: 2020/7/30 3:06 下午
 * <p>
 * 需求：随机插入字符，输出看排序结果
 * <p>
 * 执行结果：
 * [a, c, e, x, f, m]
 */
public class PriorityQueueTest {

    public static void main(String[] args) {

        // 默认容量11
        PriorityQueue<String> queue = new PriorityQueue<>();
        queue.add("c");
        queue.add("a");
        queue.add("e");
        queue.add("x");
        queue.add("f");
        queue.add("m");

        System.out.println(queue);
    }
}
