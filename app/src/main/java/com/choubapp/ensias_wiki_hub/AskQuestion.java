package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AskQuestion extends AppCompatActivity {
    private String title, content, owner;
    TextView mTitle, mContent;
    private int vote;
    private Date datePosted;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
    }


    public void AddQuestion(View v){
        title= mTitle.getText().toString();
        content= mContent.getText().toString();
        vote=0;
        datePosted = new Date();
        owner= user.getUid();
        Question question = new Question(title,content,owner,vote,datePosted,null);
        db.collection("Post").add(question).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AskQuestion.this, "Registrated successfully", Toast.LENGTH_SHORT).show();
                    moveon();
                }else{
                    Toast.makeText(AskQuestion.this, "Registrated Not Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void moveon(){

        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }

}
