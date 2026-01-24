package org.collections;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        map.put("IND",5);

        map.put("AUS",50);

        map.put("ENG",56);

        map.put("IND",97);

        map.put("IND",null);

        map.put(null,78);

        map.put("ENG",null);

        map.put(null,88);

        System.out.println(map);


    }
}
