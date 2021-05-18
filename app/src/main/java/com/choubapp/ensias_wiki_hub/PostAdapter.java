package com.choubapp.ensias_wiki_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<PostItem> postList;


    public class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title, owner, vote, date;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.post_name);
            owner= itemView.findViewById(R.id.post_owner);
            vote= itemView.findViewById(R.id.post_vote);
            date= itemView.findViewById(R.id.post_date);
        }
    }

    public PostAdapter(ArrayList<PostItem> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder evh = new PostViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        PostItem currentItem = postList.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.owner.setText(currentItem.getOwner());
        holder.vote.setText(currentItem.getVote());
        holder.date.setText(currentItem.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}
