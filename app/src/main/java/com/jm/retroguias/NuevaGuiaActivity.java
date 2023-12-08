package com.jm.retroguias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jm.retroguias.model.Companies;
import com.jm.retroguias.model.Guides;
import com.jm.retroguias.model.Platforms;
import com.jm.retroguias.model.Users;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NuevaGuiaActivity extends AppCompatActivity {

    // Al utilizar un único DatabaseReference, los datos se mezclan.
    // Al separar un DatabaseReference para cada tabla funciona correctamente.
    DatabaseReference guideRef, platformRef, companyRef, storageRef;
    StorageReference storageReference;
    FirebaseDatabase database;
    private EditText name_editText, select_file;
    private Button guardar_button, limpiar_button, volver_button;
    private String platform_selected, company_selected;
    Spinner spinner_platform, spinner_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_guia);
        name_editText = (EditText) findViewById(R.id.nueva_guia_name);
        select_file = (EditText) findViewById(R.id.nueva_guia_select_file);
        // Botones
        guardar_button = (Button) findViewById(R.id.nueva_guia_guardar);
        limpiar_button = (Button) findViewById(R.id.nueva_guia_limpiar);
        volver_button = (Button) findViewById(R.id.nueva_guia_volver_button);
        // Spiner de Plataformas
        spinner_platform = (Spinner) findViewById(R.id.nueva_guia_spinner_platform);
        platformRef = FirebaseDatabase.getInstance().getReference();
        // Spiner de Compañías
        spinner_company = (Spinner) findViewById(R.id.nueva_guia_spinner_company);
        companyRef = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(this);
        // Conexión con la base de datos
        database = FirebaseDatabase.getInstance();
        // Referencia a la tabla Users de la clase Users.java
        guideRef = database.getReference(Guides.class.getSimpleName());


        // PDF
        // Conexión con Storage
        storageReference = FirebaseStorage.getInstance().getReference();
        storageRef = FirebaseDatabase.getInstance().getReference("Guides");

        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });


        spinnerPlatform();
        spinnerCompany();

        // Botón Guardar
        guardar();
        // Limpiar Campos
        limpiarCampos();
        // Ir a LoginActivity
        volver();
    } // onCreate



    /**
     * Registra la nueva guía.
     *
     * Recibe los id de la plataforma y la compañía.
     * Posteriormente almacena la nueva guía en la base de datos.
     */
    private void registrarGuia(String platform_id, String company_id)
    {
        Guides guide = new Guides(UUID.randomUUID().toString(), name_editText.getText().toString().trim(),
                platform_id, company_id);

        // Conexión con la base de datos
        database = FirebaseDatabase.getInstance();
        // Referencia a la tabla Users de la clase Users.java
        guideRef = database.getReference(Guides.class.getSimpleName());

        guideRef.child(guide.getGuide_id()).setValue(guide).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    // Se muestra un mensaje de que el usuario se ha registrado correctamente.
                    Toast.makeText(NuevaGuiaActivity.this,
                            "Guía registrada correctamente.",
                            Toast.LENGTH_LONG).show();
                    // Limpiamos el campo name_editText
                    name_editText.setText("");
                }
                else
                {
                    Toast.makeText(NuevaGuiaActivity.this,
                            "Se ha producido un error al registrar la guía.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    } // Fin registrarGuia()


    /**
     * Guardar cambios
     *
     * Registra la nueva guía en la base de datos seleccionando la plataforma y compañía
     * Se llama al método registrarGuia al que se le añaden como parámetros
     * los métodos registrarPlataforma() y registrarCompany() que devuelven las id
     * de cada dato.
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
                        // Se llama al método que registra las guías introduciendo
                        // como parámetros las id de la plataforma y la compañía seleccionadas.
                        registrarGuia(platform_selected, company_selected);
                    }
                }
            }
        });
    }



    /*
     *  PDF
     */
    
    /**
     * Seleccionar el .PDF
     */
     private void selectPDF()
     {
     Intent intent = new Intent();
     intent.setType("application/pdf");
     intent.setAction(intent.ACTION_GET_CONTENT);
     startActivityForResult(Intent.createChooser(intent, "PDF Seleccionado."), 12);
     }

    /**
     * onActivityResult - Guardar cambios
     *
     * Registra la nueva guía en la base de datos seleccionando la plataforma y compañía
     * Primero crea el archivo seleccionado en formato .pdf,
     * después se llama al método registrarGuia al que se le añaden como parámetros
     * los métodos registrarPlataforma() y registrarCompany() que devuelven las id
     * de cada dato.
     */
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
             select_file.setText(data.getDataString()
                     .substring(data.getDataString().lastIndexOf("/") + 1));
         }
     }

    /**
     * Subir PDF a Storage
     */
    private void uploadPDFStorage(Uri uri)
    {
        StorageReference storage = storageReference.child("uploadPDF" + System.currentTimeMillis() + ".pdf");
        storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();


            }
        });
    }

    /*
     * FIN PDF
     */


    /*
     * BOTONES
     */

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
     * FIN BOTONES
     */


    /*
     * SPINNERS
     */

    /**
     * Spinner de Plataformas
     *
     * Almacena los datos de la tabla Platform en una lista,
     * luego muestra la lista en el spiner,
     * por último, guarda el campo seleccionado en una variable String.
     */
    private void spinnerPlatform()
    {
        final List<Platforms> plataformas = new ArrayList<>();
        platformRef.child("Platforms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot snapshot2: snapshot.getChildren())
                    {
                        String id = snapshot2.getKey();
                        String name = snapshot2.child("platform_name").getValue().toString();
                        plataformas.add(new Platforms(id, name));
                    }

                    ArrayAdapter<Platforms> arrayAdapterPlatform = new ArrayAdapter<>(NuevaGuiaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, plataformas);
                    spinner_platform.setAdapter(arrayAdapterPlatform);
                    spinner_platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            platform_selected = String.valueOf(adapterView.getItemIdAtPosition(i));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NuevaGuiaActivity.this,
                        "Se ha producido un error al leer los datos del spinner de Plataformas.",
                        Toast.LENGTH_LONG).show();
            }
        });
    } // Fin spinnerPlatform()


    /**
     * Spinner de Plataformas
     *
     * Almacena los datos de la tabla Platform en una lista,
     * luego muestra la lista en el spiner,
     * por último, guarda el campo seleccionado en una variable String.
     */
    private void spinnerCompany()
    {
        final List<Companies> companies = new ArrayList<>();
        companyRef.child("Companies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot snapshot2: snapshot.getChildren())
                    {
                        String id = snapshot2.getKey();
                        String name = snapshot2.child("company_name").getValue().toString();
                        companies.add(new Companies(id, name));
                    }

                    ArrayAdapter<Companies> arrayAdapterCompany = new ArrayAdapter<>(NuevaGuiaActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, companies);
                    spinner_company.setAdapter(arrayAdapterCompany);
                    spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            company_selected = String.valueOf(adapterView.getItemIdAtPosition(i));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NuevaGuiaActivity.this,
                        "Se ha producido un error al leer los datos del spinner de Plataformas.",
                        Toast.LENGTH_LONG).show();
            }
        });
    } // Fin spinnerCompany()

    /*
     * FIN SPINNERS
     */




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
     * Validar longitud name..
     *
     * Se pondrá una longitud mínima y máxima para el campo.
     */
    private boolean sizeNameValidation()
    {
        boolean validated = true;

        // Validación para name_editText
        if(name_editText.getText().toString().trim().length() > 50
                || name_editText.getText().toString().trim().length() < 4)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            name_editText.setError("El nombre de la guía debe contener entre 4 y 50 caracteres.");
            // Se limpia el campo
            name_editText.setText("");
        }

        return validated;
    }

    /*
     * FIN VALIDAICIONES
     */


}
