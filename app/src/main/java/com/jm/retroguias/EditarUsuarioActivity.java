package com.jm.retroguias;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.jm.retroguias.model.Users;

import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;

public class EditarUsuarioActivity extends AppCompatActivity {

    private EditText name_editText, last_name_editText, email_editText, phone_editText,
            old_password_editText, new_password_editText, confirm_password_editText;
    private Button guardar_button, volver_button, eliminar_button;
    FirebaseAuth auth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        // Se recogen los campos
        name_editText = (EditText) findViewById(R.id.editar_usuario_name);
        last_name_editText = (EditText) findViewById(R.id.editar_usuario_last_name);
        email_editText = (EditText) findViewById(R.id.editar_usuario_email);
        old_password_editText = (EditText) findViewById(R.id.editar_usuario_old_password);
        new_password_editText = (EditText) findViewById(R.id.editar_usuario_new_password);
        confirm_password_editText = (EditText) findViewById(R.id.editar_usuario_confirm_new_password);
        phone_editText = (EditText) findViewById(R.id.editar_usuario_phone);
        guardar_button = (Button) findViewById(R.id.editar_usuario_guardar_cambios_button);
        eliminar_button = (Button) findViewById(R.id.editar_usuario_eliminar_usuario);
        volver_button = (Button) findViewById(R.id.editar_usuario_volver_button);

        auth = FirebaseAuth.getInstance();


