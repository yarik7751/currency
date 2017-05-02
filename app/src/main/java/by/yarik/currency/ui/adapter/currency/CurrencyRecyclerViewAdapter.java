package by.yarik.currency.ui.adapter.currency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.yarik.currency.R;
import by.yarik.currency.util.CustomSharedPreference;
import by.yarik.currency.util.api.pojo.Currency;
import by.yarik.currency.util.api.pojo.CurrencyRate;
import by.yarik.currency.util.db.HelperFactory;

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyRecyclerViewAdapter.CurrencyHolder> {

    public static final String TAG = "CurrencyRecycler_logs";

    public static final int ALL_CURRENCY = 1;
    public static final int SELECTED_CURRENCY = 2;

    private Context context;
    private List<CurrencyRate> currencyRateList;
    private OnSharedPreferencesChange onSharedPreferencesChange;
    int variant;

    public CurrencyRecyclerViewAdapter(Context context, List<CurrencyRate> currencyRateList, int variant,
                                       OnSharedPreferencesChange onSharedPreferencesChange) {
        this.context = context;
        this.currencyRateList = new ArrayList<>();
        this.onSharedPreferencesChange = onSharedPreferencesChange;
        this.variant = variant;
        for(CurrencyRate currencyRate : currencyRateList) {
            if(variant == ALL_CURRENCY) {
                if(!CustomSharedPreference.getSelectCurrencies(context).contains(currencyRate.getCurAbbreviation())) {
                    this.currencyRateList.add(currencyRate);
                }
            } else if(variant == SELECTED_CURRENCY) {
                if(CustomSharedPreference.getSelectCurrencies(context).contains(currencyRate.getCurAbbreviation())) {
                    this.currencyRateList.add(currencyRate);
                }
            }
        }
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency_rate, parent, false);
        return new CurrencyHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, final int position) {
        holder.tvNameCode.setText(currencyRateList.get(position).getCurAbbreviation());
        int scale = currencyRateList.get(position).getCurScale();
        holder.tvName.setText((scale > 1 ? scale + " " : "") + getBelName(scale, currencyRateList.get(position)));
        holder.tvRate.setText(currencyRateList.get(position).getCurOfficialRate() + "");
        if(variant == SELECTED_CURRENCY) {
            holder.imgSelectCurr.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
        holder.imgSelectCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String abbreviation = currencyRateList.get(position).getCurAbbreviation().toUpperCase() + ";";
                Log.d(TAG, "abbreviation: " + abbreviation);
                String data = CustomSharedPreference.getSelectCurrencies(context);
                if(variant == ALL_CURRENCY) {
                    if(!data.contains(abbreviation)) {
                        data += abbreviation;
                    }
                    CustomSharedPreference.setSelectCurrencies(context, data);
                } else if(variant == SELECTED_CURRENCY) {
                    if(data.contains(abbreviation)) {
                        data = data.replace(abbreviation, "");
                    }
                    CustomSharedPreference.setSelectCurrencies(context, data);
                }
                Log.d(TAG, "data: " + data);
                if(onSharedPreferencesChange != null) {
                    onSharedPreferencesChange.onChange();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyRateList == null ? 0 : currencyRateList.size();
    }

    private String getBelName(int scale, CurrencyRate currencyRate) {
        List<Currency> currencies = HelperFactory.getHelper().getCurrencyDAO().getCurrencyByAbbreviation(currencyRate.getCurAbbreviation());
        if(scale > 1) {
            return currencies.get(currencies.size() - 1).getCurNameBelMulti();
        } else {
            return currencies.get(currencies.size() - 1).getCurNameBel();
        }
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {

        public TextView tvNameCode, tvName, tvRate;
        public ImageView imgSelectCurr;
        public View itemView;

        public CurrencyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNameCode = (TextView) itemView.findViewById(R.id.tv_name_code);
            tvRate = (TextView) itemView.findViewById(R.id.tv_rate);
            imgSelectCurr = (ImageView) itemView.findViewById(R.id.img_select_curr);
        }
    }
}
