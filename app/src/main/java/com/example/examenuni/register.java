package com.example.examenuni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class register extends AppCompatActivity {

    EditText et_email,et_pass,et_confpass;
    Button btn_registrar,btn_login;

    private String email;
    private String pass;

    FirebaseAuth mAuth;

    DatabaseReference databaseRererence;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = findViewById(R.id.et_ResetEmail);
        et_pass = findViewById(R.id.et_ResetContra);
        et_confpass = findViewById(R.id.et_ResetRC);

        mAuth = FirebaseAuth.getInstance();
        databaseRererence = FirebaseDatabase.getInstance().getReference();

        btn_registrar = findViewById(R.id.btn_CrearC);
        btn_registrar.setOnClickListener(view -> registrarUsuario());

        btn_login = findViewById(R.id.btn_IniciarS);
        btn_login.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        });
    }
    public void registrarUsuario(){
        email = et_email.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        String confipass = et_confpass.getText().toString().trim();
        if ( email.isEmpty() || pass.isEmpty() || confipass.isEmpty()) {
            mostrarMensaje("Por favor, ingrese todos los valores.");
        } else if (!emailValido(email)) {
            et_email.setError("Ingrese un Email válido.");
        } else if (!pass.equals(confipass)) {
            et_confpass.setError("Las contraseñas no coinciden.");
        } else if (pass.length() <= 6) {
            et_pass.setError("La contraseña debe ser mayor a 6 caracteres.");
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String userId = mAuth.getCurrentUser().getUid();
                    // Crea un HashMap para almacenar los datos
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("correoElectronico", email);
                    usuario.put("contrasena", pass);
                    // Guardar el objeto en la base de datos bajo el UID del usuario
                    databaseRererence.child("Usuarios").child(userId).setValue(usuario);

                    mostrarMensaje("Cuenta creada correctamente.");
                    irInicio();
                } else {
                    mostrarMensaje("La cuenta ya existe o ha ocurrido un error.");
                }
            });
        }
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(register.this, mensaje, Toast.LENGTH_SHORT).show();
    }
    private void irInicio() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    public  boolean emailValido(String email){
        String expresion = "[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }
}
