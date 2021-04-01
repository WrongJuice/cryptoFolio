package com.example.mywallet.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;
import com.example.mywallet.services.NomicsService;
import com.example.mywallet.services.RetrieveWallet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationService.setAppContext(this);
        setContentView(R.layout.activity_main);

        TextView wallet = findViewById(R.id.wallet);
        Button connect = findViewById(R.id.connect);

        EditText publicKey = findViewById(R.id.public_key);
        EditText privateKey = findViewById(R.id.private_key);

        connect.setOnClickListener(view -> {
            RetrieveWallet retrieveWallet = new RetrieveWallet();
            try {
                wallet.setText(String.valueOf(retrieveWallet.execute(
                        publicKey.getText().toString(),
                        privateKey.getText().toString(),
                        "EUR").get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        ArrayList<String> assets = new ArrayList<>();
        assets.add("LTC");
        assets.add("BTC");
        System.out.println("prices="+NomicsService.getPrices(assets, "EUR"));

        // api key : 0HJvpJwNLRN41SSQJIaCYpsZ4UUryR3h7XfolarHu4vCFykTPKsoNLY2IC6F0Q1e
        // api secretkey : DqvVFLcFh4ltUd4MN7WNQdoMTrEjzovQVjeqS0MyVGb4vasOQP7IMdlH2KP80RK5
    }

}