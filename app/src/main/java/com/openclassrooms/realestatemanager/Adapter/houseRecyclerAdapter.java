package com.openclassrooms.realestatemanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class houseRecyclerAdapter extends RecyclerView.Adapter<houseRecyclerAdapter.ViewHolder> {

    private List<House> houseList;
    private OnHouseListener onHouseListener;

    private DetailFragment detailFragment;
    //private Context context;

    //Use to call configureAndShowDetailFragment() from MainActivity
   /* public ListHouseAdapter(List<House> houseList, Context context) {
        this.houseList = houseList; this.context = context;
    }*/

    public houseRecyclerAdapter(List<House> houseList, OnHouseListener onHouseListener) {
        this.houseList = houseList;
        this.onHouseListener = onHouseListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item,parent,false);
        return new ViewHolder(view, onHouseListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       House house = houseList.get(position);
       holder.category.setText(house.getCategory());
       holder.district.setText(house.getDistrict());
       holder.price.setText(house.getPrice());

        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .override(100, 100);

       Glide.with(holder.illustration.getContext())
               .load(house.getIllustration())
               .apply(myOptions)
               .into(holder.illustration);

       /*holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               *//*if (context instanceof MainActivity) {
                   ((MainActivity)context).configureAndShowDetailFragment();
               }*//*
               AppCompatActivity activity = (AppCompatActivity) view.getContext();
               detailFragment = new DetailFragment();
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, detailFragment)
                       .addToBackStack(null)
                       .commit();
           }
       });*/
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public void updateList(@NonNull  final List<House> houseList) {
        this.houseList = houseList;
        notifyDataSetChanged();
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category, district, price;
        ImageView illustration;
        OnHouseListener onHouseListener;

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
