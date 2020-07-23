package concurrent.lock;

import java.util.concurrent.Semaphore;

/**
 * @author Ben Li.
 * @since: 2020/7/23 2:58 下午
 * <p>
 * 需求：10个学生先后到饭堂打饭，打饭窗口只有2个，
 * 因此每次只能有两个学生能同时打饭
 *
 * 执行结果：
 * 学生-T0 进入饭堂...
 * 学生-T5 进入饭堂...
 * 学生-T0 开始打饭...
 * 学生-T4 进入饭堂...
 * 学生-T8 进入饭堂...
 * 学生-T3 进入饭堂...
 * 学生-T2 进入饭堂...
 * 学生-T1 进入饭堂...
 * 学生-T9 进入饭堂...
 * 学生-T7 进入饭堂...
 * 学生-T0 离开...
 * 学生-T5 开始打饭...
 * 学生-T6 进入饭堂...
 * 学生-T5 离开...
 * 学生-T4 开始打饭...
 * 学生-T8 开始打饭...
 * 学生-T8 离开...
 * 学生-T4 离开...
 * 学生-T3 开始打饭...
 * 学生-T2 开始打饭...
 * 学生-T2 离开...
 * 学生-T3 离开...
 * 学生-T1 开始打饭...
 * 学生-T9 开始打饭...
 * 学生-T9 离开...
 * 学生-T1 离开...
 * 学生-T7 开始打饭...
 * 学生-T7 离开...
 * 学生-T6 开始打饭...
 * 学生-T6 离开...
 */
public class SemaphoreTest {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Student("T" + i,
                    semaphore);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

    }

    static class Student extends Thread {

        private String name;
        private Semaphore semaphore;

        public Student(String name, Semaphore semaphore) {
            this.name = name;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            System.out.println("学生-" + name + " 进入饭堂...");
            try {
                semaphore.acquire();
                System.out.println("学生-" + name + " 开始打饭...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("学生-" + name + " 离开...");
                semaphore.release();
            }
        }
    }
}