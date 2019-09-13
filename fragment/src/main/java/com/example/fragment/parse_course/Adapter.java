package com.example.fragment.parse_course;

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

public class Adapter extends RecyclerView.Adapter<Adapter.courseviewHolder> {
    interface OncourseClickListener {
        void onCourseClicked(Course model);
    }
    
    private OncourseClickListener mListener;
    
    private List<Course> mItems = new ArrayList<>();

    public Adapter() {}

    public Adapter(OncourseClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<Course> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public courseviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        final courseviewHolder viewHolder = new courseviewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                        final Course item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.onCourseClicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull courseviewHolder holder, int position) {
        Course item = mItems.get(position);
        // TODO : 데이터를 뷰홀더에 표시하시오
        if(item.getImage() != null) {
            holder.course_title.setText(item.getTitle());
            holder.imageview.setImageBitmap(item.getImage());
            }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class courseviewHolder extends RecyclerView.ViewHolder {
        // TODO : 뷰홀더 완성하시오
        TextView course_title;
        ImageView imageview;
        
        public courseviewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            imageview =itemView.findViewById(R.id.imageview);
        }
    }
}
