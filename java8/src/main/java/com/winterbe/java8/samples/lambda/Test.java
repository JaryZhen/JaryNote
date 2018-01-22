package com.winterbe.java8.samples.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jary on 2018/1/17 0017.
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("peter", "anna", "mike", "xenia");

        for (Object n : list) {
            System.out.println(n);
        }

        list.forEach(n -> System.out.println(n));

        list.forEach(System.out::println);

        /*
        *   Math::max��Ч��(a, b)->Math.max(a, b)
            String::startWith��Ч��(s1, s2)->s1.startWith(s2)
            s::isEmpty��Ч��()->s.isEmpty()
            */


    }
}
