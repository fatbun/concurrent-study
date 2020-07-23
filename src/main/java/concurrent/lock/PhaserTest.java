package concurrent.lock;

import java.util.concurrent.Phaser;

/**
 * @author Ben Li.
 * @since: 2020/7/23 11:22 上午
 * <p>
 * 题目：5个学生参加考试，一共有三道题，要求监考老师1名，且所有学生到齐才能开始考试
 * ，全部做完第一题，才能继续做第二题，后面类似。最后，老师收卷结束。
 */
public class PhaserTest {

    public static void main(String[] args) {
        // 3学生，1老师
        final int num = 4;
        Phaser phaser = new MyPhaser(num);
        Person[] person = new Person[num];
        person[0] = new Teacher("老师",
                phaser);
        for (int i = 1; i < num; i++) {
            person[i] = new Student("学生" + i,
                    phaser);
        }

        Thread[] threads = new Thread[person.length];
        for (int i = 0; i < person.length; i++) {
            Person p = person[i];
            threads[i] = new Thread(() -> {
                p.doExercise1();
                p.doExercise2();
                p.doExercise3();
                p.doCollectPaper();
            });
        }
        for (int i = 0; i < person.length; i++) {
            threads[i].start();
        }
    }

    static class MyPhaser extends Phaser {
        public MyPhaser(int parties) {
            super(parties);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                // 第一道题
                case 0:
                    System.out.println("所有学生完成第一道题...");
                    return false;
                case 1:
                    System.out.println("所有学生完成第二道题...");
                    return false;
                case 2:
                    System.out.println("所有学生完成第三道题...");
                    return false;
                case 3:
                    System.out.println("老师完成收卷");
                    return true;
            }
            return false;
        }
    }


    static class Teacher implements Person {
        String name;
        Phaser phaser;

        public Teacher(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        @Override
        public void doExercise1() {
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doExercise2() {
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doExercise3() {
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doCollectPaper() {
            System.out.println("老师开始收卷...");
            phaser.arriveAndAwaitAdvance();
        }
    }

    static class Student implements Person {
        String name;
        Phaser phaser;

        public Student(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        @Override
        public void doExercise1() {
            System.out.println(name + " 开始做练习1...");
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doExercise2() {
            System.out.println(name + " 开始做练习2...");
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doExercise3() {
            System.out.println(name + " 开始做练习3...");
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void doCollectPaper() {
            phaser.arriveAndDeregister();
        }
    }

    interface Person {
        void doExercise1();

        void doExercise2();

        void doExercise3();

        void doCollectPaper();
    }
}
