package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NuevaGuiaActivity extends AppCompatActivity {
    private EditText name_editText, platform_editText, company_editText;
    private Button guardar_button, limpiar_button, volver_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guia);
        name_editText = (EditText) findViewById(R.id.nueva_guia_name);
        platform_editText = (EditText) findViewById(R.id.nueva_guia_platform);
        company_editText = (EditText) findViewById(R.id.nueva_guia_company);



        // Botón Guardar
        guardar();
        // Limpiar Campos
        limpiarCampos();
        // Ir a LoginActivity
        volver();
    }

    /**
     * Guardar cambios
     *
     * Guarda los cambios
     */
    private void guardar()
    {
        guardar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * Limpiar campos
     *
     * Limpia los campos EditText y los deja vacíos
     */
    private void limpiarCampos()
    {
        limpiar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_editText.setText("");
                platform_editText.setText("");
                company_editText.setText("");
            }
        });
    }


    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NuevaGuiaActivity.this, GuidesActivity.class));
            }
        });
    }

}
