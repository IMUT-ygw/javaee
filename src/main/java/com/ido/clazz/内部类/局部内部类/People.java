package com.ido.clazz.内部类.局部内部类;

public class People {
    private String name;
    public People() {
    }
    /*
    放在方法内部的类：局部内部类
     */
    public People getWoman(){
        class Woman extends People{   //局部内部类
            int age =0;
        }
        return new Woman();
    }
}
