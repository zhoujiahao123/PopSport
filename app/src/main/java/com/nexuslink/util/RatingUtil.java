package com.nexuslink.util;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class RatingUtil {
    public static String getRating(int exp){
        int i = exp/100;
        switch (i){
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "3";
            case 5:
                return "3";
            case 6:
                return "4";
            case 7:
                return "4";
            case 8:
                return "4";
            case 9:
                return "4";
            case 10:
                return "5";
            case 11:
                return "5";
            case 12:
                return "5";
            case 13:
                return "5";
            case 14:
                return "5";

            case 15:
                return "6";
            case 16:
                return "6";
            case 17:
                return "6";
            case 18:
                return "6";
            case 19:
                return "6";
            case 20:
                return "6";

            case 21:
                return "7";
            case 22:
                return "7";
            case 23:
                return "7";
            case 24:
                return "7";
            case 25:
                return "7";
            case 26:
                return "7";
            case 27:
                return "7";

            case 28:
                return "8";
            case 29:
                return "8";
            case 30:
                return "8";
            case 31:
                return "8";
            case 32:
                return "8";
            case 33:
                return "8";
            case 34:
                return "8";
            case 35:
                return "8";
        }
        return "0Lv";
    }
}
