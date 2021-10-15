package com.ido.clazz;

public class Test {

    public static final String AA = "123";
    private int age;
    private String name;
    private int price;
    public String user;

    public Test(int age, String name, int price) {
        this.age = age;
        this.name = name;
        this.price = price;
    }

    public Test() {
    }

    private Test(String name){
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Test{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public void add(){
        System.out.println("add");
    }
    public void add(int num){
        System.out.println("add" + num);
    }

    private void app(){
        System.out.println("app");
    }

    private void app(int num){
        System.out.println("app" + num);
    }

    private static int password(){
        return 123;
    }

}
