package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserProfile extends AppCompatActivity {
    TextView name, email;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PostAdapter adapter;
    CollectionReference postsCollection = db.collection("Post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        setUpRecyclerView();
        db.collection("User").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot==null){
                    Toast.makeText(UserProfile.this, "Error Try again", Toast.LENGTH_SHORT).show();
                }else{
                    name.setText(documentSnapshot.get("nom")+" "+documentSnapshot.get("prenom"));
                    email.setText(documentSnapshot.get("email").toString());
                }
            }
        });
    }
    private void setUpRecyclerView(){
        Query query = postsCollection.whereEqualTo("owner",FirebaseAuth.getInstance().getCurrentUser().getUid()).orderBy("date", Query.Direction.DESCENDING);
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
}
