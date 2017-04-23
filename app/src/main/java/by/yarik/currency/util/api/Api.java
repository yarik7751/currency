package by.yarik.currency.util.api;

import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.yarik.currency.app.CurrencyApplication;
import by.yarik.currency.util.api.pojo.Currency;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String TAG = "Api_logs";
    private static final String BASE_URL = "http://www.nbrb.by/API/ExRates/";
    private static IApi iApi = null;
    public final static int CODE_SUCCESS = 200;

    private static IApi getIApiService() {
        if(iApi == null) {
            CookieJar cookieJar = new PersistentCookieJar(
                    new SetCookieCache(),
                    new SharedPrefsCookiePersistor(CurrencyApplication.getAppContext())
            );
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            iApi = retrofit.create(IApi.class);
        }
        return iApi;
    }

    public static String getAllCurrencies() {
        Call<List<Currency>> call = getIApiService().getAllCurrencies();
        Response<List<Currency>> response = null;
        String result = "";
        try {
            response = call.execute();
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Api getAllCurrencies: " + e);
        }
        if(response != null) {
            if(response.code() == CODE_SUCCESS) {
                Currency.setInstance(response.body());
            }
            result = response.code() + "";
        }
        return result;
    }
}
