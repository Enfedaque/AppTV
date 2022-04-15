package com.enfedaque.VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enfedaque.CONTRACT.videosContract;
import com.enfedaque.PRESENTER.videosPresenter;
import com.enfedaque.R;
import com.enfedaque.domain.respuestaVideos;
import com.enfedaque.domain.video;
import com.enfedaque.API.PeliculasAPI;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;

public class videosView extends AppCompatActivity implements videosContract.View {

    YouTubePlayerView videoPrincipal;
    TextView tituloPrincipal;
    TextView popularity;
    TextView fechaPrincipal;
    TextView overviewPrincipal;
    Button btnCine;

    Bundle datos;

    private videosPresenter presenter;

    private LinearLayout videosLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        iniciarComponentes();
        presenter=new videosPresenter(this);
        detectarPreferencias();

        datos=getIntent().getExtras();
        tituloPrincipal.setText(datos.getString("Titulo").toUpperCase());
        fechaPrincipal.setText(datos.getString("Fecha"));
        popularity.setText(datos.getString("Popularity"));
        overviewPrincipal.setText(datos.getString("Overview"));

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute(datos.getString("ID"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        detectarPreferencias();
    }

    @Override
    public void mostrarVideo(List<video> clave) {
        String claveVideo="";
        for (int i=0; i < clave.size(); i++){
            claveVideo=clave.get(i).getKey();
        }
        reporducirVideo(claveVideo);
    }

    @Override
    public void avisarError(String msg) {
        Toast.makeText(getApplicationContext(), msg.toUpperCase(), Toast.LENGTH_LONG).show();
    }


    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                presenter.getVideo(strings[0]);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }
    }

    //Reproducir video
    private void reporducirVideo(String clave){
        videoPrincipal.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(clave, 0);
            }
        });
    }

    //MEnu superior àra volver al index o recargarlo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    //Manu actionBar opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Si toca la casa lo envio al inicio
        if(item.getItemId() == R.id.house){
            Intent miIntent=new Intent(this, indexView.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.verFav){
            Intent miIntent=new Intent(this, verFavoritosView.class);
            startActivity(miIntent);
            return true;
        }else if(item.getItemId() == R.id.pref){
            Intent miIntent=new Intent(this, preferencias.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    private void iniciarComponentes(){
        videoPrincipal=findViewById(R.id.videoPrincipal);
        tituloPrincipal=findViewById(R.id.tituloPrincipal);
        popularity =findViewById(R.id.generoPrincipal);
        fechaPrincipal=findViewById(R.id.fechaPrincipal);
        overviewPrincipal=findViewById(R.id.overviewPrincipal);
        btnCine=findViewById(R.id.btnCine);
        videosLayout=findViewById(R.id.videosLayout);
    }

    //Metodo que me manda a ver el cine donde se pone la pelicula
    public void abrirMapaCine(View view){
        Intent miIntent=new Intent(this, Mapa.class);
        startActivity(miIntent);
    }

    //Detectar cambio de las preferencias
    private void detectarPreferencias(){

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean cbTemas = preferencias.getBoolean("cbTemas", false);
        boolean cbLetras = preferencias.getBoolean("cbLetras", false);
        if (cbTemas){
            videosLayout.setBackgroundColor(Color.LTGRAY);
            tituloPrincipal.setTextColor(Color.BLACK);
            popularity.setTextColor(Color.BLACK);
            fechaPrincipal.setTextColor(Color.BLACK);
            overviewPrincipal.setTextColor(Color.BLACK);
        }else{
            videosLayout.setBackgroundColor(Color.BLACK);
            tituloPrincipal.setTextColor(Color.BLUE);
            popularity.setTextColor(Color.BLUE);
            fechaPrincipal.setTextColor(Color.BLUE);
            overviewPrincipal.setTextColor(Color.BLUE);
        }
        if (cbLetras) {
            tituloPrincipal.setText(tituloPrincipal.getText().toString().toUpperCase());
            popularity.setText(popularity.getText().toString().toUpperCase());
            fechaPrincipal.setText(fechaPrincipal.getText().toString().toUpperCase());
            overviewPrincipal.setText(overviewPrincipal.getText().toString().toUpperCase());
        }else{
            tituloPrincipal.setText(tituloPrincipal.getText().toString().toLowerCase());
            popularity.setText(popularity.getText().toString().toLowerCase());
            fechaPrincipal.setText(fechaPrincipal.getText().toString().toLowerCase());
            overviewPrincipal.setText(overviewPrincipal.getText().toString().toLowerCase());
        }
    }
}