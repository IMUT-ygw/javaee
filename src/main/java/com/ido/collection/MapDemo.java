package com.ido.collection;

import java.util.*;

/**
 * hashtable,hashmap区别：
 *      1、table不能存null（为什么不能为null,因为底层key调用了hashcode和equals），map可以存
 *      2、添加key-value的时候，哈希算法不同，map用的是自定义算法，table用的是key的hashCode
 *      3、 他俩继承的父类不一样
 *      4、map初始容量16 table是11 ，负载因子都是0.75
 *      5、扩容机制不同：map当前已用 > 总容量 * 0.75 扩容一倍。table 当前容量翻倍 + 1
 *      6、遍历方式：map只支持entrySet迭代器，table有两种还有一种是elements
 *      7、线程安全：map不安全   table安全
 *  hashmap : JDK8以前是头插法，JDK8后是尾插法
 */
public class MapDemo {
    public static void main(String[] args) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("a",100);
        map.put("b",200);
        map.put("c",300);
        map.put("d",400);
        map.put(null,500);

        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put(null,1);

        Integer integer = 127;


        /**
         * 遍历1
         */
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            System.out.println(next);
        }

        /**
         * 遍历2
         */
        for (Map.Entry<String, Object> entry:hashtable.entrySet()) {
            System.out.println(entry.getKey() +entry.getValue());
        }

        /**
         * 遍历3 hashtable
         */
        Enumeration<Object> elements = hashtable.elements();
        while (elements.hasMoreElements()){
            System.out.println(elements.nextElement());
        }

        HashMap<User,String> map1 = new HashMap<>();
        map1.put(new User(),"1");
        System.out.println(map1);
    }
}
