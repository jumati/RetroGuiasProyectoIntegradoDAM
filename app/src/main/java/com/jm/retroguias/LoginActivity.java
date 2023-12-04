package com.jm.retroguias;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText email_editText, password_editText;
    private Button login_button, registrar_button, reset_password_button, info_button;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Declaración de variables
        email_editText = (EditText) findViewById(R.id.login_email);
        password_editText = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_iniciar_sesion_button);
        registrar_button = (Button) findViewById(R.id.login_registrar_button);
        reset_password_button = (Button) findViewById(R.id.login_recuperar_password_button);
        info_button = (Button) findViewById(R.id.login_informacion_button);

        // Logearse e ir a GuidesActivity
        goLogin();

        // Ir a RegistroActivity
        goRegistro();

        // Ir a InfoActivity
        goResetPassword();

        // Ir a InfoActivity
        goInfo();


    } // Fin onCreate()

    /**
     * Login
     *
     * Realiza las validaciones y si las supera, realiza el login y accede a
     * la lista de las guias en GuidesActivity.
     */
    private void Login()
    {
        // Validaciones
        if(!IsEmptyValidation() && emailValidation())
        {
            String email = email_editText.getText().toString().trim();
            String password = password_editText.getText().toString().trim();
            // Instancia de Auth
            auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult)
                        {
                            startActivity(new Intent(LoginActivity.this, GuidesActivity.class));
                            // Limpiamos los campos para que cuando el usuario
                            // se desloguee aparezcan los campos vacíos
                            email_editText.setText("");
                            password_editText.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Si el email o la contraseña son incorrectos se muestra un mensaje.
                    Toast.makeText(LoginActivity.this,
                            "email o contraseña incorrectos.",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    } // Fin Login






    /*
     * BOTONES
     */

    /**
     * Logearse e ir a GuidesActivity
     */
    private void goLogin()
    {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    /**
     * Ir a RegistroActivity
     */
    private void goRegistro()
    {
        registrar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
    }

    /**
     * Ir a ResetPasswordActivity
     */
    private void goResetPassword()
    {
        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    /**
     * Ir a InfoActivity
     */
    private void goInfo()
    {
        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, InfoActivity.class));
            }
        });
    }

    /*
     * FIN BOTONES
     */


    /*
     * VALIDACIONES
     */

    private boolean IsEmptyValidation() {
        boolean empty = false;

        if(email_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            email_editText.setError("Introduce un email.");
        }

        if(password_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            password_editText.setError("Introduce una contraseña.");
        }

        if(empty)
        {
            // Si hay campos vacíos se muestra un mensaje.
            Toast.makeText(LoginActivity.this,
                    "Introduce el email y la contraseña",
                    Toast.LENGTH_LONG).show();
        }
        return empty;
    }

    private boolean emailValidation()
    {
        boolean validated = true;

        // Regex de Google
        // ([a-z0-9]+(\.?[a-z0-9])*)+@(([a-z]+)\.([a-z]+))+

        // Comprueba que el correo sea el correcto con un Regex.
        Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        if(!pattern.matcher(email_editText.getText().toString().trim()).matches())
        {
            validated = false;
            // Si el email es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            email_editText.setError("El email introducido no es correcto.");
            // Se limpia el campo email.
            email_editText.setText("");
        }

        return validated;
    }






    /*
     * VALIDACIONES
     */
}
