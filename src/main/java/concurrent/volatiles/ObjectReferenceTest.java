package concurrent.volatiles;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 9:39 下午
 * <p>
 * volatile对象及其属性的可见性
 *
 * 第一次
 * a = 0, b=15
 * end
 *
 * 第二次
 * a = 0, b=16
 * end
 *
 */
public class ObjectReferenceTest {

    static volatile Data data;

    public static void main(String[] args) {
        Thread reader = new Thread(() -> {
            while (data == null) {
            }
            int x = data.a;
            int y = data.b;
            System.out.printf("a = %s, b=%s%n",
                    x,
                    y);
        });

        Thread writer = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                data = new Data(i,
                        i);
            }
        });

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }

    static class Data {
        int a, b;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
