package by.yarik.currency.util;

import android.util.Log;

import java.util.Arrays;

public class StringUtils {

    public static final String TAG = "StringUtils_logs";

    public static String getBelSumm(double summ) {
        String oneCoin = " капейка";
        String oneFewCoin = " капейкі";
        String oneManyCoin = " капеек";

        String one = " рубель, ";
        String oneFew = " рубля, ";
        String oneMany = " рублёў, ";

        String format = String.format("%.2f", summ);
        String[] elems = format.split(",");
        Log.d(TAG, "elems: " + Arrays.toString(elems));
        int rubel = Integer.parseInt(elems[0]);
        int rubelLast = Integer.parseInt(elems[0].substring(elems[0].length() - 1));
        int coin = Integer.parseInt(elems[1]);
        int coinLast = Integer.parseInt(elems[1].substring(elems[1].length() - 1));
        String res = "";
        if(rubel > 0) {
            if(rubelLast == 1) {
                res += rubel + one;
            } else if(rubelLast >= 2 && rubelLast <= 4) {
                res += rubel + oneFew;
            } else {
                res += rubel + oneMany;
            }
        }
        if(coinLast == 1) {
            res += coin + oneCoin;
        } else if(coinLast >= 2 && coinLast <= 4) {
            res += coin + oneFewCoin;
        } else {
            res += coin + oneManyCoin;
        }
        return res;
    }
}
