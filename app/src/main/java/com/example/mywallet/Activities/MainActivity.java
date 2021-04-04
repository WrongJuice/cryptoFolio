package com.example.mywallet.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywallet.R;
import com.example.mywallet.services.ApplicationService;
import com.example.mywallet.services.BinanceService;
import com.example.mywallet.services.nomics.requests.CryptoCurrenciesConversionsRequest;
import com.example.mywallet.utils.UpdateUi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationService.setAppContext(this);
        setContentView(R.layout.activity_main);

        Button connect = findViewById(R.id.connect);

        EditText publicKey = findViewById(R.id.public_key);
        EditText privateKey = findViewById(R.id.private_key);

        /*
        connect.setOnClickListener(v -> {
            Map<String, Double> balances = new HashMap<>();
            try {
                Future<Map<String, Double>> getBalances = BinanceService.getBalances(
                        publicKey.getText().toString(),
                        privateKey.getText().toString(),
                        "EUR"
                );
                while (!getBalances.isDone());
                balances = getBalances.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            double totalBalance = 0;
            for (Map.Entry<String, Double> balance : balances.entrySet()) {
                totalBalance += balance.getValue();
            }
            wallet.setText(String.valueOf(totalBalance));

        });
        */
        UpdateUi updateUi = new UpdateUi();
        updateUi.setTotalWallet(findViewById(R.id.wallet));

        connect.setOnClickListener(view -> {
            BinanceService binanceService = new BinanceService();
            binanceService.execute(publicKey.getText().toString(), privateKey.getText().toString(),
                    "EUR", updateUi);
        });


        //ArrayList<String> assets = new ArrayList<>();
        //assets.add("LTC");
        //assets.add("BTC");
        //NomicsService nomicsServices = new NomicsService();
        //nomicsServices.runJSONArrayRequest(Request.Method.GET, "https://api.nomics.com/v1/currencies/ticker?key=445493623ee547d91d94d2803d3f12ae&format=json&convert=EUR&ids=LTC,BTC&interval=none");
        //System.out.println("prices="+NomicsService.getPrices(assets, "EUR"));

        // api key : 0HJvpJwNLRN41SSQJIaCYpsZ4UUryR3h7XfolarHu4vCFykTPKsoNLY2IC6F0Q1e
        // api secretkey : DqvVFLcFh4ltUd4MN7WNQdoMTrEjzovQVjeqS0MyVGb4vasOQP7IMdlH2KP80RK5
    }

}