package com.openclassrooms.realestatemanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class HouseRecyclerAdapter extends RecyclerView.Adapter<HouseRecyclerAdapter.ViewHolder> {

    private List<House> houseList;
    private OnHouseListener onHouseListener;

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
        holder.updateHouse(houseList.get(position));
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }


    // Verifier usage
    public void updateList(@NonNull final List<House> houseList) {
        this.houseList = houseList;
        notifyDataSetChanged();
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView category, district, price;
        private ImageView illustration;
        private OnHouseListener onHouseListener;

        //Constructor
        public ViewHolder(View itemView, OnHouseListener onHouseListener) {
            super(itemView);
            category = itemView.findViewById(R.id.tv_fragment_main_item_category);
            district = itemView.findViewById(R.id.tv_fragment_main_item_district);
            price = itemView.findViewById(R.id.tv_fragment_main_item_price);
            illustration = itemView.findViewById(R.id.fragment_main_item_illustration);
            this.onHouseListener = onHouseListener;

            itemView.setOnClickListener(this);
        }

        public void updateHouse(House house) {

            category.setText(house.getCategory());
            district.setText(house.getDistrict());
            price.setText(String.valueOf(house.getPrice()));

            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .override(100, 100);

            Glide.with(illustration.getContext())
                    .load(house.getIllustration())
                    .apply(myOptions)
                    .into(illustration);
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
