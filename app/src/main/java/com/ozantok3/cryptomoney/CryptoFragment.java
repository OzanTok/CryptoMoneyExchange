package com.ozantok3.cryptomoney;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ozantok on 2.07.2018.
 */

public class CryptoFragment extends Fragment {

    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crypto_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CryptoApp.coinMarketCapApi.listCoins().enqueue(new Callback<CryptoDataList>() {
            @Override
            public void onResponse(Call<CryptoDataList> call, Response<CryptoDataList> response) {
                if(response.body() != null){
                    adapter.updateData(response.body().getData());
                }
                else {
                    Toast.makeText(getActivity(), "Response is empty", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<CryptoDataList> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }
}


