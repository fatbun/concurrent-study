package concurrent.thread;


/**
 * @author Ben Li.
 * @since: 2020/7/29 10:48 上午
 * <p>
 * ThreadLocal的值线程间隔离
 * <p>
 * 执行结果：
 * t0:threadlocal-0
 * t4:threadlocal-4
 * t3:threadlocal-3
 * t2:threadlocal-2
 * t1:threadlocal-1
 * t6:threadlocal-6
 * t5:threadlocal-5
 * t7:threadlocal-7
 * t9:threadlocal-9
 * t8:threadlocal-8
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                threadLocal.set("threadlocal-" + finalI);
                System.out.println(Thread.currentThread()
                        .getName() + ":" + threadLocal.get());
            },
                    "t" + i).start();
        }
    }
}
