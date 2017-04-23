package com.nexuslink.util;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class RatingUtil {
    public static String getRating(int exp){
        int i = exp/100;
        switch (i){
            case 0:
                return "1Lv";
            case 1:
                return "2Lv";
            case 2:
                return "2Lv";
            case 3:
                return "3Lv";
            case 4:
                return "3Lv";
            case 5:
                return "3Lv";
            case 6:
                return "4Lv";
            case 7:
                return "4Lv";
            case 8:
                return "4Lv";
            case 9:
                return "4Lv";
            case 10:
                return "5Lv";
            case 11:
                return "5Lv";
            case 12:
                return "5Lv";
            case 13:
                return "5Lv";
            case 14:
                return "5Lv";

            case 15:
                return "6Lv";
            case 16:
                return "6Lv";
            case 17:
                return "6Lv";
            case 18:
                return "6Lv";
            case 19:
                return "6Lv";
            case 20:
                return "6Lv";

            case 21:
                return "7Lv";
            case 22:
                return "7Lv";
            case 23:
                return "7Lv";
            case 24:
                return "7Lv";
            case 25:
                return "7Lv";
            case 26:
                return "7Lv";
            case 27:
                return "7Lv";

            case 28:
                return "8Lv";
            case 29:
                return "8Lv";
            case 30:
                return "8Lv";
            case 31:
                return "8Lv";
            case 32:
                return "8Lv";
            case 33:
                return "8Lv";
            case 34:
                return "8Lv";
            case 35:
                return "8Lv";
        }
        return "0Lv";
    }
}
