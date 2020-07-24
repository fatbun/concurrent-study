package concurrent.interview.A1B2C3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/24 10:10 下午
 * <p>
 * 题目：两个线程依次交替往容器里添加字符（A1B2C3...Z26），结束输出容器里的字符串
 * <p>
 * 执行结果：
 * [A, 1, B, 2, C, 3, D, 4, E, 5, F, 6, G, 7, H, 8, I, 9, J, 10, K, 11, L, 12, M, 13, N, 14, O, 15, P, 16, Q, 17, R, 18, S, 19, T, 20, U, 21, V, 22, W, 23, X, 24, Y, 25, Z, 26]
 */
public class LockSupportTest {

    static List<String> list = Collections.synchronizedList(new ArrayList<>());
    static Thread t1, t2 = null;

    public static void main(String[] args) {


        t1 = new Thread(() -> {
            String[] str = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            for (String s : str) {
                list.add(s);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (int i = 1; i <= 26; i++) {
                LockSupport.park();
                list.add(i + "");
                LockSupport.unpark(t1);

            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list);
    }
}
