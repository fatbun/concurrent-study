package concurrent.reference;

/**
 * @author Ben Li.
 * @since: 2020/7/28 5:01 下午
 */
public class MyObject {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize...");
    }
}
