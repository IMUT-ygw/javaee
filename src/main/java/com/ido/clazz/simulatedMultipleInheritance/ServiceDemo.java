package com.ido.clazz.simulatedMultipleInheritance;

/**
 * 静态内部类
 */
public class ServiceDemo implements IService{
    //静态内部类可调用name
    private static String name = "123";
    @Override
    public void method() {
        System.out.println("IService");
    }

    static class ServiceStatic implements IService2{
        @Override
        public void method() {
            System.out.println("IService2" +":" + name);
        }
    }

    public static void main(String[] args) {
        ServiceDemo serviceDemo = new ServiceDemo();
        serviceDemo.method();
        ServiceStatic serviceStatic = new ServiceStatic();
        serviceStatic.method();
    }
}
