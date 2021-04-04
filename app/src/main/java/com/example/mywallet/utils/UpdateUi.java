package com.example.mywallet.utils;

import android.widget.TextView;

public class UpdateUi {

    private TextView totalWallet;

    public void updateTotalWallet (double wallet) {
        totalWallet.setText(String.valueOf(wallet));
    }

    public void setTotalWallet(TextView totalWallet) {
        this.totalWallet = totalWallet;
    }

}
