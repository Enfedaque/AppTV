package com.enfedaque.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enfedaque.R;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarComponentes();

        //Listener del login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //login("http://192.168.1.105/appTV/login.php");
                Intent miIntent=new Intent(getApplicationContext(), index.class);
                startActivity(miIntent);
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

    private void inicializarComponentes(){
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnCrearCuenta=findViewById(R.id.btnCrearCuenta);
    }

    //Hacer login
    public void login(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Toast.makeText(Login.this, "Bienvenido de nuevo", Toast.LENGTH_LONG).show();
                    Intent miIntent=new Intent(getApplicationContext(), index.class);
                    startActivity(miIntent);
                }else{
                    Toast.makeText(Login.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    etEmail.setText("");
                    etPassword.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error al intentar conectar", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("email", etEmail.getText().toString());
                parametros.put("password", etPassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Crear nuevo cuenta de usuario
    public void crearCuenta(){
        Intent miIntent=new Intent(getApplicationContext(), registrarse.class);
        startActivity(miIntent);
    }


}