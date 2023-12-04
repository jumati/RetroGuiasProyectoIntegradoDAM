package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText name_editText, last_name_editText, email_editText, phone_editText,
            old_password_editText, new_password_editText, confirm_password_editText;
    private Button guardar_button, volver_button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Se recogen los campos
        //name_editText = (EditText) findViewById(R.id.editar_usuario_name);
        last_name_editText = (EditText) findViewById(R.id.editar_usuario_last_name);
        email_editText = (EditText) findViewById(R.id.editar_usuario_email);
        old_password_editText = (EditText) findViewById(R.id.editar_usuario_old_password);
        new_password_editText = (EditText) findViewById(R.id.editar_usuario_new_password);
        confirm_password_editText = (EditText) findViewById(R.id.editar_usuario_confirm_new_password);
        phone_editText = (EditText) findViewById(R.id.editar_usuario_phone);
        guardar_button = (Button) findViewById(R.id.editar_usuario_guardar_cambios_button);
        volver_button = (Button) findViewById(R.id.editar_usuario_volver_button);


        // Guardar cambios
        guardar();
        // Ir a LoginActivity
        volver();
    } // Fin onCreate


    /**
     * Guarda los cambios
     */
    private void guardar()
    {

    }

    /**
     * Rellena los campos
     *
     * Recoge los campos de la base de datos y los aplica a los campos
     */
    private void relenarCampos()
    {
        String name, apellidos, email;
        int phone;



    }




    /**
     * Vuelve a GuidesActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditarUsuarioActivity.this, GuidesActivity.class));
            }
        });
    }
}
