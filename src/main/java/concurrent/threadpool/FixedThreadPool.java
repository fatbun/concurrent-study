package concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 3:30 下午
 * <p>
 * 需求：找出 1~10000中的所有质数，比较同步、异步执行效率
 *
 * 执行结果：
 * =========同步执行==========
 * 11223785
 * =========异步执行==========
 * 5706668
 *
 */
public class FixedThreadPool {


    public static void main(String[] args) {

        System.out.println("=========同步执行==========");
        ExecutorService service = Executors.newFixedThreadPool(4);
        MyTask myTask = new MyTask(1,
                10000);
        Future<List<Integer>> f = service.submit(myTask);
        try {
            long start = System.nanoTime();
            f.get();
            System.out.println((System.nanoTime()) - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();


        System.out.println("=========异步执行==========");
        ExecutorService asyncService = Executors.newFixedThreadPool(4);

        MyTask myTask1 = new MyTask(1,
                2000);
        MyTask myTask2 = new MyTask(2001,
                4000);
        MyTask myTask3 = new MyTask(4001,
                6000);
        MyTask myTask4 = new MyTask(6001,
                10000);

        Future<List<Integer>> f1 = asyncService.submit(myTask1);
        Future<List<Integer>> f2 = asyncService.submit(myTask2);
        Future<List<Integer>> f3 = asyncService.submit(myTask3);
        Future<List<Integer>> f4 = asyncService.submit(myTask4);

        try {
            long start = System.nanoTime();
            f1.get();
            f2.get();
            f3.get();
            f4.get();
            System.out.println((System.nanoTime()) - start);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        asyncService.shutdown();
    }

    static class MyTask implements Callable<List<Integer>> {

        int start, end;

        public MyTask(int start, int end) {
            this.start = start;
            this.end = end;
        }


        @Override
        public List<Integer> call() throws Exception {
            return getPrime(start,
                    end);
        }
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) results.add(i);
        }

        return results;
    }
}
