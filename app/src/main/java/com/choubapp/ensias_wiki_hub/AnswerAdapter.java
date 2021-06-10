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

import com.choubapp.ensias_wiki_hub.model.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class AnswerAdapter extends FirestoreRecyclerAdapter<Answer,AnswerAdapter.AnswerViewHolder> {
    //private ArrayList<PostItem> postList;
    private OnItemClickListener listener;


    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        public TextView content, vote, date, owner;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.answer_content);
            vote= itemView.findViewById(R.id.answer_vote);
            owner= itemView.findViewById(R.id.answer_owner);
            date= itemView.findViewById(R.id.answer_date);
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

    public AnswerAdapter(@NonNull FirestoreRecyclerOptions<Answer> options) {
        super(options);
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new AnswerViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull AnswerViewHolder holder, int position, @NonNull Answer model) {
        holder.content.setText(model.getContent());
        holder.owner.setText(model.getOwner());
        holder.vote.setText(String.valueOf(model.getVote()));
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        holder.date.setText(newFormat.format(model.getDate()).toString());
    }



}
