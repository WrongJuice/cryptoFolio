package com.example.mywallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mywallet.R;
import com.example.mywallet.models.CurrencyBalance;
import com.example.mywallet.utils.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyBalanceAdapter extends ArrayAdapter<CurrencyBalance> {

    private final List<CurrencyBalance> balances;
    private final List<View> views;
    private ViewGroup parent;

    public CurrencyBalanceAdapter(@NonNull Context context, @NonNull List<CurrencyBalance> balances) {
        super(context, 0, balances);
        views = new ArrayList<>();
        this.balances = balances;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        this.parent = parent;

        // Get the data item for this position
        CurrencyBalance currencyBalanceItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.currency_balance_item, parent, false);
        }

        TextView currencyName = convertView.findViewById(R.id.currency_name);
        TextView currencyId = convertView.findViewById(R.id.currency_id);
        TextView currencyValue = convertView.findViewById(R.id.currency_value);
        TextView valueUnit = convertView.findViewById(R.id.value_unit);
        TextView currencyBalance = convertView.findViewById(R.id.currency_balance);
        TextView currencyBalanceUnit = convertView.findViewById(R.id.currency_unit);
        TextView convertedBalance = convertView.findViewById(R.id.currency_balance_converted);
        TextView convertedBalanceUnit = convertView.findViewById(R.id.converted_balance_unit);

        currencyName.setText(currencyBalanceItem.getCurrencyName());
        currencyId.setText(currencyBalanceItem.getCurrencyId());
        currencyValue.setText(String.valueOf(currencyBalanceItem.getCurrentPrice()));
        valueUnit.setText(Currency.valueOf(currencyBalanceItem.getConvertUnit()).getSymbol());
        currencyBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalance()));
        currencyBalanceUnit.setText(currencyBalanceItem.getCurrencyId());
        convertedBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalanceConverted()));
        convertedBalanceUnit.setText(Currency.valueOf(currencyBalanceItem.getConvertUnit()).getSymbol());

        views.add(convertView);

        return convertView;
    }

    public void updateViews() {
        for (int i = 0 ; i != balances.size() ; i++) {
            CurrencyBalance currencyBalanceItem = getItem(i);
            // Check if an existing view is being reused, otherwise inflate the view
            View convertView = getView(i, views.get(i), parent);

            TextView currencyName = convertView.findViewById(R.id.currency_name);
            TextView currencyId = convertView.findViewById(R.id.currency_id);
            TextView currencyValue = convertView.findViewById(R.id.currency_value);
            TextView valueUnit = convertView.findViewById(R.id.value_unit);
            TextView currencyBalance = convertView.findViewById(R.id.currency_balance);
            TextView currencyBalanceUnit = convertView.findViewById(R.id.currency_unit);
            TextView convertedBalance = convertView.findViewById(R.id.currency_balance_converted);
            TextView convertedBalanceUnit = convertView.findViewById(R.id.converted_balance_unit);

            currencyName.setText(currencyBalanceItem.getCurrencyName());
            currencyId.setText(currencyBalanceItem.getCurrencyId());
            currencyValue.setText(String.valueOf(currencyBalanceItem.getCurrentPrice()));
            valueUnit.setText(Currency.valueOf(currencyBalanceItem.getConvertUnit()).getSymbol());
            currencyBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalance()));
            currencyBalanceUnit.setText(currencyBalanceItem.getCurrencyId());
            convertedBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalanceConverted()));
            convertedBalanceUnit.setText(Currency.valueOf(currencyBalanceItem.getConvertUnit()).getSymbol());
        }
    }

}
