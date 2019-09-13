package com.example.fragment.main_View_btn1;

import android.content.Context;
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

public class Adapter2 extends RecyclerView.Adapter<Adapter2.objectViewHolder> {
    interface objectOnClickListener {
        void onClicked(object model);
    }
    Context context;
    private objectOnClickListener mListener;

    private List<object> mItems = new ArrayList<>();

    public Adapter2() {}

    public Adapter2(objectOnClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<object> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public objectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        final objectViewHolder viewHolder = new objectViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final object item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onClicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull objectViewHolder holder, int position) {
        object item = mItems.get(position);
        holder.imgview.setBackgroundResource(item.getImgpath());
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class objectViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView title;
        TextView subtitle;

        public objectViewHolder(@NonNull View itemView) {
            super(itemView);

        imgview =itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.title);
        subtitle= itemView.findViewById(R.id.subtitle);
        }
    }
}
