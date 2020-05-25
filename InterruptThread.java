package Thread;
//1、设置标志位
//缺点：当try catch中sleep()较长时间时，不能及时中断。
//本应该在3秒之后就及时中断，但在程序处于阻塞状态，没有中断
 class StopThreadTest1 {
    private volatile static boolean IS_STOP;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while(!IS_STOP){
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(3000L);
        IS_STOP=true;
    }
}

/*2、在子线程sleep阻塞的时候，中断它。
（1）线程初始时，中断标志位=false
（2）主线程休眠3秒，子线程执行3秒后处于阻塞状态
（3）调用线程的interrupt()方法，该线程的中断标志位=true，
（4）线程处于阻塞态，中断抛出InterruptedException(该异常在while循环体外被捕获)，因此循环被终止。
 */
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(Thread.currentThread().getName());
                        //sleep线程阻塞时，也可以中断，抛出异常sleep interrupted
                        Thread.sleep(1000000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        Thread.sleep(3000L);
        t.interrupt();
    }
}
/*
3、在while循环体内进行阻塞
（1）线程初始时，中断标志位=false
（2）调用线程的interrupt()方法，该线程的中断标志位=true
（3）如果线程处于阻塞态，中断抛出InterruptedException时(该异常在while循环体内被捕获)，
     会重置线程的中断标志位(即isInterrupted()会返回false)
 */
class InterruptThread1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    //线程阻塞的时候，抛出InterruptedException中断线程，isInterrupted=false;
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(3000L);
        t.interrupt();
    }
}
      //解决方法：在捕获异常时，额外的进行退出while循环的处理。
      // 例如，在catch(InterruptedException)中添加break或return就能解决该问题。

//4、Thread.interrupted()作用
class InterruptThread2 {
    public static void main(String[] args)  {
        Thread t=new Thread(()->{
            //Thread.interrupted()：返回当前的中断标志位，并重置标志位
            //（1）boolean tmp = isInterrupted;
            //（2）isInterrupted=false;
            //（3）return tmp;
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.interrupted());
            }
        });
        t.start();
        t.interrupt();
    }
}
//5、线程对象.isInterrupted()作用：只返回中断标志位，不做任何修改
class InterruptThread3 {
    public static void main(String[] args)  {
        Thread t=new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });
        t.start();
        t.interrupt();
    }
}
