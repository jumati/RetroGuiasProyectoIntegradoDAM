package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jm.retroguias.model.Companies;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Platforms;
import com.jm.retroguias.model.Users;

import java.io.File;
import java.util.UUID;

public class NuevaGuiaActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference guideRef, platformRef, companyRef;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private EditText name_editText, platform_editText, company_editText;
    private Button guardar_button, limpiar_button, volver_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guia);
        name_editText = (EditText) findViewById(R.id.nueva_guia_name);
        platform_editText = (EditText) findViewById(R.id.nueva_guia_platform);
        company_editText = (EditText) findViewById(R.id.nueva_guia_company);
        guardar_button = (Button) findViewById(R.id.nueva_guia_guardar);
        limpiar_button = (Button) findViewById(R.id.nueva_guia_limpiar);
        volver_button = (Button) findViewById(R.id.nueva_guia_volver_button);

        FirebaseApp.initializeApp(this);


        // Botón Guardar
        guardar();
        // Limpiar Campos
        limpiarCampos();
        // Ir a LoginActivity
        volver();
    }

    /**
     * Registra la nueva guía.
     *
     * Recibe los id de la plataforma y la compañía.
     * Posteriormente almacena la nueva guía en la base de datos.
     */
    private void registrarGuia(String platform_id, String company_id)
    {
        File pdf = null;
        Guides guide = new Guides(name_editText.getText().toString().trim(),
                platform_id, company_id, pdf);

        platformRef.child("Guides")
                .child(guide.getGuide_id()).setValue(guide).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            // Se muestra un mensaje de que el usuario se ha registrado correctamente.
                            Toast.makeText(NuevaGuiaActivity.this,
                                    "Guía registrada correctamente.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(NuevaGuiaActivity.this,
                                    "Se ha producido un error al registrar la guía.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * Registra la nueva plataforma.
     *
     * Registra una nueva plataforma.
     * Devuelve la id de la nueva plataforma.
     */
    private String registrarPlataforma()
    {
        Platforms platform;
        platform = new Platforms(platform_editText.getText().toString().trim());

        platformRef.child("Platforms")
                .child(platform.getPlatform_id()).setValue(platform);

        return platform.getPlatform_id();
    }

    /**
     * Registra la nueva compañía.
     *
     * Registra una nueva compañía.
     * Devuelve la id de la nueva compañía.
     */
    private String registrarCompany()
    {
        Companies company;
        company = new Companies(company_editText.getText().toString().trim());

        // Si la guía no existe, se registrará una nueva.
        if(true)
        {
            companyRef.child("Companies")
                    .child(company.getCompany_id()).setValue(company);
        }

        return company.getCompany_id();
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

                // Validaciones
                if(!isEmptyValidation())
                {
                    if(sizeNameValidation())
                    {
                        // Se insertan los datos
                    }
                }
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

    /*
     * VALIDACIONES
     */

    /**
     * Validación campos vacíos.
     *
     * Comprueba que todos los campos de esta Activity estén rellenos.
     * En caso de haber uno o más campos vacíos, mostrará un mensaje.
     */
    private boolean isEmptyValidation() {
        boolean empty = false;
        if (name_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            name_editText.setError("Introduce tu nombre.");
        }

        if(platform_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            platform_editText.setError("Introduce tus apellidos.");
        }

        if(company_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            company_editText.setError("Introduce un email.");
        }

        if(empty)
        {
            // Si hay campos vacíos se muestra un mensaje.
            Toast.makeText(NuevaGuiaActivity.this,
                    "No se han rellenado todos los campos",
                    Toast.LENGTH_LONG).show();
        }
        return empty;
    }

    /**
     * Validar longitud name, platform_editText y company_editText.
     *
     * Se pondrá una longitud mínima y máxima para cada campo.
     */
    private boolean sizeNameValidation()
    {
        boolean validated = true;

        // Validación para name_editText
        if(name_editText.getText().toString().trim().length() > 24
                || name_editText.getText().toString().trim().length() < 4)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            name_editText.setError("El nombre de la guía debe contener entre 4 y 24 caracteres.");
            // Se limpia el campo
            name_editText.setText("");
        }

        // Validación para platform_editText
        if(platform_editText.getText().toString().trim().length() > 14
                || platform_editText.getText().toString().trim().length() < 3)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            platform_editText.setError("El nombre de la plataforma debe contener entre 3 y 14 caracteres.");
            // Se limpia el campo
            platform_editText.setText("");
        }

        // Validación para company_editText
        if(company_editText.getText().toString().trim().length() > 14
                || company_editText.getText().toString().trim().length() < 3)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            company_editText.setError("El nombre de la compañía debe contener entre 3 y 14 caracteres.");
            // Se limpia el campo
            company_editText.setText("");
        }

        return validated;
    }

    /*
     * FIN VALIDAICIONES
     */


}
