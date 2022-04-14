package com.enfedaque.VIEW;

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

import com.enfedaque.BBDD.baseDeDatos;
import com.enfedaque.CONTRACT.loginContract;
import com.enfedaque.PRESENTER.loginPresenter;
import com.enfedaque.R;
import com.enfedaque.UTILS.GlobalVars;

public class LoginView extends AppCompatActivity implements loginContract.View{

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCrearCuenta;
    private LinearLayout loginLayout;

    private loginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarComponentes();

        presenter=new loginPresenter(this);

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
            presenter.getPassword(email, pass);

        }else{
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    //Crear nuevo cuenta de usuario
    public void crearCuenta(){
        Intent miIntent=new Intent(getApplicationContext(), registrarseView.class);
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

    @Override
    public String paswordOk(String email) {
        Toast.makeText(getApplicationContext(), "Bienvenido de nuevo " + email, Toast.LENGTH_LONG).show();
        Intent miIntent=new Intent(this, indexView.class);
        startActivity(miIntent);
        return null;
    }

    @Override
    public String paswordCancel() {
        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void guardarVariables(String email, String pass) {
        long Id= presenter.getIdUsuario(email);

        GlobalVars.setIdUsuario(Id);
        GlobalVars.setEmail(email);
        GlobalVars.setPass(pass);
    }


}