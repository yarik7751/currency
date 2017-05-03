package by.yarik.currency.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import by.yarik.currency.R;
import by.yarik.currency.ui.CustomDialogs;
import by.yarik.currency.ui.activity.MainActivity;
import by.yarik.currency.ui.adapter.page.SelectedCurrenciesPageAdapter;
import by.yarik.currency.ui.fragment.base.BaseFragment;
import by.yarik.currency.util.Const;
import by.yarik.currency.util.CustomSharedPreference;
import by.yarik.currency.util.DateUtils;
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
    @BindView(R.id.lc_chart) LineChart lcChart;
    @BindView(R.id.ll_main) LinearLayout llMain;
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

        if(!setSelectedCurrencies()) {
            return;
        }

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
        setChartSettings();
        requestDynamics();
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

    private void setDataOnChart() {
        List<Entry> entries = new ArrayList<Entry>();
        List<CurrencyRate> currencyDynamic = CurrencyRate.getInstanceDynamic();
        for(int i = 0; i < currencyDynamic.size(); i++) {
            entries.add(new Entry(DateUtils.stringToUnix(currencyDynamic.get(i).getDate()), currencyDynamic.get(i).getCurOfficialRate().floatValue()));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setColor(getResources().getColor(R.color.chart_values));
        lineDataSet.setCircleColor(getResources().getColor(R.color.chart_values));
        lineDataSet.setCircleColorHole(getResources().getColor(R.color.chart_values));
        LineData lineData = new LineData(lineDataSet);
        lcChart.setData(lineData);
        lcChart.invalidate();
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

    private void setChartSettings() {
        lcChart.setNoDataText(getString(R.string.loading_data) + " ...");
        lcChart.setScaleMinima(2f, 1f);
        lcChart.getAxisRight().setEnabled(false);
        lcChart.getLegend().setEnabled(false);
        lcChart.getDescription().setEnabled(false);
        lcChart.setAutoScaleMinMaxEnabled(true);
        lcChart.setScaleYEnabled(false);
        lcChart.setDoubleTapToZoomEnabled(false);
        lcChart.setAnimationCacheEnabled(true);
        lcChart.buildDrawingCache(true);
        lcChart.setDrawingCacheEnabled(true);
        lcChart.getViewPortHandler().setMaximumScaleX(4.8f);
        lcChart.setHighlightPerDragEnabled(false);
        lcChart.setHighlightPerTapEnabled(false);

        XAxis xAxis = lcChart.getXAxis();
        xAxis.setAxisLineColor(getResources().getColor(R.color.chart_values));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setTextSize(8);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Date date = new Date((long) value);
                return DateUtils.getDateByStr(date, DateUtils.DATE_FORMAT_OUTPUT);
            }
        });
    }

    public class DynamicBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(Const.RESPONSE);
            if(CurrencyRate.getInstanceDynamic() != null && response != null && response.equals(Api.CODE_SUCCESS + "")) {
                Log.d(TAG, "dynamic rates: ");
                Log.d(TAG, CurrencyRate.getInstanceDynamic().toString());
                setDataOnChart();
            } else {
                showMessage(R.string.query_no_answer);
            }
            hideProgressDialog();
        }
    }
}
