package com.choubapp.ensias_wiki_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PostAdapter extends FirestoreRecyclerAdapter<PostItem,PostAdapter.PostViewHolder> {
    //private ArrayList<PostItem> postList;
    private OnItemClickListener listener;


    public class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title, vote, date;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.post_name);
            vote= itemView.findViewById(R.id.post_vote);
            date= itemView.findViewById(R.id.post_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PostAdapter(@NonNull FirestoreRecyclerOptions<PostItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull PostItem model) {
        holder.title.setText(model.getTitle());
        holder.vote.setText(String.valueOf(model.getVote()));
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        holder.date.setText(newFormat.format(model.getDate()).toString());
        //holder.date.setText((CharSequence) new Date().toString());
    }


}
