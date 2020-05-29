package Thread;
//单例：获取同一个对象

public class Sington1 {

    //1、懒汉式-单线程版本
    /*
       多线程不安全原因？
       多线程情况下，多个线程同时执行到 if(SINGTON2==null)语句，创建多个引用对象
     */
    private static Sington1 SINGTON2;

    private Sington1(){};
    public static Sington1 getInstance2() {
        if (SINGTON2 == null) {
            SINGTON2 = new Sington1();
        }
        return SINGTON2;
    }

    //2、懒汉式-多线程版-性能低-synchronized静态同步方法
    /*
        为什么性能低？
        比如main线程同时启动10个线程；多线程下类加载SINGTON3==null;
        调用getInstance3()方法，线程同步互斥；第一个进入的线程，创建好对象，并返回；
        此时对象已经实例化，但是后面的9个线程还是通过同步互斥获取对象，此过程可通过并发执行，但是现在是按序执行，效率低
     */
    private static Sington1 SINGTON3;

    //private Sington(){};
    public synchronized static Sington1 getInstance3() {
        if (SINGTON3 == null) {
            SINGTON3 = new Sington1();
        }
        return SINGTON3;
    }

    //3、 懒汉式-多线程版-二次判断-未使用volatile
    /*
    此方法会造成指令重排序
    什么是指令重排序：JVM编译JAVA文件为CLASS字节码文件时，CPU执行机器码时(JVM运行时解释字节码为机器码)，会执行指令重排序
     是否可以重排序，与指令的前后依赖性有关
     例如：i++操作;(1)  读：线程从主内存中读i的值，拷贝到到工作内存中;
                 （2）修改：线程在工作内存中修改i的值+1;
                  (3)  写：线程把i修改后的值写入主内存中。
                  所以，此操作是非原子性操作。
          SINGTON4=new Sington()这条语句是非原子性操作;
                   分解为三条指令
                   1、分配对象的内存空间
                   2、初始化对象
                   3、对象赋值给引用变量（此时SINGTON4是非null的）
         上述过程中存在指令重拍的优化，步骤一和步骤二不能进行指令重排序，因为这两条指令存在前后依赖性。
         可以先分配对象的内存空间，对象赋值给引用变量，初始化对象，第二步和第三步的顺序是无法保证的而导致出错

         举例：比如线程A和线程B并发执行，线程A执行分配对象的内存空间，对象赋值给引用变量操作。
         这时时间片结束了，线程B执行if(SINGTON4==null)语句，发现SINGTON4!=null，返回对象，
         此时对象还没有初始化，会出现问题。线程A线程安全，线程B线程不安全。

         如果一个变量的操作不会分解为多条指令，那么就是线程安全的；否则线程不安全。
     */
    private static Sington1 SINGTON4;

    //private Sington(){};
    public static Sington1 getInstance4() {
        if (SINGTON4 == null) {
            synchronized (Sington1.class) {
                if (SINGTON4 == null) {
                    SINGTON4 = new Sington1();
                    //分解为三条指令
                    //分配对象的内存空间
                    //初始化对象
                    //对象赋值给引用变量
                }
            }
        }
        return SINGTON4;
    }

    //4、 懒汉式-多线程版-二次判断-性能高-使用volatile
    /*
     volatile：保证可见性（变量都在主存进行操作）；禁止指令重排序，建立内存屏障；不能保证原子性
     加上volitate之后，指令的执行顺序为：分配对象的内存空间；初始化对象；对象赋值给引用变量；
     步骤一和步骤二因为存在依赖性不能交换顺序，步骤三SINGTON5被volatile修饰，不会重排序。

      举例：比如线程A和线程B并发执行，线程A正在执行，这时时间片结束了；线程B执行if(SINGTON4==null)语句。
         假如if语句在步骤一和步骤二之间执行，此时SINGTON4为null，不影响。
         假如if语句在步骤二和步骤三之间执行，此时SINGTON4为null，不影响。
         假如if语句在步骤三之后执行，此时SINGTON4为非null，引用变量都已经指向对象了，也不影响。
         综上所述，if语句插入任意指令之间，线程安全。
     */
    private static volatile Sington1 SINGTON5;

    //private Sington(){};
    public static Sington1 getInstance5() {
        if (SINGTON5 == null) {
            synchronized (Sington1.class) {
                if (SINGTON5 == null) {
                    SINGTON5 = new Sington1();
                }
            }
        }
        return SINGTON5;
    }


}
