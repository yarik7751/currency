package by.yarik.currency.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import by.yarik.currency.R;
import by.yarik.currency.ui.activity.MainActivity;

public class CustomDialogs {

    public static void dialogSelectCurr(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.ok), onClickListener)
                .setTitle(R.string.attention)
                .setMessage(R.string.chart_message)
                .create();
        dialog.show();
    }
}
