package concurrent.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ben Li.
 * @since: 2020/7/29 3:23 下午
 * <p>
 * 写时复制集合，适用于读多写少的场景。
 * 缺点：写时效率较低；优点：查询效率高。
 * （用空间换时间）
 * <p>
 * <p>
 * 需求：
 * 1、写：100个线程，各自向集合插入1000个随机数；
 * 2、读：上述集合中，读集合第10个数，1000000次；
 * <p>
 * 执行结果：
 * ------ CopyOnWriteArrayList ------
 * 写耗时：4515436433
 * 读耗时：5925871
 * ------ SynchronizedList ------
 * 写耗时：30381612
 * 读耗时：24037324
 */
public class CopyOnWriteListTest {

    static void test(List<String> list) {
        List<String> write = write(list);
        read(write);
    }

    static List<String> write(List<String> list) {
        long start = System.nanoTime();

        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                String tname = Thread.currentThread()
                        .getName();
                for (int j = 0; j < 1000; j++) {
                    list.add(tname + "..." + j);
                }
            },
                    "T" + i);
        }

        Arrays.asList(threads)
                .forEach(Thread::start);
        Arrays.asList(threads)
                .forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });


        System.out.println("写耗时：" + (System.nanoTime() - start));
        return list;
    }

    static void read(List<String> list) {
        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            list.get(10);
        }

        System.out.println("读耗时：" + (System.nanoTime() - start));
    }


    public static void main(String[] args) {

        System.out.println("------ CopyOnWriteArrayList ------");
        List<String> list1 = new CopyOnWriteArrayList<>();
        test(list1);
        System.out.println("------ SynchronizedList ------");
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());
        test(list2);
    }
}
