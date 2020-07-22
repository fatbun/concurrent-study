package concurrent.increment;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 10:11 下午
 */
public class Compare {

    public static void main(String[] args){
        AtomicTest.test();
        SynchronizedTest.test();
        LongAdderTest.test();
    }
}
