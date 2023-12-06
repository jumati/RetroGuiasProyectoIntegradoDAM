package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jm.retroguias.adapter.GuidesAdapter;
import com.jm.retroguias.adapter.UsersAdapter;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Users;

public class UsersActivity extends AppCompatActivity {

    private Button volver_button;

    private RecyclerView recycler;
    private UsersAdapter usersAdapter;
    private FirebaseFirestore firestore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        volver_button = (Button) findViewById(R.id.users_volver_button);

        firestore = FirebaseFirestore.getInstance();
        recycler = (RecyclerView) findViewById(R.id.users_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        Query users_query = firestore.collection("Users");

        FirestoreRecyclerOptions<Users> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Users>()
                        .setQuery(users_query, Users.class).build();

        usersAdapter = new UsersAdapter(firestoreRecyclerOptions);
        usersAdapter.notifyDataSetChanged();
        recycler.setAdapter(usersAdapter);

        usersAdapter.setOnClickListener(new UsersAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Users user) {
                Intent intent = new Intent(UsersActivity.this, EditarUsuarioActivity.class);
                intent.putExtra("user_id", user.getId());
                intent.putExtra("user_name", user.getName());
                intent.putExtra("user_last_name", user.getLast_name());
                intent.putExtra("user_email", user.getEmail());
                intent.putExtra("user_phone", user.getPhone());
                startActivity(intent);
            }
        });

        // Ir a LoginActivity
        volver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersAdapter.stopListening();
    }


    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersActivity.this, GuidesActivity.class));
            }
        });
    }
}
