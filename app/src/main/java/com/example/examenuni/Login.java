package com.example.examenuni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    EditText et_Correo, et_contra;

    Button btn_login, btn_recuperar, btn_registrar;

    private String email;
    private String pass;
    private FirebaseAuth mAuth;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_Correo = findViewById(R.id.et_ResetEmail);
        et_contra = findViewById(R.id.et_ResetContra);
        btn_login = findViewById(R.id.btn_Login);
        btn_registrar = findViewById(R.id.btn_Registrar);
        btn_recuperar = findViewById(R.id.btn_ContraO);

        mAuth = FirebaseAuth.getInstance();



        btn_registrar.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), register.class));
            finish();
        });

        btn_recuperar.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), resetPass.class));
            finish();
        });
        btn_login.setOnClickListener(view -> {
            email = et_Correo.getText().toString().trim();
            pass = et_contra.getText().toString().trim();




            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(Login.this, "ingrese bien los datos", Toast.LENGTH_SHORT).show();
            } else {
                if (emailValido(email)) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            //iniciamos sesion
                            Toast.makeText(Login.this, "Inicio correcto", Toast.LENGTH_SHORT).show();
                            goMainScreen();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                // El usuario ha iniciado sesión, puedes acceder a su UID
                                String uid = user.getUid();
                                reference.child(uid).child("contraseña").setValue(pass);
                            }

                        } else {
                            //las credenciales son incorrectas
                            Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Correo no valido", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }

    private void goMainScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    public  boolean emailValido(String email){
        String expresion = "[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expresion, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }

}