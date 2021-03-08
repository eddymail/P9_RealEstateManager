package com.openclassrooms.realestatemanager.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class GalleryRecyclerAdapter extends Adapter<GalleryRecyclerAdapter.ViewHolder> {

    private List<Illustration> gallery;

    public GalleryRecyclerAdapter(List<Illustration> gallery) {
        this.gallery = gallery;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detail_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateIllustration(gallery.get(position));
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }


    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private ImageView picture;

        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.tv_fragment_detail_item_description);
            picture = itemView.findViewById(R.id.iv_fragment_detail_item);

        }

        public void updateIllustration(Illustration illustration) {

            description.setText(illustration.getDescription());
            Glide.with(picture.getContext())
                    .load(illustration.getUrl())
                    .into(picture);
        }
    }
}
