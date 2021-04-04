package com.example.mywallet.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;
import com.example.mywallet.services.BinanceService;
import com.example.mywallet.utils.UpdateUi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationService.setAppContext(this);
        setContentView(R.layout.activity_main);

        Button connect = findViewById(R.id.connect);

        EditText publicKey = findViewById(R.id.public_key);
        EditText privateKey = findViewById(R.id.private_key);

        UpdateUi updateUi = new UpdateUi();
        updateUi.setTotalWallet(findViewById(R.id.wallet));

        connect.setOnClickListener(view -> {
            BinanceService.setKeys(publicKey.getText().toString(), privateKey.getText().toString());
            BinanceService binanceService = new BinanceService(updateUi);
            binanceService.getBalance("EUR");
        });

        // api key : 0HJvpJwNLRN41SSQJIaCYpsZ4UUryR3h7XfolarHu4vCFykTPKsoNLY2IC6F0Q1e
        // api secretkey : DqvVFLcFh4ltUd4MN7WNQdoMTrEjzovQVjeqS0MyVGb4vasOQP7IMdlH2KP80RK5
    }

}