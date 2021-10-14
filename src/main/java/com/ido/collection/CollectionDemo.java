package com.ido.collection;

import java.util.*;

/**
 * ArrayList：查询快 增删慢  （get(i)） 底层是数组
 * LinkedList ： 查询慢  增删快
 *
 */
public class CollectionDemo {
    public static void main(String[] args) {
//        /**
//         * list
//         */
//
//        ArrayList<String> strings = new ArrayList<>();
//        LinkedList<String> strings2 = new LinkedList<>();
//        Vector<String> strings3 = new Vector<>();
//
//
//        /**
//         * set
//         */
//        HashSet<String> strings1 = new HashSet<>();
//        TreeSet<String> strings4 = new TreeSet<>();
//
//
//        /**
//         * queue
//         */
//        Collections.sort(strings, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return 0;
//            }
//        });
//
//

        String str = "11223344";
        char[] chars = str.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char temp = chars[i];
            if(map.get(temp) == null){
                map.put(temp,1);
                continue;
            }
            Integer value = map.get(temp);
            value++;
            map.put(temp,value);
        }
        for (Map.Entry<Character, Integer> characterIntegerEntry : map.entrySet()) {
            System.out.println(characterIntegerEntry.getKey() +":" + characterIntegerEntry.getValue());
        }
    }
}