        // Guardar cambios
        guardar();
        // Eliminar usuario
        eliminarUsuario();
        // Ir a LoginActivity
        volver();
    } // Fin onCreate




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
     * Guardar cambios
     *
     * Guarda los cambios
     */
    private void guardar()
    {
        guardar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordValidation();

            }
        });
    }

    /**
     * Eliminar Usuario
     *
     * Lleva a una pantalla donde se confirman el email y la contraseña del usuario.
     * Una vez condirmado, se elimina el usuario.
     */
    private void eliminarUsuario()
    {
        eliminar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditarUsuarioActivity.this, EliminarUsuarioActivity.class));
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
                startActivity(new Intent(EditarUsuarioActivity.this, GuidesActivity.class));
            }
        });
    }


    /*
     * VALIDACIONES
     */



    /*
     * VALIDACIONES CONTRASEÑAS
     */

    /**
     * Validación contraseñas vacías.
     *
     * Comprueba que las conttraseñas estén rellenas,
     * en caso contrario, se mostrará un mensaje.
     */
    private void passwordValidation() {
        boolean empty = false;
        // boolean validated = true;

        // Contraseñas vacías
        if(old_password_editText.getText().toString().trim().isEmpty()
                && (!new_password_editText.getText().toString().trim().isEmpty()
                || !confirm_password_editText.getText().toString().trim().isEmpty()))
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            new_password_editText.setError("Introduce la nueva contraseña.");
            confirm_password_editText.setError("Confirma la nueva contraseña.");
        }

        if(new_password_editText.getText().toString().trim().isEmpty()
                && (!old_password_editText.getText().toString().trim().isEmpty()
                || !confirm_password_editText.getText().toString().trim().isEmpty()))
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            old_password_editText.setError("Introduce la contraseña actual.");
            confirm_password_editText.setError("Confirma la nueva contraseña.");
        }

        if(confirm_password_editText.getText().toString().trim().isEmpty()
                && (!new_password_editText.getText().toString().trim().isEmpty()
                || !old_password_editText.getText().toString().trim().isEmpty()))
        {
            empty = true;
            // Señalamos el error con un mensaje y marcando el campo en rojo
            new_password_editText.setError("Introduce la nueva contraseña.");
            old_password_editText.setError("Introduce la contraseña actual.");
        }

        // Si se ha escrito en alguna de las contraseñas pero ha dejado
        // alguna de las otras en blanco se mostrará un mensaje
        if(empty)
        {
            // validated = false;
            // Si hay campos vacíos se muestra un mensaje.
            Toast.makeText(EditarUsuarioActivity.this,
                    "No se han rellenado todos los campos de contraseña.",
                    Toast.LENGTH_LONG).show();
        }

        // Si los tres campos están rellenos se comprueba la validación
        if(!confirm_password_editText.getText().toString().trim().isEmpty()
                && !new_password_editText.getText().toString().trim().isEmpty()
                && !old_password_editText.getText().toString().trim().isEmpty())
        {
            passwordCredential();
        }
        // return validated;
    }


    private void passwordCredential()
    {
        AuthCredential credential = EmailAuthProvider
                .getCredential(auth.getCurrentUser().getEmail(),
                        old_password_editText.getText().toString().trim());

        auth.getCurrentUser().reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Reautenticación exitosa, aquí puedes realizar las operaciones que requieran autenticación
                Toast.makeText(EditarUsuarioActivity.this, "Reautenticación exitosa", Toast.LENGTH_SHORT).show();
                // Luego puedes realizar las operaciones que requieran autenticación, como cambiar la contraseña
                passwordChange();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // La reautenticación falló, muestra un mensaje de error
                Toast.makeText(EditarUsuarioActivity.this, "Error en la reautenticación", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Validación de new_password_editText y confirm_password.
     *
     * Llama al método  que comprueba que la contraseña sea correcta.
     * Comprueba que los campos password y confirm_password sean identicos.
     */
    private void passwordChange()
    {
        // boolean validated = true;
        String new_pass = new_password_editText.getText().toString().trim();
        String comfirm_pass = confirm_password_editText.getText().toString().trim();

        // Comprobación de que los campos password y confirm_password sean idénticos.
        if(!passwordRegexValidation())
        {
            // El mensaje se muestra en passwordRegexValidation().
            // validated = false;
            // Comprueba que ambas contraseñas no sean iguales
            if (new_pass.equals(comfirm_pass)) {

                // Actualiza la contraseña
                auth.getCurrentUser().updatePassword(new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "Se ha cambiado la contraseña correctamente.",
                                    Toast.LENGTH_LONG).show();Toast.makeText(EditarUsuarioActivity.this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "Se ha producido un error al cambiar la contraseña.",
                                    Toast.LENGTH_LONG).show();}
                    }
                });
            } else {
                // validated = false;
                // Las contraseñas nuevas no coinciden
                Toast.makeText(EditarUsuarioActivity.this,
                        "Las contraseñas nuevas no coinciden",
                        Toast.LENGTH_SHORT).show();
            }
        }


        // return validated;
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
        if(!pattern.matcher(new_password_editText.getText().toString().trim()).matches())
        {
            validated = false;
            // Si el teléfono es incorrecto se muestra un mensaje
            // Señalamos el error con un mensaje y marcando el campo en rojo
            new_password_editText.setError("La contraseña debe tener al entre 8 y 20 caracteres y, al menos, " +
                    "un dígito, una minúscula y una mayúscula.");
            // Se limpian ambos campos: password y confirm_password.
            new_password_editText.setText("");
            confirm_password_editText.setText("");
        }

        return validated;
    }


    /*
     * FIN VALIDACION CONTRASEÑAS
     */




    /**
     * Validar longitud name y last_name.
     *
     * Se pondrá una longitud mínima y máxima para cada campo.
     */
    private boolean sizeNameValidation()
    {
        boolean validated = true;

        // Si el campo no está vacío
        if(!name_editText.getText().toString().trim().isEmpty())
        {
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
        }

        return validated;
    }

    /**
     * Validar longitud name y last_name.
     *
     * Se pondrá una longitud mínima y máxima para cada campo.
     */
    private boolean sizeLastNameValidation()
    {
        boolean validated = true;

        // Si el campo no está vacío
        if(!last_name_editText.getText().toString().trim().isEmpty())
        {
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

        // Si el campo teléfono no está vacío se realizará la validación
        if(!phone_editText.getText().toString().trim().isEmpty())
        {
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
        }
        return validated;
    }




}
