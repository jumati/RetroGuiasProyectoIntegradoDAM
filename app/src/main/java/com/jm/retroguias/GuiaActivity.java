package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Users;

public class GuiaActivity extends AppCompatActivity {

    Bundle bundle;
    DatabaseReference ref;
    TextView title_textView,platform_textView, company_textView;
    String guide_title, platform_id, company_id;
    private Button volver_button, eliminar_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);
        Intent intent = getIntent();

        title_textView = (TextView) findViewById(R.id.guia_title_guia);
        platform_textView = (TextView) findViewById(R.id.guia_platform);
        company_textView = (TextView) findViewById(R.id.guia_company);
        eliminar_button = (Button) findViewById(R.id.guia_eliminar_guia);
        volver_button = (Button) findViewById(R.id.guides_volver_button);

        /*
        // El bundle recoge los campos del Intent de la pantalla GuidesActivity para mostrar la guía.
        bundle = getIntent().getExtras();
        if(bundle != null)
        {
            title_textView = findViewById(R.id.guia_title);
            platform_textView = findViewById(R.id.guia_platform);
            //company_textView = findViewById(R.id.guia_company);
            guide_title = findViewById(R.id.guia_title_guia).toString();
            platform_id = findViewById(R.id.guia_platform).toString();
            //company_id = findViewById(R.id.guia_company).toString();
        }
        */


        // Código para GuidesActivity y GuidesAdapter con Firestore.
        title_textView.setText(intent.getStringExtra("guideName"));
        platform_textView.setText(intent.getStringExtra("platformName"));
        company_textView.setText(intent.getStringExtra("companyName"));

        // Eliminar guía
        eliminarGuia();
        // Ir a LoginActivity
        volver();
    } // Fin onCreate


    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuiaActivity.this, GuidesActivity.class));
            }
        });
    }

    /**
     * Eliminar Usuario
     *
     * Lleva a una pantalla donde se confirman el email y la contraseña del usuario.
     * Una vez condirmado, se elimina el usuario.
     */
    private void eliminarGuia()
    {
        eliminar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Query query = ref.orderByChild("guide_name")
                        .equalTo(title_textView.getText().toString().trim());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren())
                        {
                            // Recogemos los datos de la guía
                            Guides guide = snap.getValue(Guides.class);
                            // Borramos la guía
                            ref.child(guide.getGuide_id()).removeValue();

                            // cambiamos de pantalla a GuidesActivity
                            startActivity(new Intent(GuiaActivity.this, GuidesActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Si se produce algún error en el proceso, se muestra un mensaje.
                        Toast.makeText(GuiaActivity.this,
                                "Se ha producido un error al eliminar la guía.",
                                Toast.LENGTH_LONG).show();
                    }
                });
                 */
            }
        });
    }
}