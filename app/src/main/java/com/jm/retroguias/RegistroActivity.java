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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jm.retroguias.model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseRef;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private EditText name_editText, last_name_editText, email_editText,
            password_editText, confirm_password_editText, phone_editText;
    private Button registrar_button, volver_button, limpiar_campos_boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Se recogen los campos
        name_editText = (EditText) findViewById(R.id.registro_name);
        last_name_editText = (EditText) findViewById(R.id.registro_last_name);
        email_editText = (EditText) findViewById(R.id.registro_email);
        password_editText = (EditText) findViewById(R.id.registro_password);
        confirm_password_editText = (EditText) findViewById(R.id.registro_confirm_password);
        phone_editText = (EditText) findViewById(R.id.registro_phone);
        registrar_button = (Button) findViewById(R.id.registro_registrar_button);
        limpiar_campos_boton = (Button) findViewById(R.id.registro_limpiar);
        volver_button = (Button) findViewById(R.id.registro_volver_button);

        FirebaseApp.initializeApp(this);

        // firestore = FirebaseFirestore.getInstance();


        // Botón Registrar Usuario
        registrar();
        // Limpiar Campos
        limpiarCampos();
        // Ir a LoginActivity
        volver();
    } // Fin onCreate

    /**
     * Registra un usuario
     */
    private void registrar()
    {
        registrar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comprobar validaciones
                // Primero comprueba que no haya campos vacíos
                if(!isEmptyValidation()) {
                    // Luego te dice las validaciones en el resto de campos
                    if (sizeNameValidation() && emailValidation()
                            && passwordValidation() && phoneValidation()) {
                        String name = name_editText.getText().toString();
                        String last_name = last_name_editText.getText().toString();
                        String email = email_editText.getText().toString();
                        String password = password_editText.getText().toString();
                        String confirm_password = confirm_password_editText.getText().toString();
                        int phone = Integer.parseInt(phone_editText.getText().toString());

                        // Instancias Firebase
                        auth = FirebaseAuth.getInstance();

                        // Conexión con la base de datos
                        database = FirebaseDatabase.getInstance();
                        // Referencia a la tabla Users de la clase Users.java
                        databaseRef = database.getReference(Users.class.getSimpleName());

                        // Se crea la autentificación con email y contraseña.
                        createAuth(email, password);

                        // Se crea un objeto Users que contenga todos los campos
                        Users user = new Users(email, name, last_name, phone);

                        // Se insertan los datos en la base de datos
                        user.setId(UUID.randomUUID().toString());
                        user.setEmail(email);
                        user.setName(name);
                        user.setLast_name(last_name);
                        user.setPhone(phone);
                        List<String> fav = new ArrayList<>();
                        user.setFav(fav);

                        databaseRef.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful())
                                {
                                    // Se muestra un mensaje de que el usuario se ha registrado correctamente.
                                    Toast.makeText(RegistroActivity.this,
                                            "Se ha producido un error al añadir al usuario a la tabla.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });;


                        // Se cierra sesión para evitar que se mantenga logeado el usuario
                        auth.signOut();

                        // Se limpian los campos una vez registrado el usuario
                        name_editText.setText("");
                        last_name_editText.setText("");
                        email_editText.setText("");
                        password_editText.setText("");
                        confirm_password_editText.setText("");
                        phone_editText.setText("");
                    }
                }

            }
        });
    } // Fin Registrar()

    /**
     * Registrar Usuario y Contraseña
     */
    private void createAuth(String email, String password)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            // Se muestra un mensaje de que el usuario se ha registrado correctamente.
                            Toast.makeText(RegistroActivity.this,
                                    "Usuario registrado correctamente.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(RegistroActivity.this,
                                    "Se ha producido un error al registrar el usuario.",
                                    Toast.LENGTH_LONG).show();
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
        limpiar_campos_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_editText.setText("");
                last_name_editText.setText("");
                email_editText.setText("");
                password_editText.setText("");
                confirm_password_editText.setText("");
                phone_editText.setText("");
            }
        });
    }


    /**
     * Vuelve a LoginActivity
     */
    private void volver()
    {
        volver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
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

        if(last_name_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            last_name_editText.setError("Introduce tus apellidos.");
        }

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

        if(confirm_password_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            confirm_password_editText.setError("Confirma la contraseña.");
        }

        if(phone_editText.getText().toString().trim().isEmpty())
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            phone_editText.setError("Introduce tu teléfono.");
        }

        if(empty)
        {
            // Si hay campos vacíos se muestra un mensaje.
            Toast.makeText(RegistroActivity.this,
                    "No se han rellenado todos los campos",
                    Toast.LENGTH_LONG).show();
        }
        return empty;
    }

    /**
     * Valida el correo.
     *
     * Valida el correo para que sea compatible con correos de Google.
     */
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
        else if(emailExist()) // Valida que el email exista
        {
            validated = false;
        }

        return validated;
    }

    /**
     * Validar existencia email.
     *
     * Comprueba si el email existe en la base de datos.
     * En caso de existir, no pasará la validación.
     */
    private boolean emailExist()
    {
        boolean exist = false; // Cambiar

        if(exist) //FALTA ESTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
        {
            exist = true;
            // Si el email se encuentra en la base de datos se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            email_editText.setError("El email introducido ya existe.");
            // Se limpia el campo email.
            email_editText.setText("");
        }

        return exist;
    }

    /**
     * Validar longitud name y last_name.
     *
     * Se pondrá una longitud mínima y máxima para cada campo.
     */
    private boolean sizeNameValidation()
    {
        boolean validated = true;

        // Validación para name
        if(name_editText.getText().toString().trim().length() > 12
                || name_editText.getText().toString().trim().length() < 3)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            name_editText.setError("El nombre debe contener entre 3 y 12 caracteres.");
            // Se limpia el campo
            name_editText.setText("");
        }

        // Validación para last_name
        if(last_name_editText.getText().toString().trim().length() > 20
                || last_name_editText.getText().toString().trim().length() < 4)
        {
            validated = false;
            // Si es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            last_name_editText.setError("El apellido debe contener entre 4 y 20 caracteres.");
            // Se limpia el campo
            last_name_editText.setText("");
        }

        return validated;
    }

    /**
     * Validación de password y confirm_password.
     *
     * Llama al método  que comprueba que la contraseña sea correcta.
     * Comprueba que los campos password y confirm_password sean identicos.
     */
    private boolean passwordValidation()
    {
        boolean validated = true;
        String pass = password_editText.getText().toString().trim();
        String comfirm_pass = confirm_password_editText.getText().toString().trim();

        // Comprobación de que los campos password y confirm_password sean idénticos.
        if(!passwordRegexValidation())
        {
            // El mensaje se muestra en passwordRegexValidation().
            validated = false;
        }
        else if(!pass.equals(comfirm_pass))
        {
            validated = false;
            // Si los campos no son idénticos se muestra un mensaje
            // Señalamos el error con un mensaje y marcando los campos en rojo
            password_editText.setError("Las contraseñas no son idénticas.");
            confirm_password_editText.setError("Las contraseñas no son idénticas.");
            // También lanzamos un mensaje global indicando que deben ser idénticas.
            Toast.makeText(RegistroActivity.this,
                    "Las contraseñas no son idénticas.",
                    Toast.LENGTH_LONG).show();
            // Se limpian ambas contraseñas
            password_editText.setText("");
            confirm_password_editText.setText("");
        }

        return validated;
    }

    /**
     * Validar longitud password.
     *
     * La contraseña debe tener al entre 8 y 16 caracteres y, al menos,
     * un dígito, una minúscula y una mayúscula.
     * Solo se validará password. El campo confirm_password no es
     * necesario al tener que ser idéntico que password.
     */
    private boolean passwordRegexValidation()
    {
        boolean validated = true;

        Pattern pattern = Pattern.compile("^(?=.*[0-9])" // Al menos un dígito
                                                + "(?=.*[a-z])" // Al menos una letra minúscula
                                                + "(?=.*[A-Z])" // Al menos una letra mayúscula
                                                + "(?=\\S+$)" // No puede haber espacios en blanco
                                                + ".{8,20}$"); // Entre 8 y 20 caracteres
        if(!pattern.matcher(password_editText.getText().toString().trim()).matches())
        {
            validated = false;
            // Si el teléfono es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            password_editText.setError("La contraseña debe tener al entre 8 y 20 caracteres y, al menos, " +
                                        "un dígito, una minúscula y una mayúscula.");
            // Se limpian ambos campos: password y confirm_password.
            password_editText.setText("");
            confirm_password_editText.setText("");
        }

        return validated;
    }

    /**
     * Validación del teléfono.
     *
     * Comprueba que el teléfono esté introducido correctamente.
     * En caso de no pasar la validación, mostrará un mensaje.
     * (Solo tiene en cuenta móviles de España)
     */
    private boolean phoneValidation()
    {
        boolean validated = true;

        // Pattern que utiliza un Regex para comprobar que el teléfono sea un móvil o un fijo español:
        // Puede empezar por prefijo español (+34, 0034, o 34) o sin él,
        // seguido de un número de 9 dígitos que comience por 6, 7, 8 o 9.
        Pattern pattern = Pattern.compile("^(\\+34|0034|34)?[6789][0-9]{8}$");

        // Se valida si el parámetro introducido coincide con el Regex del pattern o no.
        if (!pattern.matcher(phone_editText.getText().toString().trim()).matches())
        {
            validated = false;
            // Si el teléfono es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            phone_editText.setError("El teléfono introducido no es correcto.");
            // Se limpia el campo
            phone_editText.setText("");
        }
        return validated;
    }







    /*
     * FIN VALIDACIONES
     */

}