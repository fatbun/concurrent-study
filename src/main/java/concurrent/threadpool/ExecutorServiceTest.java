package concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/31 4:04 下午
 */
public class ExecutorServiceTest {
    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {

        invokeTest();

        executorService.shutdown();
    }

    static void invokeTest() {
        List<MyCallable> list = new ArrayList<>();

        list.add(new MyCallable(1000));
        list.add(new MyCallable(3000));
        list.add(new MyCallable(1500));
        list.add(new MyCallable(2000));
        list.add(new MyCallable(2500));

        try {
//            executorService.invokeAll(list,1000,TimeUnit.MILLISECONDS);
            executorService.invokeAny(list,900,TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void submitTest() {
        Future<?> future = executorService.submit(() -> {
            System.out.println(Thread.currentThread()
                    .getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        try {
            Object o = future.get();
            System.out.println("result: " + o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    static class MyCallable implements Callable<String> {

        long delay;

        public MyCallable(long delay) {
            this.delay = delay;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(delay);
            System.out.println(Thread.currentThread()
                    .getName());
            return "a";
        }
    }

}
