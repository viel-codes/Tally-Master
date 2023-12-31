package com.labactivity.tallymaster;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addNewTally extends AppCompatActivity {

    EditText TallyNametxt, TallyGoaltxt, TallyIncrement;
    ImageButton saveTally, cancelTally;

    LinearLayout layout_cardview;

    ImageButton color1, color2, color3, color4, color5, color6, color7, color8, color9, color10;

    private int selectedColor = 0; // Default color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tally);

        TallyNametxt = findViewById(R.id.txt_counter_name);
        TallyGoaltxt = findViewById(R.id.txt_counter_goal);
        TallyIncrement = findViewById(R.id.txt_counter_increment);
        saveTally = findViewById(R.id.save_tally_btn);
        cancelTally = findViewById(R.id.cancel_tally_btn);

        color1 = findViewById(R.id.coloroption1_id);
        color2 = findViewById(R.id.coloroption2_id);
        color3 = findViewById(R.id.coloroption3_id);
        color4 = findViewById(R.id.coloroption4_id);
        color5 = findViewById(R.id.coloroption5_id);
        color6 = findViewById(R.id.coloroption6_id);
        color7 = findViewById(R.id.coloroption7_id);
        color8 = findViewById(R.id.coloroption8_id);
        color9 = findViewById(R.id.coloroption9_id);
        color10 = findViewById(R.id.coloroption10_id);


        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption1_pink); // Replace with your color resource
                applySelectedColor();
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption2_blue); // Replace with your color resource
                applySelectedColor();
            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption3_purple); // Replace with your color resource
                applySelectedColor();
            }
        });

        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption4_yellow); // Replace with your color resource
                applySelectedColor();
            }
        });

        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption5_green); // Replace with your color resource
                applySelectedColor();
            }
        });

        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption6_ocean); // Replace with your color resource
                applySelectedColor();
            }
        });

        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption7_sage); // Replace with your color resource
                applySelectedColor();
            }
        });

        color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption8_mustard); // Replace with your color resource
                applySelectedColor();
            }
        });

        color9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption9_red); // Replace with your color resource
                applySelectedColor();
            }
        });

        color10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColor(R.color.colorOption10_magenta); // Replace with your color resource
                applySelectedColor();
            }
        });



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

                    applySelectedColor();

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
                        TallyNametxt.getText().clear();
                        TallyGoaltxt.getText().clear();
                        TallyIncrement.getText().clear();
                    }
                });


            }
        });

        cancelTally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });



    }

    private void applySelectedColor() {

        TallyNametxt.setTextColor(selectedColor);
        TallyGoaltxt.setTextColor(selectedColor);
        TallyIncrement.setTextColor(selectedColor);

    }

}