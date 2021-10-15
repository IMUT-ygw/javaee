package com.ido.clazz.内部类.匿名内部类;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        //匿名内部类，不去单独实现一个类
        Person person = new Person() {
            @Override
            public void eat() {
                System.out.println("eat something");
            }
        };
    }
}
abstract class Person {
    public abstract void eat();
}

/**
 * 这种情况没必要去实现一个单独类
 */
class Child extends Person {
    public void eat() {
        System.out.println("eat something");
    }
}

