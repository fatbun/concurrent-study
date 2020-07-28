package concurrent.reference;

/**
 * @author Ben Li.
 * @since: 2020/7/28 5:01 下午
 * <p>
 * 强引用，内存不足也不会回收此对象
 */
public class StrongTest {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        //myObject = null;
        System.gc();
    }
}
