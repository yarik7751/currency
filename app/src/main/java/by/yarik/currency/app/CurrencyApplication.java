package by.yarik.currency.app;

import android.app.Application;
import android.content.Context;

import by.yarik.currency.util.db.HelperFactory;

public class CurrencyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        HelperFactory.setHelper(context);
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }

    public static Context getAppContext() {
        return CurrencyApplication.context;
    }
}
