package com.spring.boot.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/8 15:55
 */
public class NumberUtil {

    public static List<Integer> splitNumber(Integer number) {
        List<Integer> all = new ArrayList();
        int temp = 1;
        while(temp <= number){
            all.add(temp);
            temp = temp << 1;
        }
        List<Integer> result = new ArrayList<>();
        for(Integer num : all){
            if((number&num) != 0){
                result.add(num);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        List<Integer> list = splitNumber(8);
//        list.forEach(System.out::println);
//    }

}
