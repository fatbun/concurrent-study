package concurrent.synchronize;

/**
 * @author Ben Li.
 * @since: 2020/7/22 4:14 下午
 */
public class BlockCodeTest2 {

    private Integer count = 0;

    public void add() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            this.count++;
        }
    }

    public Integer getCount() {
        return count;
    }

    public static void main(String[] args) {
        BlockCodeTest2 s = new BlockCodeTest2();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                s.add();
            });
        }

        Long start = System.nanoTime();
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println(s.getCount() + "... time: " + (System.nanoTime() - start));
    }
}
