package com.choubapp.ensias_wiki_hub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


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
