package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView wallet = findViewById(R.id.wallet);

        Wallet firstWallet = new Wallet();
        wallet.setText(String.valueOf(firstWallet.getWalletAmount()));
    }
}