package com.ozantok3.cryptomoney;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ozantok on 2.07.2018.
 */

public interface CoinMarketCapApi {

    @GET("/v2/listings/")
    Call<CryptoDataList> listCoins();
}
