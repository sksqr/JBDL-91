package org.gfg.dsa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//https://practice.geeksforgeeks.org/problems/first-repeating-element4018/1/
public class Problem1 {

    public  Integer firstRepeated(List<Integer> integerList) {
        Map<Integer,Integer> map = new HashMap<>();
        Integer result = Integer.MAX_VALUE;
        for(int i=0; i<integerList.size(); i++){
            if(map.containsKey(integerList.get(i))){
                if(map.get(integerList.get(i))  < result){
                    result = map.get(integerList.get(i));
                }
            }
            else{
                map.put(integerList.get(i),i);
            }
        }
        if(result == Integer.MAX_VALUE){
            return -1;
        }
        return result;

    }
}
