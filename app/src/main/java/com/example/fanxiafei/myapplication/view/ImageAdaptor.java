package com.example.fanxiafei.myapplication.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.model.Image;

import java.util.ArrayList;

public class ImageAdaptor extends RecyclerView.Adapter<ImageAdaptor.ViewHolder> {

    private ArrayList<Image> images;

    public void setImageList(ArrayList<Image> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdaptor.ViewHolder holder, int position) {
        Image image = images.get(position);
        Glide.with(holder.imageView.getContext())
                .load(image.getPath())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
        holder.compatCheckBox.setChecked(image.isCheck());
        holder.imageView.setOnClickListener(v -> {
            boolean isChecked = image.isCheck();
            image.setCheck(!isChecked);
            holder.compatCheckBox.setChecked(!isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public AppCompatCheckBox compatCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            compatCheckBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
