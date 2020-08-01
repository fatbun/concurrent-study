package concurrent.threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 9:16 下午
 */
public class WorkStealingPoolTest {

    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime()
                .availableProcessors());

        ExecutorService executorService = Executors.newWorkStealingPool();

        executorService.execute(new MyTask(1000));
        executorService.execute(new MyTask(2000));
        executorService.execute(new MyTask(2000));
        executorService.execute(new MyTask(2000));
        executorService.execute(new MyTask(2000));



        System.in.read();
    }

    static class MyTask implements Runnable {

        long time;

        public MyTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + "..." + Thread.currentThread()
                    .getName());
        }
    }
}
