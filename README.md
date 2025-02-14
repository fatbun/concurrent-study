# Java Concurrency Learning Project

本项目为Java并发编程学习仓库，涵盖并发工具类、线程同步、线程池等多个核心主题。各模块说明如下：

## 一、Concurrent Collections (并发集合)

| 类名                        | 说明                                                  |
| --------------------------- | ----------------------------------------------------- |
| `CopyOnWriteListTest`       | CopyOnWriteArrayList实践 - 写时复制实现的线程安全List |
| `ArrayBlockingQueueTest`    | 数组结构有界阻塞队列的阻塞操作演示                    |
| `ConcurrentLinkedQueueTest` | 非阻塞线程安全队列的并发操作示例                      |
| `DelayQueueTest`            | 延时队列的使用场景演示（任务调度等）                  |
| `LinkedBlockingQueueTest`   | 链表结构可选有界阻塞队列的容量控制                    |
| `PriorityQueueTest`         | 优先级阻塞队列的排序规则实现                          |
| `SynchronousQueueTest`      | 直接传递队列的生产者消费者模式实现                    |
| `TransferQueueTest`         | 带有transfer方法的特殊队列操作示例                    |



## 二、Atomic Operations (原子操作)

| 类名               | 说明                                    |
| ------------------ | --------------------------------------- |
| `AtomicTest`       | 基本原子类（AtomicInteger等）的使用演示 |
| `LongAdderTest`    | LongAdder与传统atomic类的性能对比测试   |
| `CompareAndSwap`   | CAS底层原理实现模拟                     |
| `SynchronizedTest` | synchronized关键字的同步控制示例        |



## 三、Locks (锁机制)

### 3.1 基础锁

| 类名                | 说明                     |
| ------------------- | ------------------------ |
| `ReentrantLockTest` | 可重入锁的标准用法演示   |
| `ReadWriteLockTest` | 读写锁分离的性能优化示例 |



### 3.2 同步工具

| 类名                 | 说明                                 |
| -------------------- | ------------------------------------ |
| `CountDownLatchTest` | 多线程计数同步控制（火箭发射场景）   |
| `CyclicBarrierTest`  | 可重复使用的线程屏障（会议等待场景） |
| `SemaphoreTest`      | 信号量的并发流量控制演示             |
| `PhaserTest`         | 分阶段执行的同步器示例               |



## 四、Thread Pool (线程池)

| 类名                     | 说明                     |
| ------------------------ | ------------------------ |
| `ThreadPoolExecutorTest` | 自定义线程池参数配置     |
| `ForkJoinPoolTest`       | 分治任务框架使用示例     |
| `CompletableFutureTest`  | 异步编程链路实践         |
| `WorkStealingPoolTest`   | 工作窃取线程池的性能演示 |



## 五、Memory Visibility (内存可见性)

| 类名             | 说明                             |
| ---------------- | -------------------------------- |
| `VisibilityTest` | volatile的可见性保证演示         |
| `ReorderTest`    | 指令重排序现象及解决方案         |
| `VolatileTest`   | volatile关键字的不同使用场景测试 |



## 六、Other Concurrency Tools

| 类名                   | 说明                           |
| ---------------------- | ------------------------------ |
| `ThreadLocalTest`      | 线程本地变量使用与内存泄漏预防 |
| `PhantomReferenceTest` | 虚引用在资源清理中的应用       |



------

> **项目特征分析**：

1.典型并发场景全覆盖（线程同步/通信/资源管理）

2.包含JDK 5+到JDK 8的新特性（如LongAdder、CompletableFuture）

3.多维度实践对比（synchronized vs Lock，不同队列实现选择等）

4.包含JMM核心概念验证（可见性、有序性）



# 案例学习

## 一、并发集合核心实现

### 1. CopyOnWriteListTest

```java
// 迭代过程中可修改（迭代器使用原始数组快照）
List<Integer> list = new CopyOnWriteArrayList<>();
list.add(1); 
list.add(2);

Iterator<Integer> it = list.iterator();
list.add(3); // 不会触发并发修改异常

while(it.hasNext()) {
    System.out.println(it.next()); // 输出 1, 2（看不到新增的3）
}
```

**核心逻辑**：写操作时复制新数组保证迭代一致性，适合读多写少场景

### 2. ArrayBlockingQueueTest

```java
BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

// 生产者
new Thread(() -> {
    queue.put(1); // 队列满时自动阻塞
}).start();

// 消费者
new Thread(() -> {
    Integer num = queue.take(); // 队列空时阻塞等待
}).start();
```

