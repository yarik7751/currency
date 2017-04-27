package by.yarik.currency.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import by.yarik.currency.util.api.service.CurrencyRateService;

public class LoadRatesService extends Service {

    public static final String TAG = "LoadRatesService_Logs";
    private final static int INTERVAL = 5 * 60 * 1000;

    private static Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestCurrenciesRate();
            }
        }, 0, INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void requestCurrenciesRate() {
        Log.d(TAG, "requestCurrenciesRate()");
        Intent intent = new Intent(this, CurrencyRateService.class);
        intent.putExtra(CurrencyRateService.PERIODICITY, 0);
        intent.putExtra(CurrencyRateService.PARAM_MODE, 0);
        startService(intent);
    }
}
