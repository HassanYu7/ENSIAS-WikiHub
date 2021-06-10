package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.choubapp.ensias_wiki_hub.model.Answer;
import com.choubapp.ensias_wiki_hub.model.Question;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pchmn.materialchips.ChipView;
import com.pchmn.materialchips.ChipsInput;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.GONE;

public class PostPage extends AppCompatActivity {
    TextView questionTitle, questionContent, questionOwner, questionVote, questionDate, answer;
    String docId, answerContent;
    Question question=null;
    LinearLayout edit, tags ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Answer mAnswer;
    ChipView chip1, chip2, chip3, chip4, chip5;
    private AnswerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docId = getIntent().getStringExtra("POST_DOCUMENT_ID");
        setContentView(R.layout.activity_post_page);
        edit = findViewById(R.id.edit_question);
        questionTitle=findViewById(R.id.question_title);
        questionContent=findViewById(R.id.question_content);
        questionOwner=findViewById(R.id.question_owner);
        questionVote=findViewById(R.id.question_vote);
        questionDate=findViewById(R.id.question_date);
        answer = findViewById(R.id.answer);

        tags= findViewById(R.id.tags);
        chip1 = findViewById(R.id.chip1);
        chip2 = findViewById(R.id.chip2);
        chip3 = findViewById(R.id.chip3);
        chip4 = findViewById(R.id.chip4);
        chip5 = findViewById(R.id.chip5);

        loadQuestion();
        loadAnswers();
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
                if (question.getTags()==null){
                    tags.setVisibility(GONE);
                }else{
                    switch (question.getTags().size()){
                        case 1:
                            chip1.setLabel(question.getTags().get(0));
                            chip2.setVisibility(GONE);
                            chip3.setVisibility(GONE);
                            chip4.setVisibility(GONE);
                            chip5.setVisibility(GONE);
                            //chip2.setLabel(question.getTags().get(1));
                            //chip3.setLabel(question.getTags().get(2));
                            //chip4.setLabel(question.getTags().get(3));
                            //chip5.setLabel(question.getTags().get(4));
                            break;
                        case 2:
                            chip1.setLabel(question.getTags().get(0));
                            chip2.setLabel(question.getTags().get(1));
                            chip3.setVisibility(GONE);
                            chip4.setVisibility(GONE);
                            chip5.setVisibility(GONE);
                            //chip3.setLabel(question.getTags().get(2));
                            //chip4.setLabel(question.getTags().get(3));
                            //chip5.setLabel(question.getTags().get(4));
                            break;
                        case 3:
                            chip1.setLabel(question.getTags().get(0));
                            chip2.setLabel(question.getTags().get(1));
                            chip3.setLabel(question.getTags().get(2));
                            chip4.setVisibility(GONE);
                            chip5.setVisibility(GONE);
                            //chip4.setLabel(question.getTags().get(3));
                            //chip5.setLabel(question.getTags().get(4));
                            break;

                        case 4:
                            chip1.setLabel(question.getTags().get(0));
                            chip2.setLabel(question.getTags().get(1));
                            chip3.setLabel(question.getTags().get(2));
                            chip4.setLabel(question.getTags().get(3));
                            chip5.setVisibility(GONE);
                            //chip5.setLabel(question.getTags().get(4));
                            break;

                        case 5:
                            chip1.setLabel(question.getTags().get(0));
                            chip2.setLabel(question.getTags().get(1));
                            chip3.setLabel(question.getTags().get(2));
                            chip4.setLabel(question.getTags().get(3));
                            chip5.setLabel(question.getTags().get(4));
                            break;
                    }

                }
                if (!question.getOwner().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    edit.setVisibility(GONE);
                }
                db.collection("User").document(question.getOwner()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        questionOwner.setText(documentSnapshot.get("nom")+" "+documentSnapshot.get("prenom"));
                    }
                });
            }
        });
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

    public void AddAnswer(View v){
        answerContent= answer.getText().toString();
        db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mAnswer = new Answer(answerContent, documentSnapshot.get("nom")+" "+documentSnapshot.get("prenom"), 0, new Date());
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
        });
    }

    public void editquestion (View v){
        Intent intent = new Intent(this,EditPost.class);
        intent.putExtra("POST_DOCUMENT_ID", docId);
        startActivity(intent);
    }



    public void vote( View v){
        db.collection("Post").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Question qst = task.getResult().toObject(Question.class);
                int voteCount = qst.getVote() + 1;
                updateVoteCount(voteCount);
                questionVote.setText(""+voteCount);
                questionVote.setTextColor(getResources().getColor(R.color.colorPrimary));
                ImageView arrow = findViewById(R.id.vote);
                arrow.setColorFilter(ContextCompat.getColor(PostPage.this, R.color.colorPrimary));
                setVoter();
            }
        });
    }

    private void setVoter() {
    }

    public void updateVoteCount(int vote){
        db.collection("Post").document(docId).update("vote",vote);
    }
}
