package concurrent.reference;

import java.lang.ref.SoftReference;

/**
 * @author Ben Li.
 * @since: 2020/7/28 5:09 下午
 * <p>
 * 软引用
 * 内存不足时, JVM会回收此对象, 适合用于缓存
 * -Xmx20M
 */
public class SoftTest {

    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        //m = null;
        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());

        //再分配一个数组，heap将装不下，这时候系统会垃圾回收，先回收一次，如果不够，会把软引用干掉
        byte[] b = new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }
}
