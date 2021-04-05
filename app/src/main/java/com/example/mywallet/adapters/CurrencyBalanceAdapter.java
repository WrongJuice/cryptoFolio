package com.example.mywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mywallet.R;
import com.example.mywallet.models.CurrencyBalance;

import java.util.List;

public class CurrencyBalanceAdapter extends ArrayAdapter<CurrencyBalance> {

    /**
     * The Context.
     */
    Context context;

    public CurrencyBalanceAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyBalance> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        CurrencyBalance currencyBalance = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.currency_balance_item, parent, false);
        }

        return convertView;
    }
}
