package by.yarik.currency.util.api.service;


import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;

public class CurrencyRateService extends IntentService {

    public static final String TAG = "CurrencyRateService";
    public static final String ACTION = "by.yarik.currency.util.api.service.CurrencyRateService";
    public static final String ON_DATE = "ON_DATE";
    public static final String PERIODICITY = "PERIODICITY";
    public static final String PARAM_MODE = "PARAM_MODE";
    private static final String NAME_STREAM = "CurrencyRateService";

    public CurrencyRateService() {
        super(NAME_STREAM);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String response = "";
        String onDate = intent.getStringExtra(ON_DATE);
        int periodicity = intent.getIntExtra(PERIODICITY, -1);
        int paramMode = intent.getIntExtra(PARAM_MODE, -1);
        if(TextUtils.isEmpty(onDate)) {
            response = Api.getRates(periodicity, paramMode);
        } else {
            response = Api.getRates(onDate, periodicity, paramMode);
        }
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
