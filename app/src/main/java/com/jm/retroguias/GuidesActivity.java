package com.jm.retroguias;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jm.retroguias.adapter.GuidesAdapter;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Users;

import java.util.ArrayList;
import java.util.List;

public class GuidesActivity extends AppCompatActivity {

    // private Button recargar_button, logout_button, editar_usuario_button, favoritos_button, usuarios_button;

    private RecyclerView recycler;
    GridLayoutManager gridLayoutManager;
    private GuidesAdapter guidesAdapter;
    private FirebaseFirestore firestore;
    private FirebaseDatabase database;
    private DatabaseReference refGuides;
    ValueEventListener eventListener;
    private Users user;
    List<Guides> guidesList;
    boolean master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);


        /*
        // Listado de guías con Firebase Realtime Database
        // Recycler
        recycler = findViewById(R.id.guides_recycler);
        gridLayoutManager = new GridLayoutManager(GuidesActivity.this, 1);
        recycler.setLayoutManager(gridLayoutManager);

        guidesList= new ArrayList<>();

        guidesAdapter = new GuidesAdapter(GuidesActivity.this, guidesList);
        recycler.setAdapter(guidesAdapter);

        refGuides = FirebaseDatabase.getInstance().getReference("Guides");

        eventListener = refGuides.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                guidesList.clear();
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    Guides guide = snap.getValue(Guides.class);
                    guidesList.add(guide);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Se muestra un mensaje si algo falla.
                Toast.makeText(GuidesActivity.this,
                        "Se ha producido un error en la lista de guías.",
                        Toast.LENGTH_LONG).show();
            }
        });

         */

        // Listado de guías con Firestore.
        // Funciona correctamente pero he decidido hacer la base de datos con Firebase Realtime Database
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
        guidesAdapter.setOnClickListener(new GuidesAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Guides guide) {
                Intent intent = new Intent(GuidesActivity.this, GuiaActivity.class);
                intent.putExtra("guideName", guide.getGuide_name());
                intent.putExtra("platformName", guide.getPlatform_id());
                intent.putExtra("companyName", guide.getCompany_id());
                startActivity(intent);
            }
        });


        // Oculta o muestra accedos del toolbar dependiendo de si es master o no
        master = Boolean.parseBoolean(getIntent().getStringExtra("esMaster"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guides_menu, menu);

        // Oculta o muestra accedos del toolbar dependiendo de si es master o no
        master = Boolean.parseBoolean(getIntent().getStringExtra("esMaster"));

        if(!master)
        {
            MenuItem new_guide = menu.findItem(R.id.menu_new_guide);
            MenuItem lista_usuarios = menu.findItem(R.id.menu_lista_usuarios);
            new_guide.setVisible(false);
            lista_usuarios.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_editar_usuario)
        {
            startActivity(new Intent(GuidesActivity.this, EditarUsuarioActivity.class));
        }
        else if (id == R.id.menu_lista_usuarios)
        {
            startActivity(new Intent(GuidesActivity.this, UsersActivity.class));
        }
        else if (id == R.id.menu_logout)
        {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(GuidesActivity.this, LoginActivity.class));
        }
        else if (id == R.id.menu_new_guide)
        {
            // Solo si es Master
            FirebaseAuth auth = FirebaseAuth.getInstance();
            startActivity(new Intent(GuidesActivity.this, NuevaGuiaActivity.class));
        }
        /*
        else if (id == R.id.menu_favoritos)
        {
            startActivity(new Intent(GuidesActivity.this, FavoritosActivity.class));
        }
         */
        return true;

        /*
        // Debido a un problema con las últimas versiones de Android, el switch()
        // da errores al reconocer el id de los menu y las barras con items.
        switch (item.getItemId())
        {
            case R.id.menu_editar_usuario:
                startActivity(new Intent(GuidesActivity.this, EditarUsuarioActivity.class));
                break;
            case R.id.menu_favoritos:
                startActivity(new Intent(GuidesActivity.this, FavoritosActivity.class));
                break;
            case R.id.menu_lista_usuarios:
                startActivity(new Intent(GuidesActivity.this, UsersActivity.class));
                break;
            case R.id.menu_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(GuidesActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
         */
    }


    // Métodos necesarios para Firestore
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


    /**
     * Ocultar funcionalidades isMaster
     *
     * Si no es máster, se ocultarán algunas funcionalidades.
     */
    private void isMasterFunction()
    {
        database = FirebaseDatabase.getInstance().getReference().getDatabase();


    }











}
