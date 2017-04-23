package by.yarik.currency.app;

import android.app.Application;
import android.content.Context;

import com.orm.SugarApp;
import com.orm.SugarContext;

public class CurrencyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SugarContext.init(context);
    }

    public static Context getAppContext() {
        return CurrencyApplication.context;
    }
}
