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

import java.util.List;

public class CurrencyBalanceAdapter extends ArrayAdapter<CurrencyBalance> {

    /**
     * The Context.
     */
    Context context;

    public CurrencyBalanceAdapter(@NonNull Context context, @NonNull List<CurrencyBalance> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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
        TextView currencyBaanceUnit = convertView.findViewById(R.id.balance_unit);
        TextView convertedBalance = convertView.findViewById(R.id.currency_balance_converted);
        TextView convertedBalanceUnit = convertView.findViewById(R.id.converted_balance_unit);

        currencyName.setText(currencyBalanceItem.getCurrencyName());
        currencyId.setText(currencyBalanceItem.getCurrencyId());
        currencyValue.setText(String.valueOf(currencyBalanceItem.getCurrentPrice()));
        valueUnit.setText(currencyBalanceItem.getConvertUnit());
        currencyBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalance()));
        currencyBaanceUnit.setText(currencyBalanceItem.getConvertUnit());
        convertedBalance.setText(String.valueOf(currencyBalanceItem.getCurrentBalanceConverted()));
        convertedBalanceUnit.setText(currencyBalanceItem.getConvertUnit());

        return convertView;
    }
}
