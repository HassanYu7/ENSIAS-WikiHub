package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<PostItem> postsList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference posts = db.collection("Post");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        posts.whereEqualTo("tags",null).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(DocumentSnapshot doc: task.getResult()){
                        postsList.add(new PostItem(doc.get("title").toString(),doc.get("owner").toString(), ((Long)doc.get("vote")).intValue(),doc.getDate("datePosted")));
                        System.out.println("!!!!!!!!!! I found Something "+postsList);
                    }
                }
                adapter = new PostAdapter(postsList);
                recyclerView.setAdapter(adapter);
                if (postsList.isEmpty())
                    recyclerView.setVisibility(View.VISIBLE);
                else recyclerView.setVisibility(View.GONE);
            }
        });


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
