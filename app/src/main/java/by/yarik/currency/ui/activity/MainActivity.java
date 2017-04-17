package by.yarik.currency.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import butterknife.BindView;
import by.yarik.currency.R;
import by.yarik.currency.ui.activity.base.BaseActivity;
import by.yarik.currency.ui.fragment.ChartFragment;
import by.yarik.currency.ui.fragment.CurrencyFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bnv_menu) BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                return false;
            }
        });
    }
}
