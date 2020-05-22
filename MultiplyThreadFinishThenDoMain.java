//同时运行多个线程，等待所有线程执行完毕，再执行Main
package Thread;
//期望结果：0~19随时同时打印，全部打印完之后，再打印main
public class MultiplyThreadFinishThenDoMain  {
    public static void main(String[] args) throws InterruptedException {
       Thread[] threads=new Thread[20];
        //for循环的时间很短，cpu执行非常快,相对申请线程、创建线程来说for循环时间可忽略。
        //线程可以同时申请系统创建并调度
        for (int i = 0; i < 20; i++) {
            final int j=i;
            Thread t=new Thread(()->{
                System.out.println(j);
            });
            t.start();
            threads[i]=t;
        }
        //让main线程阻塞，引用线程执行完毕，再往下执行，如果下一个引用线程已经执行完毕，join()方法就不等待了，直接往下执行。
        //main线程阻塞等待这20个线程同时执行完。
        for (int i = 0; i < 20; i++) {
            threads[i].join();
        }
        System.out.println("main");
    }
}
