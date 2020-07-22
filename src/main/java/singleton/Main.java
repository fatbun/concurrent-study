package singleton;

/**
 * @author Ben Li.
 * @since: 2020/7/22 6:00 下午
 */
public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                SingletonTest2 instance = SingletonTest2.getInstance();
                System.out.println(instance);
            },
                    "T" + i).start();
        }
    }
}
