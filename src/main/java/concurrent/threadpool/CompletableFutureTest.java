package concurrent.threadpool;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author: Ben Li.
 * @Date: create in 2020/8/1 2:23 下午
 * <p>
 * 需求：从天猫、淘宝、JD，分别获取商品的价格。
 */
public class CompletableFutureTest {

    static Double getPriceTM() {
        delay();
        return 99.99;
    }

    static Double getPriceTB() {
        delay();
        return 89.99;
    }

    static Double getPriceJD() {
        delay();
        return 199.99;
    }

    static void delay() {
        int delay = new Random().nextInt(500);
        try {
            System.out.println("delay: " + delay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        //        异步
        CompletableFuture<Double> tmFuture = CompletableFuture.supplyAsync(CompletableFutureTest::getPriceTM);
        CompletableFuture<Double> tbFuture = CompletableFuture.supplyAsync(CompletableFutureTest::getPriceTB);
        CompletableFuture<Double> jdFuture = CompletableFuture.supplyAsync(CompletableFutureTest::getPriceJD);

        CompletableFuture.allOf(tmFuture,
                tbFuture,
                jdFuture)
                .join();

        System.out.println((System.currentTimeMillis()) - start);

    }
}
