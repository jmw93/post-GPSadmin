package com.example.fragment.parse_hotel;

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

    public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    interface onClickListener {
        void onclicked(Hotel model);
    }

    private onClickListener mListener;

    private List<Hotel> mItems = new ArrayList<>();

    public Adapter() {}

    public Adapter(onClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<Hotel> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_item, parent, false);
        final Holder viewHolder = new Holder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final Hotel item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onclicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Hotel item = mItems.get(position);
        // TODO : 데이터를 뷰홀더에 표시하시오
        holder.title.setText(item.getTitle());
        holder.addr.setText(item.getAddr());
        holder.image.setImageBitmap(item.getImage());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView addr;
        TextView title;
        ImageView image;
        public Holder(@NonNull View itemView) {
            super(itemView);

            addr =itemView.findViewById(R.id.hotel_addr);
            title =itemView.findViewById(R.id.hotel_title);
            image =itemView.findViewById(R.id.hotel_imageview);

        }
    }
}
