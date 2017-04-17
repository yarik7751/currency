package by.yarik.currency.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.yarik.currency.ui.fragment.base.BaseFragment;

public class CurrencyFragment extends BaseFragment {

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
