package org.gfg.dsa;

import java.util.*;

//https://www.geeksforgeeks.org/problems/k-largest-elements4206/1
public class Problem3 {

    /*
    Input: arr[] = [12, 5, 787, 1, 23], k = 2
    Output: [787, 23]
    Explanation: 1st largest element in the array is 787 and second largest is 23.
     */

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(12, 5, 787,787, 1, 23);
        Problem3 obj = new Problem3();
        System.out.println(obj.kLargest(list,2));
    }


    // O(nlogn)
    public List<Integer> kLargest(List<Integer> arr, int k) {
        // Your code here
        //Collections.sort(arr,(n1,n2)->n2-n1);
        Collections.sort(arr, Comparator.reverseOrder());
        List<Integer> result = new ArrayList<>();
        for(int i=0; i<k;i++){
            result.add(arr.get(i));
        }
        return result;

    }


    // O(nlogk)
    public List<Integer> kLargestWithHeap(List<Integer> arr, int k) {

        List<Integer> result = new ArrayList<>();

        return result;

    }
}
