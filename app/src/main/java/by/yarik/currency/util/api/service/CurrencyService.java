package by.yarik.currency.util.api.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.Currency;

public class CurrencyService extends IntentService {

    public static final String TAG = "CurrencyService";
    public static final String ACTION = "by.yarik.currency.util.api.service.CurrencyService";
    private static final String NAME_STREAM = "CurrencyService";

    public CurrencyService() {
        super(NAME_STREAM);
        Log.d(TAG, "NAME_STREAM: " + NAME_STREAM);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String response = Api.getAllCurrencies();
        if(Currency.getInstance() != null && response != null && response.equals(Api.CODE_SUCCESS + "")) {
            for(Currency currency : Currency.getInstance()) {
                currency.save();
            }
        }
        Log.d(TAG, "response: " + response);
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
