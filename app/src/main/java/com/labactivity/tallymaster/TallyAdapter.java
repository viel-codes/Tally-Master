package com.labactivity.tallymaster;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class TallyAdapter extends FirebaseRecyclerAdapter<TallyModel, TallyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public TallyAdapter(@NonNull FirebaseRecyclerOptions<TallyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull TallyModel model) {
        holder.title.setText(model.getB_title());
        holder.goal.setText(model.getC_goal());
        holder.increment.setText("0"); // Set the initial value of increment to 0

        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int currentIncrement = Integer.parseInt(model.getD_increment());
                int currentDisplayValue = Integer.parseInt(holder.increment.getText().toString());
                int newDisplayValue = currentDisplayValue + currentIncrement;

                holder.increment.setText(String.valueOf(newDisplayValue));

            }

        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Warning");
                builder.setMessage("Are you sure you want to delete this tally?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)

                    {
                        FirebaseDatabase.getInstance().getReference("tally")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(builder.getContext(), "Deletion cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new ViewHolder(R.layout.show_update))
                        .setExpanded(true, 1000)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText title = view.findViewById(R.id.edit_name);
                EditText goal = view.findViewById(R.id.edit_goal);
                EditText increment = view.findViewById(R.id.edit_increment);
                ImageButton update = view.findViewById(R.id.btn_id_update);
                ImageButton cancel = view.findViewById(R.id.btn_id_cancel);


                title.setText(model.getB_title());
                goal.setText(model.getC_goal());
                increment.setText(model.getD_increment());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_is = title.getText().toString().trim();
                        String goal_is = goal.getText().toString().trim();
                        String increment_is = increment.getText().toString().trim();

                        if (name_is.isEmpty()) {
                            title.setError("Tally Name is required");
                        } else if (goal_is.isEmpty()) {
                            goal.setError("Tally Goal is required");
                        } else if (increment_is.isEmpty()) {
                            increment.setError("Tally Increment is required");
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("b_title", name_is);
                            map.put("c_goal", goal_is);
                            map.put("d_increment", increment_is);

                            FirebaseDatabase.getInstance().getReference().child("tally")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.title.getContext(), "Tally Updated!", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss(); // Dismiss the dialog after successful update
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.title.getContext(), "Error updating tally", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.title.getContext(), MainActivity.class);

                        // Add any additional flags or data if needed
                        // intent.putExtra("key", "value");

                        // Start the MainActivity
                        holder.title.getContext().startActivity(intent);
                    }
                });
            }
        });
    }



    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    private OnEditClickListener editClickListener;

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_counter,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView title, goal, increment;
        ImageButton btnDelete, btnEdit, btnDecrement, btnIncrement;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.id_name_tallies);
            goal = itemView.findViewById(R.id.txt_goal_id);
            increment = itemView.findViewById(R.id.txt_counter_id);
            btnDelete = itemView.findViewById(R.id.btn_delete_id);
            btnEdit = itemView.findViewById(R.id.btn_edit_btn);
            btnDecrement= itemView.findViewById(R.id.btn_decrement_id);
            btnIncrement= itemView.findViewById(R.id.btn_increment_id);


        }
    }



}
