package com.example.fragment.parse_course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.R;

import java.util.ArrayList;
import java.util.List;

    public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    interface onclickListener {
        void onClicked(course_info model);
    }

    private onclickListener mListener;

    private List<course_info> mItems = new ArrayList<>();

    public Adapter2() {}

    public Adapter2(onclickListener listener) {
        mListener = listener;
    }

    public void setItems(List<course_info> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_info_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final course_info item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onClicked(item);
                }else{
                    Log.d("jmw93","더 표시할데이터가 없음");
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        course_info item = mItems.get(position);
        String title= item.getTitle();
        String num = item.getNum();
        int int_num= Integer.parseInt(num);
        int_num =int_num+1;

        holder.title.setText(int_num+"코스:" + title);
        holder.image.setImageBitmap(item.getImg());
        holder.overview.setText(item.getOverview());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overview;
        TextView num;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title =itemView.findViewById(R.id.title);
            image =itemView.findViewById(R.id.image);
            num= itemView.findViewById(R.id.num);
            overview = itemView.findViewById(R.id.overview);
        }
    }
}
