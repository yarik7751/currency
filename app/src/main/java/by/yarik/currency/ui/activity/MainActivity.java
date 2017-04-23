package by.yarik.currency.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.orm.SugarDb;

import butterknife.BindView;
import by.yarik.currency.R;
import by.yarik.currency.ui.activity.base.BaseActivity;
import by.yarik.currency.ui.fragment.ChartFragment;
import by.yarik.currency.ui.fragment.CurrencyFragment;
import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.service.CurrencyService;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity_logs";

    @BindView(R.id.bnv_menu) BottomNavigationView bnvMenu;

    private GetCurrencyBroadcastReceiver getCurrencyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgressDialogMessage(getString(R.string.loading_currencies));

        Log.d(TAG, "Currency.count: " + Currency.count(Currency.class));
        if(Currency.count(Currency.class) == 0) {
            startService(new Intent(this, CurrencyService.class));
            showProgressDialog();
        }

        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_currency:
                        onSwitchFragment(CurrencyFragment.getInstance(), CurrencyFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_chart:
                        onSwitchFragment(ChartFragment.getInstance(), ChartFragment.class.getName(), false, true, R.id.container);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initReceivers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceivers();
    }

    private void initReceivers() {
        getCurrencyBroadcastReceiver = new GetCurrencyBroadcastReceiver();
        IntentFilter intentFilterCurrencyBroadcastReceiver = new IntentFilter(CurrencyService.ACTION);
        intentFilterCurrencyBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(getCurrencyBroadcastReceiver, intentFilterCurrencyBroadcastReceiver);
    }

    private void unregisterReceivers() {
        unregisterReceiver(getCurrencyBroadcastReceiver);
    }

    public class GetCurrencyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(Currency.getInstance() == null && response == null && !response.equals(Api.CODE_SUCCESS + "")) {
                showMessage(R.string.query_no_answer);
            }
            hideProgressDialog();
        }
    }
}
