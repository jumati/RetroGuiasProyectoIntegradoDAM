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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.common.subtyping.qual.Bottom;

public class EliminarUsuarioActivity extends AppCompatActivity {

    private Button eliminar_button, volver_button;
    private  EditText email_editText, password_editText;
    FirebaseUser firebaseUser;
    AuthCredential credential;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_usuario);

        email_editText = (EditText) findViewById(R.id.eliminar_usuario_email);
        password_editText = (EditText) findViewById(R.id.eliminar_usuario_password);
        eliminar_button = (Button) findViewById(R.id.eliminar_usuario_eliminar_usuario);
        volver_button = (Button) findViewById(R.id.eliminar_usuario_volver_button);


        // Eliminar usuario
        eliminarUsuario();
        // Volver
        volver();
    }



    /**
     * Eliminar Usuario
     *
     * Autentifica al usuario.
     * Si es correcto, elimina al usuario
     */
    private void eliminarUsuario()
    {
        eliminar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                credential = EmailAuthProvider
                        .getCredential(email_editText.getText().toString().trim(),
                                password_editText.getText().toString().trim());

                if(firebaseUser != null)
                {
                    firebaseUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        firebaseUser.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task2) {
                                                        if(task2.isSuccessful())
                                                        {
                                                            Toast.makeText(EliminarUsuarioActivity.this,
                                                                    "Se ha eliminado el usuario correctamente.",
                                                                    Toast.LENGTH_LONG).show();

                                                            FirebaseAuth.getInstance().signOut();

                                                            // FALTA ELIMINAR DE LA TABLA
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(EliminarUsuarioActivity.this,
                                                                "Se ha producido un error al eliminar al usuario.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(EliminarUsuarioActivity.this,
                                                "Se ha producido un error al autentificar el usuario.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    startActivity(new Intent(EliminarUsuarioActivity.this, EditarUsuarioActivity.class));

                                }
                            });
                }
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
                startActivity(new Intent(EliminarUsuarioActivity.this, EditarUsuarioActivity.class));
            }
        });
    }

}
