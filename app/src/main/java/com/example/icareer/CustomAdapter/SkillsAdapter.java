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

public class SkillsAdapter  extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>{
    private List<JobSeekerClass> skillsList;
    private LayoutInflater mInflater;


    public SkillsAdapter(Context context, List<JobSeekerClass> skillsList) {
        this.mInflater = LayoutInflater.from(context);
        this.skillsList = skillsList;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.skills_layout_of_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobSeekerClass skills = skillsList.get(position);
        holder.myTextView.setText(skills.getSkill());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillsList.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, skillsList.size());
            }
        });
    }


    // total number of rows
    @Override
    public int getItemCount() {

        return skillsList.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageButton deleteBtn;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.skill_text);
            deleteBtn = itemView.findViewById(R.id.delete_skill_item);
        }

    }
}
