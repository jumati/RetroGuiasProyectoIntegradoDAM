package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FavoritosActivity extends AppCompatActivity {

    private Button volver_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        // volver_button = (Button) findViewById(R.id.);

        // Ir a LoginActivity
        volver();
    }



    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoritosActivity.this, GuidesActivity.class));
            }
        });
    }
}
