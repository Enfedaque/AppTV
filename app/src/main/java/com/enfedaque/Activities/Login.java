package com.enfedaque.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.R;
import com.enfedaque.UTILS.GlobalVars;
import com.enfedaque.domain.usuario;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCrearCuenta;
    private LinearLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarComponentes();
        detectarPreferencias();

        //Listener del login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarDatos();
            }
        });

        //Listener de registrarse
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                crearCuenta();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        detectarPreferencias();
    }

    private void inicializarComponentes(){
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnCrearCuenta=findViewById(R.id.btnCrearCuenta);
        loginLayout=findViewById(R.id.loginLayout);
    }

    //Valido si ya hay cuenta con esos datos
    private void validarDatos(){
        String email=etEmail.getText().toString();
        String pass=etPassword.getText().toString();

        if (!email.equalsIgnoreCase("") && !pass.equalsIgnoreCase("")){
            baseDeDatos database= Room.databaseBuilder(getApplicationContext(), baseDeDatos.class,
                    "Peliculas").allowMainThreadQueries().fallbackToDestructiveMigration().build();

            try{
                String password= database.usuarioDAO().findByEmail(email);
                long Id= database.usuarioDAO().findById(email);
                if (password!=null){
                    if (password.equalsIgnoreCase(pass)){

                        GlobalVars.setIdUsuario(Id);
                        GlobalVars.setEmail(email);
                        GlobalVars.setPass(pass);

                        Toast.makeText(getApplicationContext(), "Bienvenido de nuevo " + email, Toast.LENGTH_LONG).show();
                        Intent miIntent=new Intent(this, index.class);
                        startActivity(miIntent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
                    }
                }

            }catch (SQLiteException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    //Crear nuevo cuenta de usuario
    public void crearCuenta(){
        Intent miIntent=new Intent(getApplicationContext(), registrarse.class);
        startActivity(miIntent);
    }

    //Detectar cambio de las preferencias
    private void detectarPreferencias(){

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbTemas = preferencias.getBoolean("cbTemas", false);
        if (cbTemas){
            loginLayout.setBackgroundColor(Color.LTGRAY);
            etEmail.setTextColor(Color.BLACK);
            etPassword.setTextColor(Color.BLACK);
        }else{
            loginLayout.setBackgroundColor(Color.BLACK);
            etEmail.setTextColor(Color.WHITE);
            etPassword.setTextColor(Color.WHITE);
        }
    }

}