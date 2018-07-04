package com.ozantok3.cryptomoney;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ozantok on 2.07.2018.
 */

public class CryptoApp extends Application {

    public static List<CryptoData> listFav = new ArrayList<>();
    public static Retrofit retrofit;
    public static CoinMarketCapApi coinMarketCapApi;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coinMarketCapApi = retrofit.create(CoinMarketCapApi.class);
    }

}
