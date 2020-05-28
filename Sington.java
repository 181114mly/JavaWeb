package Thread;
/*单例：获取同一个对象
  类加载在JVM内部执行，保证了线程安全
  (1)只执行一次 (2)线程安全
 */

public class Sington {
    //饿汉式：在类加载的时候就会执行static语句以及static静态代码块，,创建对象

    /* 为什么类属性要设置为静态？
     *（1）SINGTON需要在调用getInstance时候被初始化，只有static的成员才能在没有创建对象时进行初始化。
     *（2）类的静态成员在类第一次被使用时初始化后就不会再被初始化，保证了单例。
     */
    private static Sington SINGTON=new Sington();

    /*为什么设置私有的构造方法
      （1）因为本类是public,在其他类中new Sington()就会产生其他的对象。
           每次new对象的时候都要调用构造方法 而private的权限是当前类 那么其他类new对象的时候一定会失败
      （2）构造方法设置成private是考虑封装性，防止在外部类中进行初始化
     */
    private Sington(){};

    public static Sington getInstance(){
        return SINGTON;
    }


}
