package com.guagua.chapter1;

/**
 * @author guagua
 * @date 2022/10/15 23:02
 * @describe 第1章 整数
 */
public class IntAlgorithm {

    public static void main(String[] args) {
//        int divide = divide(10, -1);
//        System.out.println(divide);

//        String result = addBinary("11", "10");
//        System.out.println(result);

//        int[] ints = countBits(4);
//        for (int i : ints) {
//            System.out.println(i);
//        }

//        int[] ints2 = countBits2(4);
//        for (int i : ints2) {
//            System.out.println(i);
//        }

//        int[] ints3 = countBits3(4);
//        for (int i : ints3) {
//            System.out.println(i);
//        }

//        int i = singleNumber(new int[]{0, 1, 0, 1, 0, 1, 100});
//        System.out.println(i);

//        int i = maxProduct(new String[]{"abcw", "foo", "bar", "fxyz", "abcdef"});
//        System.out.println(i);

        int i = maxProduct2(new String[]{"abcw", "foo", "bar", "fxyz", "abcdef"});
        System.out.println(i);
    }


    /**
     * 面试题1：整数除法
     * 0x80000000：-2^31
     * 0xc0000000：-2^30
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide(int dividend, int divisor) {
        if (dividend == 0x80000000 && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        int negative = 2;
        if (dividend > 0) {
            negative--;
            dividend = -dividend;
        }

        if (divisor > 0) {
            negative--;
            divisor = -divisor;
        }
        int result = divideCore(dividend, divisor);
        return negative == 1 ? -result : result;
    }

    public static int divideCore(int dividend, int divisor) {
        int result = 0;
        while (dividend <= divisor) {
            int value = divisor;
            int quotient = 1;
            while (value >= 0xc0000000 && dividend <= value + value) {
                quotient += quotient;
                value += value;
            }
            result += quotient;
            dividend -= value;
        }
        return result;
    }


    /**
     * 面试题2：二进制加法
     *
     * @param a
     * @param b
     */
    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            int digitA = i >= 0 ? a.charAt(i--) - '0' : 0;
            int digitB = j >= 0 ? b.charAt(j--) - '0' : 0;
            int sum = digitA + digitB + carry;
            carry = sum >= 2 ? 1 : 0;
            sum = sum >= 2 ? sum - 2 : sum;
            result.append(sum);
        }
        if (carry == 1) {
            result.append(1);
        }
        return result.reverse().toString();
    }

    /**
     * 面试题3 方式1：前n个数字二进制形式中1的个数
     *
     * @param num
     * @return
     */
    public static int[] countBits(int num) {
        int[] result = new int[num + 1];
        for (int i = 0; i <= num; ++i) {
            int j = i;
            while (j != 0) {
                result[i]++;
                j = j & (j - 1);
            }
        }
        return result;
    }

    /**
     * 面试题3 方式2：前n个数字二进制形式中1的个数
     *
     * @param num
     * @return
     */
    public static int[] countBits2(int num) {
        int[] result = new int[num + 1];
        for (int i = 1; i <= num; ++i) {
            result[i] = result[i & (i - 1)] + 1;
        }
        return result;
    }

    /**
     * 面试题3 方式3：前n个数字二进制形式中1的个数
     *
     * @param num
     * @return
     */
    public static int[] countBits3(int num) {
        int[] result = new int[num + 1];
        for (int i = 1; i <= num; ++i) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

    /**
     * 面试题4：只出现一次的数字
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        int[] bitSums = new int[32];
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                bitSums[i] += (num >> 31 - i) & 1;
            }
        }
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) + bitSums[i] % 3;
        }
        return result;
    }

    /**
     * 面试题5 方式1：单词长度的最大乘积
     *
     * @param words
     * @return
     */
    public static int maxProduct(String[] words) {
        boolean[][] flags = new boolean[words.length][26];
        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                flags[i][c - 'a'] = true;
            }
        }
        int result = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                int k = 0;
                for (; k < 26; k++) {
                    if (flags[i][k] && flags[j][k]) {
                        break;
                    }
                }
                if (k == 26) {
                    int prod = words[i].length() * words[j].length();
                    result = Math.max(result, prod);
                }
            }
        }

        return result;
    }


    /**
     * 面试题5 方式2：单词长度的最大乘积
     *
     * @param words
     * @return
     */
    public static int maxProduct2(String[] words) {
        int[] flags = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            for (char ch : words[i].toCharArray()) {
                flags[i] |= 1 << (ch - 'a');
            }
        }

        int result = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((flags[i] & flags[j]) == 0) {
                    int prod = words[i].length() * words[j].length();
                    result = Math.max(result, prod);
                }
            }
        }
        return result;
    }
}
