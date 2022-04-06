package com.enfedaque.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfedaque.R;
import com.enfedaque.adaptadores.recyclerViewAdapterUPCOMING;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.respuestaUpcoming;
import com.enfedaque.API.PeliculasAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class upcoming extends AppCompatActivity {

    private RecyclerView rv_upcoming;
    private recyclerViewAdapterUPCOMING rvAdapter;

    private PeliculasAPI peliculasAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_upcoming);

        inicializarComponentes();

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();
    }

    private void inicializarComponentes(){
        rv_upcoming=findViewById(R.id.rv_upcoming);
    }

    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Integer... strings) {
            try{
                findUpcoming();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
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

    private void findUpcoming(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peliculasAPI =retrofit.create(PeliculasAPI.class);
        Call<respuestaUpcoming> call=peliculasAPI.findUpcoming();

        call.enqueue(new Callback<respuestaUpcoming>() {
            @Override
            public void onResponse(Call<respuestaUpcoming> call, Response<respuestaUpcoming> response) {
                try {
                    if (response.isSuccessful()) {
                        respuestaUpcoming miRespuesta=response.body();

                        List<peliculas> lista=miRespuesta.getResults();
                        rvAdapter=new recyclerViewAdapterUPCOMING(lista);
                        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                                RecyclerView.VERTICAL, false);
                        rv_upcoming.setLayoutManager(mLayoutManager);
                        rv_upcoming.setItemAnimator(new DefaultItemAnimator());
                        rv_upcoming.setAdapter(rvAdapter);
                        rvAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                                miIntent.putExtra("ID", String.valueOf(lista.get(rv_upcoming.getChildAdapterPosition(view)).getId()));
                                miIntent.putExtra("Titulo", lista.get(rv_upcoming.getChildAdapterPosition(view)).getOriginal_title());
                                miIntent.putExtra("Fecha", lista.get(rv_upcoming.getChildAdapterPosition(view)).getRelease_date());
                                miIntent.putExtra("Popularity", String.valueOf(lista.get(rv_upcoming.getChildAdapterPosition(view)).getVote_count()));
                                miIntent.putExtra("Overview", lista.get(rv_upcoming.getChildAdapterPosition(view)).getOverview());
                                startActivity(miIntent);

                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR " , Toast.LENGTH_LONG).show();
                        System.out.println(response.raw());
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void onFailure(Call<respuestaUpcoming> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR " , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }
}
