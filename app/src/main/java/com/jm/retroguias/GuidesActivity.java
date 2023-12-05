package com.jm.retroguias;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
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
    private ClipData.Item editar_usuario_item, favoritos_item, usuarios_item, logout_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guides_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_editar_usuario)
        {
            startActivity(new Intent(GuidesActivity.this, EditarUsuarioActivity.class));
        }
        else if (id == R.id.menu_favoritos)
        {
            startActivity(new Intent(GuidesActivity.this, FavoritosActivity.class));
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
        return true;

        /*
        // Debido a un error de las últimas versiones de Android, el switch()
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


}
