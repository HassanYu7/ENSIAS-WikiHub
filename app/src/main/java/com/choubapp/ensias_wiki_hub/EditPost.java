package com.choubapp.ensias_wiki_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.choubapp.ensias_wiki_hub.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {
    EditText content;
    TextInputEditText title;
    String docId;
    Question question=null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docId = getIntent().getStringExtra("POST_DOCUMENT_ID");
        setContentView(R.layout.activity_edit_post);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        loadDetails();
    }

    private void loadDetails() {
        db.collection("Post").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                question= task.getResult().toObject(Question.class);
                title.setText(question.getTitle());
                content.setText(question.getContent());
            }
        });
    }


    public void EditQuestion (View v){
        Map<String,Object> updates = new HashMap<>();
        updates.put("title", title.getText().toString());
        updates.put("content", content.getText().toString());
        db.collection("Post").document(docId).update(updates);
        finish();

    }
}
