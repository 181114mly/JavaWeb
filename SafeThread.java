package Thread;
//有一个共享变量，初始0，启动20个线程，每个线程循环10000，每次循环将共享变量sum++
public class SafeThread {
    private static  int sum;

    private static synchronized void increment(int j) {
        sum++;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    increment(j);
                }
            }).start();
        }
        //所有子线程全部执行完，打印sum值
        //使用debug运行，或者用run运行改为Thread.activeCount()>2。因为idea中使用run运行会默认再启动一个线程。
        //activeCount()当前线程+当前线程的子线程活跃的总数量（子线程活跃：子线程没有运行完，它还没有结束，它就是活跃的）
        while(Thread.activeCount()>1) {
            //main线程由运行态转化为就绪态
            Thread.yield();
        }
        System.out.println(sum);
    }
}
