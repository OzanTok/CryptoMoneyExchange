package com.ozantok3.cryptomoney;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ozantok on 2.07.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CryptoFragment> listItems;
    private Context myContext;

    public MyAdapter (List<CryptoFragment> listItems, Context myContext){

        this.listItems = listItems;
        this.myContext = myContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final CryptoFragment item = listItems.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display option Menu

                PopupMenu popupMenu = new PopupMenu(myContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.fav_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch  (item.getItemId()) {

                            case R.id.menu_item_fav:

                                Toast.makeText(myContext, "Adding Favorites", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_item_del:
                                Toast.makeText(myContext,"Deleting Favorites", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtOptionDigit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle= itemView.findViewById(R.id.textView);

        }
    }
}
