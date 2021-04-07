package com.example.mywallet.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;
import com.example.mywallet.services.BinanceService;
import com.example.mywallet.utils.Currency;
import com.example.mywallet.utils.UpdateUi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationService.setAppContext(this);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = ApplicationService.getAppContext().getSharedPreferences("keys", 0); // 0 - for private mode

        Button connect = findViewById(R.id.connect);

        Spinner currenceySelector = findViewById(R.id.currency_select);

        ArrayList<String> currencies = new ArrayList<>();
        for (Currency currency : Currency.values()) currencies.add(currency.name());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currenceySelector.setAdapter(adapter);

        EditText publicKey = findViewById(R.id.public_key);
        EditText privateKey = findViewById(R.id.private_key);

        publicKey.setText(pref.getString("publicBinance", ""));
        privateKey.setText(pref.getString("privateBinance", ""));

        UpdateUi updateUi = new UpdateUi();
        updateUi.setUnitTextView(findViewById(R.id.unit));
        updateUi.setTotalWallet(findViewById(R.id.wallet));
        updateUi.setUpCurrenciesBalanceView(findViewById(R.id.balances));

        connect.setOnClickListener(view -> {
            pref.edit().putString("publicBinance", publicKey.getText().toString())
            .putString("privateBinance", privateKey.getText().toString()).apply();
            BinanceService.setKeys(publicKey.getText().toString(), privateKey.getText().toString());
            BinanceService binanceService = new BinanceService(updateUi);
            binanceService.getBalance(currencies.get(currenceySelector.getSelectedItemPosition()));
            updateUi.setSelectedConvertCurrency(currencies.get(currenceySelector.getSelectedItemPosition()));
        });

        // api key : 0HJvpJwNLRN41SSQJIaCYpsZ4UUryR3h7XfolarHu4vCFykTPKsoNLY2IC6F0Q1e
        // api secretkey : DqvVFLcFh4ltUd4MN7WNQdoMTrEjzovQVjeqS0MyVGb4vasOQP7IMdlH2KP80RK5
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);

        } else {
            setContentView(R.layout.activity_main);
        }
    }

}