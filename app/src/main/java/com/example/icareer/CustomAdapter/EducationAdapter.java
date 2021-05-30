package com.example.icareer.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.Model.JobSeekerClass;
import com.example.icareer.R;

import java.util.List;

public class EducationAdapter  extends RecyclerView.Adapter<EducationAdapter.ViewHolder>{
    private List<JobSeekerClass> educationList;
    private LayoutInflater mInflater;


    public EducationAdapter(Context context, List<JobSeekerClass> educationList) {
        this.mInflater = LayoutInflater.from(context);
        this.educationList = educationList;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.education_layout_of_recycleview, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final JobSeekerClass education = educationList.get(position);
        holder.myTextView.setText(education.getEducation());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationList.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, educationList.size());
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return educationList.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageButton deleteBtn;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.education_text);
            deleteBtn = itemView.findViewById(R.id.delete_education_item);
        }

    }

}
