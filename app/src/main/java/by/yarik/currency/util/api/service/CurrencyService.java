package by.yarik.currency.util.api.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;

import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.db.HelperFactory;

public class CurrencyService extends IntentService {

    public static final String TAG = "CurrencyService_logs";
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
                try {
                    HelperFactory.getHelper().getCurrencyDAO().create(currency);
                } catch (SQLException e) {
                    Log.d(TAG, "create currency ERROR" + e);
                    e.printStackTrace();
                }
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
