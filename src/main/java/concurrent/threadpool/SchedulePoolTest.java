package concurrent.threadpool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 4:17 下午
 */
public class SchedulePoolTest {

    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

        // 按照固定的频率，重复执行任务
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    try {
                        long delay = new Random().nextInt(1000);
                        TimeUnit.MILLISECONDS.sleep(delay);

                        System.out.println(Thread.currentThread()
                                .getName() + "... delayed " + delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                },
                0,
                500,
                TimeUnit.MILLISECONDS);

    }
}
