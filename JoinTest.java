package Thread;
// join()等待当前线程结束。
// join(long millis)当前线程阻塞并等待引用线程死亡，最多millis毫秒。
//如果引用线程执行完毕或者给定的时间到了，当前线程继续向下执行。
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(()->{
            try {
                Thread.sleep(3000L);
                System.out.println("Thread Baby");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        //t.join();
        //t.join(1000L);
        t.join(5000L);
        System.out.println("main");
    }
}
