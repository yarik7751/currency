package by.yarik.currency.util.api.service;

import android.app.IntentService;
import android.content.Intent;

import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;

public class DynamicService extends IntentService {

    public static final String TAG = "CurrencyRateService";
    public static final String ACTION = "by.yarik.currency.util.api.service.DynamicService";
    public static final String CURR_ID = "CURR_ID";
    private static final String NAME_STREAM = "CurrencyRateService";

    public DynamicService() {
        super(NAME_STREAM);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String response = Api.getDynamic(intent.getStringExtra(CURR_ID));
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(Const.RESPONSE, response);
        sendBroadcast(responseIntent);
    }
}
