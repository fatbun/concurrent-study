package singleton;

/**
 * @author Ben Li.
 * @since: 2020/7/22 5:55 下午
 * <p>
 * 懒汉式
 */
public class SingletonTest2 {
    private static SingletonTest2 instance;

    private SingletonTest2() {
    }

    public static SingletonTest2 getInstance() {
        if (instance == null) {
            synchronized (SingletonTest2.class) {
                if (instance == null) {

                    instance = new SingletonTest2();
                }
            }
        }

        return instance;
    }
}
