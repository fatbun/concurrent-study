package concurrent.threadpool;

import java.util.concurrent.Executor;

/**
 * @author: Ben Li.
 * @Date: create in 2020/7/31 3:13 下午
 * <p>
 * 使用Executor执行任务
 * <p>
 * 1、任务由caller线程执行
 * 执行结果：
 * main...main
 * executor...main
 * executor...main
 * <p>
 * 2、任务由子线程执行
 * 执行结果：
 * main...main
 * executor...Thread-0
 * executor...Thread-1
 */
public class ExecutorTest implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
        //        new Thread(command).start();
    }

    public static void main(String[] args) {
        System.out.println("main..." + Thread.currentThread()
                .getName());

        ExecutorTest executorTest = new ExecutorTest();
        executorTest.execute(() -> {
            System.out.println("executor..." + Thread.currentThread()
                    .getName());
        });
        executorTest.execute(() -> {
            System.out.println("executor..." + Thread.currentThread()
                    .getName());
        });

    }
}
