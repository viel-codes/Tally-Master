package com.labactivity.tallymaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addNewTally extends AppCompatActivity {

    EditText TallyNametxt, TallyGoaltxt, TallyIncrement;
    ImageButton saveTally;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tally);

        TallyNametxt = findViewById(R.id.txt_counter_name);
        TallyGoaltxt = findViewById(R.id.txt_counter_goal);
        TallyIncrement = findViewById(R.id.txt_counter_increment);
        saveTally = findViewById(R.id.save_tally_btn);

        saveTally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_is = TallyNametxt.getText().toString();
                String goal_is = TallyGoaltxt.getText().toString();
                String increment_is = TallyIncrement.getText().toString();

                if (name_is.isEmpty()) {
                    TallyNametxt.setError("Tally Name is required");
                } else if (goal_is.isEmpty()) {
                    TallyGoaltxt.setError("Tally Goal is required");
                } else if (increment_is.isEmpty()) {
                    TallyIncrement.setError("Tally Increment is required");
                } else {
                    f_insert_record(name_is, goal_is, increment_is);

                    Intent intent = new Intent(addNewTally.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }
            }

            private void f_insert_record(String name_is,String goal_is, String increment_is) {

                HashMap<String,Object> data_hashmap = new HashMap<>();
                data_hashmap.put("b_title", name_is);
                data_hashmap.put("c_goal", goal_is);
                data_hashmap.put("d_increment", increment_is);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference tbl_reference = database.getReference("tally");

                String idkey = tbl_reference.push().getKey();
                data_hashmap.put("a_idno",idkey);

                tbl_reference.child(idkey).setValue(data_hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(addNewTally.this, "Tally Saved", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}