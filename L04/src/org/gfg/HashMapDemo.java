package org.gfg;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    public static void main(String[] args) {


        Map<Integer,String> map = new HashMap<>();



        map.put(1,"Rahul"); // O(1)  --> O(k) or O(logk)

        map.put(2,"Ajay");

        map.put(3,"Vijay");


        System.out.println(map.get(2)); // O(1)  --> O(k) or O(logk)




    }
}
