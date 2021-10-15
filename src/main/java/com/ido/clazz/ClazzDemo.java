package com.ido.clazz;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 获取class的四种方式：
 *           类名.class
 *           对象.getClass
 *           class.getClassLoader().loadClass()
 *           Class.forName()
 *
 */
public class ClazzDemo {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        getInternalClass();
    }
    public void getClassMethod() throws ClassNotFoundException {
        String name = "name";
        Class<? extends String> aClass0 = name.getClass();
        Class<?> aClass1 = Class.forName("java.lang.String");
        Class<?> aClass2 = ClazzDemo.class.getClassLoader().loadClass("java.lang.String");
        Class<?> aClass4 = String.class;
    }
    public void getAllDataMethod()throws Exception{
        //获取类的构造函数
        Class<?> clazz = Class.forName("com.ido.clazz.Test");
        //获取所有构造器
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        System.out.println(Arrays.toString(declaredConstructors));
        //根据参数获取构造函数(可以获取私有)
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(String.class);
        System.out.println(declaredConstructor.toString());
        //获取所有公共构造函数
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println(Arrays.toString(constructors));
        //根据参数获取构造函数（不能获取私有）
        Constructor<?> constructor = clazz.getConstructor(int.class,String.class,int.class);
        System.out.println(constructor);

        System.out.println("=========================================================================");
        //获取所有方法（包含私有）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        System.out.println(Arrays.toString(declaredMethods));
        //根据方法名获取方法并执行
        Method password = clazz.getDeclaredMethod("password");
        //设置属性访问
        password.setAccessible(true);
        //执行方法，参数是返回值类型
        Object invoke = password.invoke(int.class);
        System.out.println(invoke);
        //获取所有非私有方法
        Method[] methods = clazz.getMethods();
        System.out.println(Arrays.toString(methods));
        //获取某个非私有方法
        Method add = clazz.getMethod("add", int.class);
        System.out.println(add);
        System.out.println("=========================================================================");
        //获取所有变量（包含私有）
        Field[] declaredFields = clazz.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));
        //获取某个变量（包含私有）
        Field price = clazz.getDeclaredField("price");
        System.out.println(price);
        //获取非私有字段
        Field[] fields = clazz.getFields();
        System.out.println(Arrays.toString(fields));
        //获取某个非私有字段
        Field name = clazz.getField("AA");
        System.out.println(name);
    }
    public static void getInternalClass() throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException {
        //获取外部类对象
        Class<InnerContainer> clazz = InnerContainer.class;
        //实例化成员内部类时需要外部类对象
        InnerContainer innerContainer = clazz.newInstance();
        Class<?>[] declaredClasses = clazz.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            /*
                int类型表示该类修饰符
                    PUBLIC: 1
                    PRIVATE: 2
                    PROTECTED: 4
                    STATIC: 8
                    FINAL: 16
                    SYNCHRONIZED: 32
                    VOLATILE: 64
                    TRANSIENT: 128
                    NATIVE: 256
                    INTERFACE: 512
                    ABSTRACT: 1024
                    STRICT: 2048
             */
            int modifiers = declaredClass.getModifiers();
            //获取修饰符
            String str = Modifier.toString(modifiers);
            if(str.contains("static")){
                Constructor<?> declaredConstructor = declaredClass.getDeclaredConstructor();
                System.out.println(declaredConstructor);
            }else{
                //获取成员内部类，需要将外部类对象作为参数穿进去
                Constructor<?> declaredConstructor = declaredClass.getDeclaredConstructor(clazz);
                System.out.println(declaredConstructor);
            }

        }

    }
}
