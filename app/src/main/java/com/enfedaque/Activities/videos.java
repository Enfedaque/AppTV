package com.enfedaque.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.enfedaque.R;
import com.enfedaque.domain.respuestaVideos;
import com.enfedaque.domain.video;
import com.enfedaque.API.PeliculasAPI;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class videos extends AppCompatActivity {

    YouTubePlayerView videoPrincipal;
    TextView tituloPrincipal;
    TextView popularity;
    TextView fechaPrincipal;
    TextView overviewPrincipal;
    TextView otrosPrincipal;

    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        iniciarComponentes();

        datos=getIntent().getExtras();
        tituloPrincipal.setText(datos.getString("Titulo").toUpperCase());
        fechaPrincipal.setText(datos.getString("Fecha"));
        popularity.setText(datos.getString("Popularity"));
        overviewPrincipal.setText(datos.getString("Overview"));

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute(datos.getString("ID"));
    }

    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                getVideos(strings[0]);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

    private void iniciarComponentes(){
        videoPrincipal=findViewById(R.id.videoPrincipal);
        tituloPrincipal=findViewById(R.id.tituloPrincipal);
        popularity =findViewById(R.id.generoPrincipal);
        fechaPrincipal=findViewById(R.id.fechaPrincipal);
        overviewPrincipal=findViewById(R.id.overviewPrincipal);
        otrosPrincipal=findViewById(R.id.otrosPrincipal);
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
            Intent miIntent=new Intent(this, index.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    //Conecto con la API para obtener el video de la pelicula
    private void getVideos(String idPelicula){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PeliculasAPI peliculasAPI =retrofit.create(PeliculasAPI.class);
        Call<respuestaVideos> call=peliculasAPI.findVideos(idPelicula);

        call.enqueue(new Callback<respuestaVideos>() {
            @Override
            public void onResponse(Call<respuestaVideos> call, Response<respuestaVideos> response) {
                try {
                    if (response.isSuccessful()) {
                        respuestaVideos miRespuesta=response.body();

                        ArrayList<video> lista=miRespuesta.getResults();
                        System.out.println(lista);

                        String clave="";
                        for (int i=0; i < lista.size(); i++){
                            clave=lista.get(i).getKey();
                        }

                        reporducirVideo(clave);

                    }else{
                        Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR 1" , Toast.LENGTH_LONG).show();
                        System.out.println(response.raw());
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void onFailure(Call<respuestaVideos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR 2" , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
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
}