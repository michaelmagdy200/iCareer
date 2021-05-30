package com.example.icareer.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icareer.Model.JobSeekerClass;
import com.example.icareer.R;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder>{
    private List<JobSeekerClass> ExperienceList;
    private LayoutInflater mInflater;


    public ExperienceAdapter(Context context, List<JobSeekerClass> ExperienceList) {
        this.mInflater = LayoutInflater.from(context);
        this.ExperienceList = ExperienceList;
    }
    // inflates the row layout from xml when needed
    @Override
    public ExperienceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.experciese_layout_of_recycleview, parent, false);
        return new ExperienceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobSeekerClass experience = ExperienceList.get(position);
        holder.myTextView.setText(experience.getExperience());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceList.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ExperienceList.size());
            }
        });
    }




    // total number of rows
    @Override
    public int getItemCount() {
        return ExperienceList.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageButton deleteBtn;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.experience_text);
            deleteBtn = itemView.findViewById(R.id.delete_experience_item);
        }

    }



}
