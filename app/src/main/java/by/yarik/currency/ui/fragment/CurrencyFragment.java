package by.yarik.currency.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import by.yarik.currency.R;
import by.yarik.currency.ui.activity.MainActivity;
import by.yarik.currency.ui.adapter.currency.CurrencyRecyclerViewAdapter;
import by.yarik.currency.ui.adapter.currency.OnSharedPreferencesChange;
import by.yarik.currency.ui.fragment.base.BaseFragment;
import by.yarik.currency.util.Const;
import by.yarik.currency.util.api.Api;
import by.yarik.currency.util.api.pojo.CurrencyRate;
import by.yarik.currency.util.api.service.CurrencyRateService;
import by.yarik.currency.util.api.service.CurrencyService;

public class CurrencyFragment extends BaseFragment {

    @BindView(R.id.rv_all_currencies) RecyclerView rvAllCurrencies;
    @BindView(R.id.rv_selected_currencies) RecyclerView rvSelectedCurrencies;

    private GetCurrencyRateBroadcastReceiver getCurrencyRateBroadcastReceiver;
    private OnSharedPreferencesChange onSharedPreferencesChange = new OnSharedPreferencesChange() {
        @Override
        public void onChange() {
            setData();
        }
    };

    private static CurrencyFragment currencyFragment;
    public static CurrencyFragment getInstance() {
        if(currencyFragment == null) {
            currencyFragment = new CurrencyFragment();
        }
        return currencyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReceivers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_currency, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAllCurrencies.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvSelectedCurrencies.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvSelectedCurrencies.setNestedScrollingEnabled(false);
        rvAllCurrencies.setNestedScrollingEnabled(false);
        requestCurrenciesRate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    private void initReceivers() {
        getCurrencyRateBroadcastReceiver = new GetCurrencyRateBroadcastReceiver();
        IntentFilter intentFilterCurrencyRateBroadcastReceiver = new IntentFilter(CurrencyRateService.ACTION);
        intentFilterCurrencyRateBroadcastReceiver.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(getCurrencyRateBroadcastReceiver, intentFilterCurrencyRateBroadcastReceiver);
    }

    private void unregisterReceivers() {
        getActivity().unregisterReceiver(getCurrencyRateBroadcastReceiver);
    }

    private void setData() {
        rvAllCurrencies.setAdapter(new CurrencyRecyclerViewAdapter(
                getContext(),
                CurrencyRate.getInstance(),
                CurrencyRecyclerViewAdapter.ALL_CURRENCY,
                onSharedPreferencesChange));
        rvSelectedCurrencies.setAdapter(new CurrencyRecyclerViewAdapter(
                getContext(),
                CurrencyRate.getInstance(),
                CurrencyRecyclerViewAdapter.SELECTED_CURRENCY,
                onSharedPreferencesChange));
    }

    private void requestCurrenciesRate() {
        Intent intent = new Intent(getContext(), CurrencyRateService.class);
        intent.putExtra(CurrencyRateService.PERIODICITY, 0);
        intent.putExtra(CurrencyRateService.PARAM_MODE, 0);
        getActivity().startService(intent);
        setProgressDialogMessage(getString(R.string.get_now_rate));
        showProgressDialog();
    }

    public class GetCurrencyRateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(CurrencyRate.getInstance() != null && response != null && response.equals(Api.CODE_SUCCESS + "")) {
                setData();
            } else {
                showMessage(R.string.query_no_answer);
            }
            hideProgressDialog();
        }
    }
}
