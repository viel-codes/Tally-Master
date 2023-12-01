package com.labactivity.tallymaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addTallyBtn;
    TallyAdapter tallyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTallyBtn = findViewById(R.id.add_tally_btn);
        recyclerView = findViewById(R.id.rv_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TallyModel> options =
                new FirebaseRecyclerOptions.Builder<TallyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tally"), TallyModel.class)
                        .build();

        tallyAdapter = new TallyAdapter(options);
        recyclerView.setAdapter(tallyAdapter);

        addTallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, addNewTally.class));


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        tallyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tallyAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void txtSearch(String str) {
        FirebaseRecyclerOptions<TallyModel> options =
                new FirebaseRecyclerOptions.Builder<TallyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tally")
                                .orderByChild("b_title").startAt(str).endAt(str + "~"), TallyModel.class)
                        .build();

        tallyAdapter = new TallyAdapter(options);
        tallyAdapter.startListening();
        recyclerView.setAdapter(tallyAdapter);
    }
}