package concurrent.collection.queue;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author Ben Li.
 * @since: 2020/7/30 4:43 下午
 *
 * 执行结果：
 *
 * 2
 * take1: abc
 * put1... done
 * put2... done
 * take2: abcc
 */
public class TransferQueueTest {

    public static void main(String[] args) {
        /*
            private static final int NOW   = 0; // for untimed poll, tryTransfer
            private static final int ASYNC = 1; // for offer, put, add
            private static final int SYNC  = 2; // for transfer, take
            private static final int TIMED = 3; // for timed poll, tryTransfer

            若生产者需要同步知道自己放到队列的任务被领取，则使用方法transfer阻塞等待。
         */
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                queue.transfer("abc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put1... done");
        }).start();

        new Thread(() -> {
            try {
                queue.transfer("abcc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put2... done");
        }).start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());

        try {
            System.out.println("take1: " + queue.take());
//            System.out.println("take2: " + queue.remove());
            System.out.println("take2: " + queue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
