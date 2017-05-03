package by.yarik.currency.ui.fragment;

import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.currency.R;
import by.yarik.currency.ui.CustomDialogs;
import by.yarik.currency.ui.activity.MainActivity;
import by.yarik.currency.ui.adapter.page.SelectedCurrenciesPageAdapter;
import by.yarik.currency.ui.fragment.base.BaseFragment;
import by.yarik.currency.util.CustomSharedPreference;
import by.yarik.currency.util.StringUtils;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.pojo.CurrencyRate;
import by.yarik.currency.util.db.HelperFactory;

public class InfoFragment extends BaseFragment {

    public static final String TAG = "InfoFragment_logs";

    @BindView(R.id.vp_selected_currencies) ViewPager vpSelectedCurrencies;
    @BindView(R.id.img_left) ImageView imgLeft;
    @BindView(R.id.img_right) ImageView imgRight;
    @BindView(R.id.ll_main) LinearLayout llMain;
    @BindView(R.id.et_summ) EditText etSumm;
    @BindView(R.id.tv_abreviation) TextView tvAbbreviation;
    @BindView(R.id.tv_result) TextView tvResult;
    @BindView(R.id.cv_result) CardView cvResult;

    private List<Currency> currencies;
    private Unregistrar unregistrar;

    private static InfoFragment infoFragment;
    public static InfoFragment getInstance() {
        if(infoFragment == null) {
            infoFragment = new InfoFragment();
        }
        return infoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, null);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(currencies != null && currencies.size() > 0) {
            etSumm.setText("");
            cvResult.setVisibility(View.GONE);
            setAbbreviation();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!setSelectedCurrencies()) {
            return;
        }
        setAbbreviation();
        vpSelectedCurrencies.setAdapter(new SelectedCurrenciesPageAdapter(getViewPageInfo()));
        vpSelectedCurrencies.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCREEN_STATE_OFF) {
                    setAbbreviation();
                    showResult();
                }
            }
        });

        etSumm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showResult();
                if(TextUtils.isEmpty(s)) {
                    cvResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        unregistrar = KeyboardVisibilityEvent.registerEventListener(getActivity(), new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(!isOpen && !TextUtils.isEmpty(etSumm.getText().toString())) {
                    Log.d(TAG, "keyboard close");
                    showResult();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unregistrar != null) {
            unregistrar.unregister();
        }
    }

    @OnClick(R.id.img_right)
    public void clickImgRight() {
        vpSelectedCurrencies.setCurrentItem(vpSelectedCurrencies.getCurrentItem() + 1);
    }

    @OnClick(R.id.img_left)
    public void clickImgLeft() {
        vpSelectedCurrencies.setCurrentItem(vpSelectedCurrencies.getCurrentItem() - 1);
    }

    private void showResult() {
        if(TextUtils.isEmpty(etSumm.getText().toString())) {
            return;
        }
        double summ = Double.parseDouble(etSumm.getText().toString());
        CurrencyRate currency = null;
        for(CurrencyRate currencyRate : CurrencyRate.getInstance()) {
            if(currencyRate.getCurId().doubleValue() == currencies.get(vpSelectedCurrencies.getCurrentItem()).getCurId().doubleValue()) {
                currency = currencyRate;
            }
        }
        if(currency != null) {
            double result = summ * currency.getCurOfficialRate() / currency.getCurScale();
            tvResult.setText(StringUtils.getBelSumm(result));
            cvResult.setVisibility(View.VISIBLE);
        }
    }

    private void setAbbreviation() {
        String abbreviation = currencies.get(vpSelectedCurrencies.getCurrentItem()).getCurAbbreviation();
        tvAbbreviation.setText(abbreviation);
    }

    private boolean setSelectedCurrencies() {
        currencies = new ArrayList<>();
        String selectedCurrencies = CustomSharedPreference.getSelectCurrencies(getContext());
        String[] elems = selectedCurrencies.split(";");
        Log.d(TAG, "getSelectCurrencies.length: " + selectedCurrencies.length());
        Log.d(TAG, "getSelectCurrencies: " + selectedCurrencies);
        Log.d(TAG, "elems.length: " + elems.length);
        Log.d(TAG, "elems: " + Arrays.toString(elems));
        if(!TextUtils.isEmpty(selectedCurrencies)) {
            for (String elem : elems) {
                List<Currency> currencies = HelperFactory.getHelper().getCurrencyDAO().getCurrencyByAbbreviation(elem);
                this.currencies.add(currencies.get(currencies.size() - 1));
            }
            Log.d(TAG, "currencies.size(): " + currencies.size());
            return true;
        } else {
            llMain.setVisibility(View.GONE);
            CustomDialogs.dialogSelectCurr(getContext(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MainActivity) getActivity()).setCurrencyFragment();
                }
            });
            return false;
        }
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
}
