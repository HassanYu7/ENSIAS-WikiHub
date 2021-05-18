package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.Answer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;


public class Dashboard extends AppCompatActivity {
    private PostAdapter adapter;
    //ArrayList<PostItem> postsList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postsCollection = db.collection("Post");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query = postsCollection.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<PostItem> options = new FirestoreRecyclerOptions.Builder<PostItem>()
                .setQuery(query, PostItem.class)
                .build();
        adapter= new PostAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //PostItem post = documentSnapshot.toObject(PostItem.class);
                String id = documentSnapshot.getId();
                //String path = documentSnapshot.getReference().getPath();
                Toast.makeText(Dashboard.this,
                        "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard.this,PostPage.class);
                intent.putExtra("POST_DOCUMENT_ID", id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void home(View v){
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }

    public void profile(View v){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
    }

    public void newquestion(View v){
        Intent intent = new Intent(this,AskQuestion.class);
        startActivity(intent);
    }
}
