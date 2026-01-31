package org.gfg.dsa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//https://www.geeksforgeeks.org/problems/key-pair5616/1
public class Problem5 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(0, -1, 2, -3, 1);
        System.out.println(twoSum(list,-20));
        System.out.println(twoSum(list,0));
    }


    //O(n)
     static boolean  twoSum(List<Integer> list, int target) {
        // code here
         Set<Integer> set = new HashSet<>();
         for(Integer a: list){
             Integer b = target-a;
             if(set.contains(b)){
                 return true;
             }
             else{
                 set.add(a);
             }
         }
         return false;
    }
}
