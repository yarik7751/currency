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

import java.sql.SQLException;

import butterknife.BindView;
import by.yarik.currency.R;
import by.yarik.currency.ui.activity.base.BaseActivity;
import by.yarik.currency.ui.fragment.ChartFragment;
import by.yarik.currency.ui.fragment.CurrencyFragment;
import by.yarik.currency.ui.fragment.InfoFragment;
import by.yarik.currency.util.AndroidUtils;
import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.service.CurrencyService;
import by.yarik.currency.util.db.HelperFactory;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity_logs";

    @BindView(R.id.bnv_menu) BottomNavigationView bnvMenu;

    private GetCurrencyBroadcastReceiver getCurrencyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgressDialogMessage(getString(R.string.loading_currencies));

        long count = 0;
        try {
            count = HelperFactory.getHelper().getCurrencyDAO().countOf();
        } catch (SQLException e) {
            count = 0;
            e.printStackTrace();
        }
        Log.d(TAG, "Currency.count: " + count);
        if(count <= 0) {
            startService(new Intent(this, CurrencyService.class));
            showProgressDialog();
        } else {
            setCurrencyFragment();
        }

        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                AndroidUtils.hideKeyboard(MainActivity.this);
                switch (item.getItemId()) {
                    case R.id.action_currency:
                        setCurrencyFragment();
                        break;

                    case R.id.action_chart:
                        onSwitchFragment(ChartFragment.getInstance(), ChartFragment.class.getName(), false, true, R.id.container);
                        break;

                    case R.id.action_info:
                        onSwitchFragment(InfoFragment.getInstance(), InfoFragment.class.getName(), false, true, R.id.container);
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

    public void setCurrencyFragment() {
        bnvMenu.getMenu().getItem(0).setChecked(true);
        onSwitchFragment(CurrencyFragment.getInstance(), CurrencyFragment.class.getName(), false, true, R.id.container);
    }

    public class GetCurrencyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(Currency.getInstance() == null && response == null && !response.equals(Api.CODE_SUCCESS + "")) {
                showMessage(R.string.query_no_answer);
            } else {
                setCurrencyFragment();
            }
            hideProgressDialog();
        }
    }
}
