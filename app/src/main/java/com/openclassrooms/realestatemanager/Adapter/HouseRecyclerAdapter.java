package com.openclassrooms.realestatemanager.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.Activities.MainActivity;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import java.util.List;

public class HouseRecyclerAdapter extends RecyclerView.Adapter<HouseRecyclerAdapter.ViewHolder> {

    private List<House> houseList;
    private OnHouseListener onHouseListener;
  //  private boolean isEuro;

    public HouseRecyclerAdapter(List<House> houseList, OnHouseListener onHouseListener) {
        this.houseList = houseList;
        this.onHouseListener = onHouseListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
        return new ViewHolder(view, onHouseListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("Test", "onBindViewHolder");
        holder.updateHouse(houseList.get(position));
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public void setData(List<House> newData) {
        if (houseList != null) {
            houseList.clear();
            houseList.addAll(newData);
            notifyDataSetChanged();
        } else {
            houseList = newData;
        }
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView category, district, price;
        private ImageView illustrationView, changeView;
        private OnHouseListener onHouseListener;
        private String illustration;
        public Resources res;

        // Constructor
        public ViewHolder(View itemView, OnHouseListener onHouseListener) {
            super(itemView);
         //   res = itemView.getResources();
            category = itemView.findViewById(R.id.tv_fragment_main_item_category);
            district = itemView.findViewById(R.id.tv_fragment_main_item_district);
            price = itemView.findViewById(R.id.tv_fragment_main_item_price);
            illustrationView = itemView.findViewById(R.id.fragment_main_item_illustration);
            changeView = itemView.findViewById(R.id.iv_fragment_main_item_change);
            this.onHouseListener = onHouseListener;

            itemView.setOnClickListener(this);
        }

        public void updateHouse(House house) {

            category.setText(house.getCategory());
            district.setText(house.getDistrict());

            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .override(100, 100);

            boolean isEuro = house.isEuro();


            if (isEuro == true) {
                price.setText(String.valueOf(house.getPrice()));
                changeView.setImageResource(R.drawable.ic_baseline_eur_24);
            }
            else if (isEuro == false) {
                price.setText(String.valueOf(Utils.convertEuroToDollars(house.getPrice())));
                changeView.setImageResource(R.drawable.ic_baseline_price_change_24);
            }

            if (house.getIllustration().isEmpty()) {
                illustrationView.setImageResource(R.drawable.sell_house);
            } else {
                illustration = Utils.getIllustrationFromDevice(house);

                Glide.with(illustrationView.getContext())
                        .load(illustration)
                        .apply(myOptions)
                        .into(illustrationView);
            }
            Log.e("Test", "updateHouse");
        }

        @Override
        public void onClick(View view) {
            onHouseListener.onHouseClick(getAdapterPosition());
        }
    }

    // Use to detect the click
    public interface OnHouseListener {
        void onHouseClick(int position);
    }
}
