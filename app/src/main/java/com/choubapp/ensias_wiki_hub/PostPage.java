package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.Answer;
import com.choubapp.ensias_wiki_hub.model.Question;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostPage extends AppCompatActivity {
    TextView questionTitle, questionContent, questionOwner, questionVote, questionDate, answer;
    String docId, answerContent;
    Question question=null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Answer mAnswer;
    private AnswerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docId = getIntent().getStringExtra("POST_DOCUMENT_ID");
        setContentView(R.layout.activity_post_page);
        questionTitle=findViewById(R.id.question_title);
        questionContent=findViewById(R.id.question_content);
        questionOwner=findViewById(R.id.question_owner);
        questionVote=findViewById(R.id.question_vote);
        questionDate=findViewById(R.id.question_date);
        answer = findViewById(R.id.answer);
        loadQuestion();
        loadAnswers();
    }

    private void loadAnswers() {
        Query query = db.collection("Post").document(docId).collection("Answers").orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Answer> options = new FirestoreRecyclerOptions.Builder<Answer>()
                .setQuery(query, Answer.class)
                .build();
        adapter= new AnswerAdapter(options);
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

    private void loadQuestion() {
        db.collection("Post").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                question= task.getResult().toObject(Question.class);
                questionTitle.setText(question.getTitle());
                questionContent.setText(question.getContent());
                questionVote.setText(String.valueOf(question.getVote()));
                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                questionDate.setText(newFormat.format(question.getDate()));
                questionOwner.setText(question.getTitle());
            }
        });
    }

    public void AddAnswer(View v){
        answerContent= answer.getText().toString();
        mAnswer = new Answer(answerContent, FirebaseAuth.getInstance().getCurrentUser().getUid(), 0, new Date());
        db.collection("Post").document(docId).collection("Answers").add(mAnswer).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PostPage.this, "Registrated successfully", Toast.LENGTH_SHORT).show();
                    answer.setText("");
                }else{
                    Toast.makeText(PostPage.this, "Registrated Not Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
