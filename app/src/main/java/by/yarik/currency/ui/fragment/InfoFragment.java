package by.yarik.currency.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.yarik.currency.R;
import by.yarik.currency.ui.fragment.base.BaseFragment;

/**
 * Created by Lenovo on 23.04.2017.
 */

public class InfoFragment extends BaseFragment {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
