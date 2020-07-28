package concurrent.reference;

import java.lang.ref.WeakReference;

/**
 * @author Ben Li.
 * @since: 2020/7/28 5:15 下午
 * <p>
 * 弱引用遭到gc就会回收
 *
 * 执行结果：
 * concurrent.reference.MyObject@610455d6
 * null
 * finalize...
 */
public class WeakTest {
    public static void main(String[] args) {
        WeakReference<MyObject> m = new WeakReference<>(new MyObject());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

    }
}
