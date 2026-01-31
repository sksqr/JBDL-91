package org.gfg.dsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//https://www.geeksforgeeks.org/dsa/check-for-balanced-parentheses-in-an-expression/
public class Problem2 {

    public static void main(String[] args) {
        Problem2 obj = new Problem2();
        System.out.println(obj.isBalanced("([])"));
    }

    public boolean isBalanced(String s) {

        Map<Character,Character> map = new HashMap<>();
        map.put('(',')');
        map.put('[',']');
        map.put('{','}');
        Stack<Character> stack = new Stack<>();
        int len = s.length();
        for(int i=0; i<len; i++){
            if(map.containsKey(s.charAt(i))){
                stack.push(s.charAt(i));
            }
            else{
                if(stack.isEmpty()){
                    return false;
                }
                Character top = stack.pop(); // "()" "(]"
                if(!map.get(top).equals(s.charAt(i))){
                    return false;
                }
            }
        }
        if(stack.isEmpty()){
            return true;
        }
        return false;

    }
}
