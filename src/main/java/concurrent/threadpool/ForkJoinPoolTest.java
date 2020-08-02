package concurrent.threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 10:15 下午
 * <p>
 * 需求：一个容量为1000000的整形数组，把里面元素做累加，使用ForkJoinTask递归累加
 */
public class ForkJoinPoolTest {

    static Integer[] nums = new Integer[1000000];
    static final Integer MAX_NUM = 50000;

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = new Random().nextInt(100);
        }

        long start = System.nanoTime();
        Integer sum = Arrays.asList(nums)
                .stream()
                .reduce(0,
                        Integer::sum);
        System.out.println("同步求和: " + sum + "..." + (System.nanoTime() - start));
        ;
    }

    static class MyForkJoinTask extends RecursiveTask<Long> {

        int start, end;

        public MyForkJoinTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                Long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }

            int middle = start + (end - start) / 2;

            MyForkJoinTask left = new MyForkJoinTask(start,
                    middle);
            MyForkJoinTask right = new MyForkJoinTask(middle,
                    end);

            ForkJoinTask<Long> lv = left.fork();
            ForkJoinTask<Long> rv = right.fork();

            return lv.join() + rv.join();
        }
    }

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        MyForkJoinTask myForkJoinTask = new MyForkJoinTask(0,
                nums.length);

        long start = System.nanoTime();
        ForkJoinTask<Long> result = forkJoinPool.submit(myForkJoinTask);
        try {
            System.out.println("异步求和: "+result.get() + "..." + (System.nanoTime() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("done...");
    }
}
