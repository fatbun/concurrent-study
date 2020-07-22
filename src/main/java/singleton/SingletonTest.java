package singleton;

/**
 * @author Ben Li.
 * @since: 2020/7/22 5:55 下午
 * <p>
 * 饿汉式
 */
public class SingletonTest {
    private static SingletonTest instance = new SingletonTest();

    private SingletonTest() {
    }

    public SingletonTest getInstance() {
        return instance;
    }
}
