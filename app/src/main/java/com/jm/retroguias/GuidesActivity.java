package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jm.retroguias.adapter.GuidesAdapter;
import com.jm.retroguias.model.Guides;

public class GuidesActivity extends AppCompatActivity {

    private Button recargar_button, logout_button, editar_usuario_button,
    favoritos_button, usuarios_button;
    private RecyclerView recycler;
    private GuidesAdapter guidesAdapter;
    private FirebaseFirestore firestore;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);

        editar_usuario_button = (Button) findViewById(R.id.guides_editar_usuario_button);
        favoritos_button = (Button) findViewById(R.id.guides_favoritos_button);
        usuarios_button = (Button) findViewById(R.id.guides_usuarios_button);
        recargar_button = (Button) findViewById(R.id.guides_recargar_button);
        logout_button = (Button) findViewById(R.id.guides_logout_button);

        firestore = FirebaseFirestore.getInstance();
        recycler = (RecyclerView) findViewById(R.id.guides_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = firestore.collection("Guides");

        FirestoreRecyclerOptions<Guides> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Guides>()
                .setQuery(query, Guides.class).build();

        guidesAdapter = new GuidesAdapter(firestoreRecyclerOptions);
        guidesAdapter.notifyDataSetChanged();
        recycler.setAdapter(guidesAdapter);


        // Ir a Editar Usuario
        GoEditarUsuario();
        // Ir a Favoritos
        GoFavoritos();
        // Ir a Usuarios
        GoUsuarios();
        // Desloguea al usuario y vuelve a LoginActivity
        LogOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        guidesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        guidesAdapter.stopListening();
    }

    /*
     * CAMBIOS DE PANTALLA
     */

    /**
     * Ir a EditarUsuarioActivity
     */
    private void GoEditarUsuario()
    {
        editar_usuario_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuidesActivity.this, EditarUsuarioActivity.class));
            }
        });
    }

    /**
     * Ir a FavoritosActivity
     */
    private void GoFavoritos()
    {
        favoritos_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuidesActivity.this, FavoritosActivity.class));
            }
        });
    }

    /**
     * Ir a UsuariosActivity
     */
    private void GoUsuarios()
    {
        usuarios_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuidesActivity.this, UsersActivity.class));
            }
        });
    }

    /**
     * Cerrar Sesión
     *
     * Cierra la sesión del usuario actual y devuelve a LoginActivity
     */
    private void LogOut()
    {
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(GuidesActivity.this, LoginActivity.class));
            }
        });
    }


    /*
     * FIN CAMBIOS DE PANTALLA
     */
}
