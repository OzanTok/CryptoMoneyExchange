package com.ozantok3.cryptomoney;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ozantok on 2.07.2018.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private List<CryptoData> listItems = new ArrayList<>();
    private Context myContext;

    public MyAdapter(Context myContext) {
        this.myContext = myContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(listItems.get(position));
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public void updateData(List<CryptoData> items) {
        listItems.clear();
        listItems.addAll(items);
        notifyDataSetChanged();
    }

    protected class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRow)
        TextView mItemText;

        @BindView(R.id.imageView)
        ImageView imageView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private boolean isInFavorites(CryptoData cryptoData) {
            for (int i = 0; i < CryptoApp.listFav.size(); i++) {
                if (CryptoApp.listFav.get(i).getSymbol().equals(cryptoData.getSymbol())) {
                    return true;
                }
            }
            return false;
        }

        public void bindView(final CryptoData cryptoData) {
            mItemText.setText(cryptoData.getName());
            boolean isInFav = false;

            if (isInFavorites(cryptoData)) {
                imageView.setImageResource(android.R.drawable.star_on);
            } else {
                imageView.setImageResource(android.R.drawable.star_off);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Display option Menu
                    PopupMenu popupMenu = new PopupMenu(myContext, itemView);
                    popupMenu.inflate(R.menu.fav_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_item_fav:
                                    if(!isInFavorites(cryptoData)){
                                        CryptoApp.listFav.add(cryptoData);
                                    }
                                    imageView.setImageResource(android.R.drawable.star_on);
                                    Toast.makeText(myContext, "Adding Favorites", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.menu_item_del:
                                    CryptoApp.listFav.remove(cryptoData);
                                    imageView.setImageResource(android.R.drawable.star_off);
                                    Toast.makeText(myContext, "Deleting Favorites", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }

                            return false;
                        }
                    });

                    popupMenu.show();

                }
            });
        }


    }
}
