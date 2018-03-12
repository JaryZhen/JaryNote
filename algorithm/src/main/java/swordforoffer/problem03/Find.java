package swordforoffer.problem03;

import java.util.Scanner;

/*
 * 面试题3：在一个二维数组中，每一行的数从左到右递增，每一列的数从上到下递增。
 * 输入这样的一个数组和一个整数，判断数组中是否含有该整数。
 * 思路：别从左到右一个一个比，先比右上角的或左下角的，如果要找的数比这个数小，
 * 剔除这一列，比较前一列的第一个数。如果大，剔除这一行，再比较该列下一个数。
 * 注意如果先比左上角或右下角的是不行的。
 */
public class Find {
    public static void main(String[] args) {
        int[][] array = {
                {1, 2, 8, 9},
                {2, 4, 9, 12},
                {4, 9, 10, 13},
                {4, 10, 11, 15}};
        int ta = 11;
        System.out.println(find(array, ta));
        System.out.println("gsdfgs dsadfghdfh东方红东方");
    }


    static boolean find(int[][] array, int target) {
        int row = 0;
        int col = array[0].length - 1;

        while (row < array.length && col >= 0) {
            if (array[row][col] == target) {
                return true;
            } else if (array[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }
}
