package com.example.fragment.parse_Tour;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TourViewHolder> {
    interface OnTourClickListener {
        void onTourClicked(Tour model);
    }
    
    private OnTourClickListener mListener;
    
    private List<Tour> mItems = new ArrayList<>();

    public Adapter() {}

    public Adapter(OnTourClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<Tour> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_item, parent, false);
        final TourViewHolder viewHolder = new TourViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final Tour item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onTourClicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour item = mItems.get(position);
        // TODO : 데이터를 뷰홀더에 표시하시오
        holder.name.setText(item.getName());
        holder.address.setText(item.getAddress());
        holder.imageView.setImageBitmap(item.getBitmap());
}

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {
        // TODO : 뷰홀더 완성하시오
        TextView name;
        TextView address;
        ImageView imageView;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}