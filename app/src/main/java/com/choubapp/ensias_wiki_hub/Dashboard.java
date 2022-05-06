package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.Answer;
import com.choubapp.ensias_wiki_hub.model.Question;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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
        final EditText edittext = (EditText) findViewById(R.id.editMobileNo);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //Toast.makeText(Dashboard.this, "loading ...", Toast.LENGTH_SHORT).show();
                    searchPost(edittext.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void searchPost(final String text) {
        //setUpRecyclerView(query);
        Query query = postsCollection.orderBy("title").startAt(text).limit(100);
        FirestoreRecyclerOptions<PostItem> options = new FirestoreRecyclerOptions.Builder<PostItem>()
                .setQuery(query, PostItem.class)
                .build();
        adapter.updateOptions(options);
        //adapter.notifyDataSetChanged();
    }


    private void setUpRecyclerView(){
        Query query = postsCollection.orderBy("date", Query.Direction.DESCENDING).limit(10);
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
                //Toast.makeText(Dashboard.this,"Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Dashboard.this,PostPage.class);
                intent.putExtra("POST_DOCUMENT_ID", id);
                startActivity(intent);
            }
        });

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.editMobileNo);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "SEARCH " + query, Toast.LENGTH_LONG).show();
                searchUsers(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, "SEARCH " + newText, Toast.LENGTH_LONG).show();
                searchUsers(newText);
                return false;
            }
        });
        return true;
    }
    private void searchUsers(String recherche) {
        if (recherche.length() > 0)
            recherche = recherche.substring(0, 1).toUpperCase() + recherche.substring(1).toLowerCase();

        ArrayList<Question> results = new ArrayList<>();
        for(Question user : listUsers){
            if(user.getName() != null && user.getName().contains(recherche)){
                results.add(user);
            }
        }
        updateListUsers(results);
    }
    private void updateListUsers(ArrayList<Question> listUsers) {

        // Sort the list by date
        Collections.sort(listUsers, new Comparator<Question>() {
            @Override
            public int compare(Question o1, User o2) {
                int res = -1;
                if (o1.getDate() > (o2.getDate())) {
                    res = 1;
                }
                return res;
            }
        });

        userRecyclerAdapter = new UserRecyclerAdapter(listUsers, InvitationActivity.this, this);
        rvUsers.setNestedScrollingEnabled(false);
        rvUsers.setAdapter(userRecyclerAdapter);
        layoutManagerUser = new LinearLayoutManager(getApplicationContext());
        rvUsers.setLayoutManager(layoutManagerUser);
        userRecyclerAdapter.notifyDataSetChanged();
    }
    private void getUsers() {
        db.collection("users").whereEqualTo("etat", 1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Listen failed:" + e);
                            return;
                        }
                        listUsers = new ArrayList<User>();

                        for (DocumentSnapshot doc : snapshots) {
                            User user = doc.toObject(User.class);
                            listUsers.add(user);
                        }
                        updateListUsers(listUsers);
                    }
                });
    }
*/




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

    public void profile(View v){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
    }

    public void newQuestion(View v){
        Intent intent = new Intent(this,AskQuestion.class);
        startActivity(intent);
    }
}
