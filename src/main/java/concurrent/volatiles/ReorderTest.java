package concurrent.volatiles;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/22 9:17 下午
 *
 * 验证指令重排徐代码。
 *
 * 结果：
 * 第一次、
 * 循环了 0次
 * 循环了 100000次
 * 循环了 200000次
 * 循环了 300000次
 * 循环了 400000次
 * 循环了 500000次
 * 总共循环了 585112次 重现出指令重排!
 *
 * 第二次
 * 循环了 0次
 * 循环了 100000次
 * 总共循环了 101350次 重现出指令重排!
 *
 */
public class ReorderTest {
    static /*volatile*/ int a,b,x,y;
    static long count = 0;

    public static void main(String[] args) throws InterruptedException {

        for (;;){
            x = 0; y = 0;
            a = 0; b = 0;

            Thread t1 = new Thread(() -> {
                shortWait(100000);
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            if (x == 0 && y == 0){
                break;
            }else if (count % 100000 == 0){
                System.out.println("循环了 " + count + "次");
            }
            count++;
        }

        System.out.println("总共循环了 " + count + "次 重现出指令重排!");
    }

    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
