package com.enfedaque.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.enfedaque.domain.usuario;

import java.util.HashMap;
import java.util.Map;

public class registrarse extends AppCompatActivity {

    private EditText nuevoUsuario;
    private EditText nuevoEmail;
    private EditText nuevaPassword;
    private CheckBox cbTerminos;
    private Button btnAdd;
    private Button btnLimpiar;

    private usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
        inicializarComponentes();

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarFormulario();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recogerDatos();
            }
        });

    }

    private void inicializarComponentes(){
        nuevoUsuario=findViewById(R.id.nuevoUsuario);
        nuevoEmail=findViewById(R.id.nuevoEmail);
        nuevaPassword=findViewById(R.id.nuevaPassword);
        cbTerminos=findViewById(R.id.cbTerminos);
        btnAdd=findViewById(R.id.btnAddUsuario);
        btnLimpiar=findViewById(R.id.btnLimpiarUsuario);
    }

    //Recoger datos
    public void recogerDatos(){
        String nombreUsuario=nuevoUsuario.getText().toString();
        String email=nuevoEmail.getText().toString();
        String pass=nuevaPassword.getText().toString();
        if (nombreUsuario.equalsIgnoreCase("") || email.equalsIgnoreCase("") ||
                pass.equalsIgnoreCase("") || !cbTerminos.isChecked()){
            Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
        }else{
            usuario=new usuario(nombreUsuario, email, pass);
            registro("http://192.168.1.105/appTV/addUsuario.php");
        }
    }

    //Limpiar formulario
    public void limpiarFormulario(){
        nuevoUsuario.setText("");
        nuevoEmail.setText("");
        nuevaPassword.setText("");
        cbTerminos.setChecked(false);
    }

    //Crear nuevo usuario
    public void registro(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    System.out.println(response.toUpperCase());
                    Toast.makeText(registrarse.this, "Usuario creado con exito", Toast.LENGTH_LONG).show();
                    Intent miIntent=new Intent(getApplicationContext(), index.class);
                    startActivity(miIntent);
                }else{
                    Toast.makeText(registrarse.this, "Ha surgido algun error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registrarse.this, "Error al intentar conectar", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("nombre", usuario.getNombreUsuario());
                parametros.put("email", usuario.getEmail());
                parametros.put("password", usuario.getPass());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}