**阻塞机制**：使用ReentrantLock + Condition实现等待/通知模型

------

## 二、原子操作核心代码

### 1. LongAdderTest

```java
LongAdder adder = new LongAdder();
// 多线程并发累加
for (int i = 0; i < 10; i++) {
    new Thread(() -> {
        adder.add(1);
    }).start();
}
// 最终一致性结果（分段锁减少竞争）
System.out.println(adder.sum()); 
```

**优势**：通过Cell[]分散热点数据，高并发下性能优于AtomicLong

### 2. AtomicTest (CAS实现)

```java
public class AtomicTest {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void safeIncrement() {
        int oldVal;
        do {
            oldVal = count.get();
        } while (!count.compareAndSet(oldVal, oldVal + 1));
    }
}
```

**CAS逻辑**：循环判断内存值是否被修改，避免锁竞争

------

## 三、锁机制关键实现

### 1. ReentrantLockTest

```java
ReentrantLock lock = new ReentrantLock();
Condition condition = lock.newCondition();

lock.lock();
try {
    while (queue.isEmpty()) {
        condition.await(); // 释放锁并进入等待
    }
    // 执行操作...
    condition.signalAll();
} finally {
    lock.unlock();
}
```

**核心点**：显式加锁解锁 + Condition精准控制线程唤醒

### 2. CountDownLatchTest

```java
CountDownLatch latch = new CountDownLatch(3);

// 多个子线程
Runnable task = () -> {
    // 执行任务...
    latch.countDown();
};

// 主线程等待所有任务完成
latch.await(); 
System.out.println("All tasks completed");
```

**使用场景**：多个并行任务完成后触发主线程操作

------

## 四、线程池核心配置

### ThreadPoolExecutorTest

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5, // 核心线程数
    10, // 最大线程数
    60L, TimeUnit.SECONDS, // 空闲线程存活时间
    new ArrayBlockingQueue<>(100) // 工作队列
);

executor.execute(() -> {
    // 任务执行逻辑
});
```

**关键参数调优**：根据任务类型（CPU密集/IO密集）设置合理的队列和线程数

------

## 五、内存可见性示例

### VisibilityTest

```java
// 不加volatile时读取线程可能无法感知变化
private volatile boolean flag = true;

new Thread(() -> {
    while(flag) { /* 循环 */ } // 立刻可见修改
}).start();

new Thread(() -> {
    flag = false; // 修改后立即刷新到主内存
}).start(); 
```

**实现原理**：volatile通过内存屏障禁止指令重排序 + 强制写入/读取主内存



## 六、Special Concurrency Tools (特殊并发工具)

### 1. 引用类型测试（reference包）

#### PhantomTest 虚引用示例

```java
// 追踪对象被回收的状态
Object obj = new Object();
ReferenceQueue<Object> queue = new ReferenceQueue<>();
PhantomReference<Object> phantomRef = new PhantomReference<>(obj, queue);

obj = null; // 取消强引用
System.gc();

// 虚引用只能通过队列检测对象回收
Reference<?> ref = queue.remove(500);
if (ref != null) {
    System.out.println("对象已被回收，可执行资源清理");
}
```

**应用场景**：实现精细化的资源释放控制（如直接内存回收）

#### WeakTest 弱引用示例

```java
WeakReference<byte[]> weakRef = new WeakReference<>(new byte[10*1024*1024]);
System.gc();
System.out.println(weakRef.get()); // 大概率输出null
```

**特性**：在GC时无论内存是否充足都会被回收

------

### 2. ThreadLocalTest 线程本地变量

```java
// 定义带有初始值的ThreadLocal
private static ThreadLocal<SimpleDateFormat> dateFormat = 
    ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

// 不同线程独立使用自己的副本
new Thread(() -> {
    System.out.println(dateFormat.get().format(new Date()));
}).start();
```

**内存泄漏防护**：必须在使用后调用`remove()`删除Entry

------

### 3. SingletonTest 单例模式

#### 双重校验锁实现

```java
public class Singleton {
    private static volatile Singleton instance; // 禁止指令重排序
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(); // volatile保证初始化完成
                }
            }
        }
        return instance;
    }
}
```

**核心要点**：volatile确保对象的可见性与有序性

------

### 4. 同步方式对比（synchronize包）

#### BlockMethodTest 同步方法

```java
public synchronized void increment() {
    count++; // 锁对象为当前实例
}
```

#### BlockCodeTest 同步代码块

```java
public void increment() {
    synchronized(lockObj) { // 显式指定锁对象
        count++;
    }
}
```

**选择建议**：优先缩小同步范围（使用代码块减少锁粒度）
