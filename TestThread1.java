package Thread;

//方法一：继承Thread类，重写run()方法，调用start()方法启动线程
public class TestThread1 extends Thread {
    @Override
    //run()方法，线程处于运行态执行的程序
    public void run() {
        for(int i=0;i<20;i++){
            System.out.println("星期一  "+i);
        }
    }
    public static void main(String[] args) {
        //创建一个线程对象
        TestThread1 t1=new TestThread1();
        //调用start()方法启动线程，盛情系统调度并运行。
        t1.start();
        for(int i=0;i<1000;i++){
            System.out.println("星期二  "+i);
        }
    }
}
//方法二：实现Runnable接口，重写run()方法，创建Runnable接口实现类的对象，将类对象作为Thread类的参数。
class TestThread2 implements Runnable{
    @Override
    public void run() {
        for(int i=0;i<20;i++){
            System.out.println("晴天  "+i);
        }
    }

    public static void main(String[] args) {
        //创建Runnable接口实现类的对象
       TestThread2 t2=new TestThread2();
       //创建线程对象
       new Thread(t2).start();
        for(int i=0;i<1000;i++){
            System.out.println("雨天  "+i);
        }
    }
}
//方法三：使用匿名类创建 Thread 子类对象
class TestThread3{
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("使用匿名类创建 Thread 子类对象");
            }
        };
        t1.start();
    }
}
// 方法四：使用匿名类创建 Runnable 子类对象
class TestThread4{
    public static void main(String[] args) {
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("使用匿名类创建 Runnable 子类对象");
            }
        });
        t2.start();
    }
}
//方法五：使用 lambda 表达式创建 Runnable 子类对象
class TestThread5{
    public static void main(String[] args) {
        Thread t3 = new Thread(() -> {
            System.out.println("使用匿名类创建 Thread 子类对象");
        });
        t3.start();
    }
}