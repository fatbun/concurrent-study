package concurrent.collection.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Ben Li.
 * @since: 2020/7/30 3:37 下午
 * <p>
 * DelayQueue在PriorityQueue基础上，放入待延期时间的任务到队列中，
 * 当且仅当到达延期时间后，消费者才能从队列中取到任务，否则返回null
 *
 *
 * <p>
 * 需求：5个任务分别在延迟不同的时间后处理
 * <p>
 * 执行结果：
 * [MyTask{name='t5', runningTime=1596095849781}, MyTask{name='t4', runningTime=1596095850781}, MyTask{name='t3', runningTime=1596095851781}, MyTask{name='t2', runningTime=1596095852281}, MyTask{name='t1', runningTime=1596095851281}]
 * MyTask{name='t5', runningTime=1596095849781}
 * MyTask{name='t4', runningTime=1596095850781}
 * MyTask{name='t1', runningTime=1596095851281}
 * MyTask{name='t3', runningTime=1596095851781}
 * MyTask{name='t2', runningTime=1596095852281}
 */
public class DelayQueueTest {

    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        DelayQueue<MyTask> queue = new DelayQueue<>();
        MyTask t1 = new MyTask("t1",
                now + 2000);
        MyTask t2 = new MyTask("t2",
                now + 3000);
        MyTask t3 = new MyTask("t3",
                now + 2500);
        MyTask t4 = new MyTask("t4",
                now + 1500);
        MyTask t5 = new MyTask("t5",
                now + 500);

        queue.put(t1);
        queue.put(t2);
        queue.put(t3);
        queue.put(t4);
        queue.put(t5);

        System.out.println(queue);
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyTask implements Delayed {
        String name;
        long runningTime;

        public MyTask(String name, long runningTime) {
            this.name = name;
            this.runningTime = runningTime;
        }


        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "name='" + name + '\'' +
                    ", runningTime=" + runningTime +
                    '}';
        }
    }
}
