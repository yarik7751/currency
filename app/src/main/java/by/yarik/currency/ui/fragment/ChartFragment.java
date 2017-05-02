package by.yarik.currency.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.currency.R;
import by.yarik.currency.ui.adapter.page.SelectedCurrenciesPageAdapter;
import by.yarik.currency.ui.fragment.base.BaseFragment;
import by.yarik.currency.util.Const;
import by.yarik.currency.util.CustomSharedPreference;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.pojo.CurrencyRate;
import by.yarik.currency.util.api.service.DynamicService;
import by.yarik.currency.util.db.HelperFactory;

public class ChartFragment extends BaseFragment {

    public static final String TAG = "ChartFragment_logs";

    @BindView(R.id.vp_selected_currencies) ViewPager vpSelectedCurrencies;
    @BindView(R.id.img_left) ImageView imgLeft;
    @BindView(R.id.img_right) ImageView imgRight;

    private List<Currency> currencies;

    private DynamicBroadcastReceiver dynamicBroadcastReceiver;

    private static ChartFragment chartFragment;
    public static ChartFragment getInstance() {
        if(chartFragment == null) {
            chartFragment = new ChartFragment();
        }
        return chartFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencies = new ArrayList<>();
        String[] elems = CustomSharedPreference.getSelectCurrencies(getContext()).split(";");
        Log.d(TAG, "elems.length: " + elems.length);
        for(String elem : elems) {
            List<Currency> currencies = HelperFactory.getHelper().getCurrencyDAO().getCurrencyByAbbreviation(elem);
            this.currencies.add(currencies.get(currencies.size() - 1));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(DynamicService.ACTION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(dynamicBroadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vpSelectedCurrencies.setAdapter(new SelectedCurrenciesPageAdapter(getViewPageInfo()));
        vpSelectedCurrencies.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCREEN_STATE_OFF) {
                    requestDynamics();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(dynamicBroadcastReceiver);
    }

    @OnClick(R.id.img_right)
    public void clickImgRight() {
        vpSelectedCurrencies.setCurrentItem(vpSelectedCurrencies.getCurrentItem() + 1);
    }

    @OnClick(R.id.img_left)
    public void clickImgLeft() {
        vpSelectedCurrencies.setCurrentItem(vpSelectedCurrencies.getCurrentItem() - 1);
    }

    public void requestDynamics() {
        String curId = currencies.get(vpSelectedCurrencies.getCurrentItem()).getCurId().toString();
        Log.d(TAG, "curId: " + curId);
        Intent intent = new Intent(getContext(), DynamicService.class);
        intent.putExtra(DynamicService.CURR_ID, curId);
        getActivity().startService(intent);
        setProgressDialogMessage(getString(R.string.wait) + " ...");
        showProgressDialog();
    }

    private List<View> getViewPageInfo() {
        List<View> info = new ArrayList<>();
        for(Currency currency : currencies) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_select_currency, null);
            ((TextView) v.findViewById(R.id.tv_curr_info)).setText(currency.getCurAbbreviation() + " " + currency.getCurNameBel());
            info.add(v);
        }
        return info;
    }

    public class DynamicBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(CurrencyRate.getInstanceDynamic() != null && response != null && response.equals(Api.CODE_SUCCESS + "")) {
                Log.d(TAG, "dynamic rates: ");
                Log.d(TAG, CurrencyRate.getInstanceDynamic().toString());
            } else {
                showMessage(R.string.query_no_answer);
            }
            hideProgressDialog();
        }
    }
}
