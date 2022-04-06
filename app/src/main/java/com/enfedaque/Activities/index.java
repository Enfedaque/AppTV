package com.enfedaque.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.enfedaque.R;
import com.enfedaque.adaptadores.recyclerViewAdapterPOPULAR;
import com.enfedaque.domain.peliculas;
import com.enfedaque.domain.respuestaPeliculas;
import com.enfedaque.API.PeliculasAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class index extends AppCompatActivity {

    private RecyclerView rvRecyclerView;
    private recyclerViewAdapterPOPULAR rvAdapter;
    private RecyclerView pruebas;
    private RecyclerView pruebas2;

    private PeliculasAPI peliculasAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv);

        inicializarComponentes();

        TaskVideos taskVideos=new TaskVideos();
        taskVideos.execute();

    }

    //Hilo que me va a lanzar la busqueda en la API
    class TaskVideos extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... strings) {
            try{
                findAll();
                findAllOther();
                findAllTopRated();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
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
        }else if(item.getItemId() == R.id.fondoMapa){
            Intent miIntent=new Intent(this, Mapa.class);
            startActivity(miIntent);
            return true;
        }
        return true;
    }

    //Obtener datos de la API
    private void findAll(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peliculasAPI =retrofit.create(PeliculasAPI.class);
        Call<respuestaPeliculas> call=peliculasAPI.findAllPopular();

        call.enqueue(new Callback<respuestaPeliculas>() {
            @Override
            public void onResponse(Call<respuestaPeliculas> call, Response<respuestaPeliculas> response) {
                try {
                    if (response.isSuccessful()) {
                        respuestaPeliculas miRespuesta=response.body();

                        List<peliculas> lista=miRespuesta.getResults();
                        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
                        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                                RecyclerView.HORIZONTAL, false);
                        rvRecyclerView.setLayoutManager(mLayoutManager);
                        rvRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        rvRecyclerView.setAdapter(rvAdapter);
                        rvAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                                miIntent.putExtra("ID", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getId()));
                                miIntent.putExtra("Titulo", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOriginal_title());
                                miIntent.putExtra("Fecha", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getRelease_date());
                                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getVote_count()));
                                miIntent.putExtra("Overview", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOverview());
                                startActivity(miIntent);

                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                        System.out.println(response.raw());
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void onFailure(Call<respuestaPeliculas> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void findAllTopRated(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peliculasAPI =retrofit.create(PeliculasAPI.class);
        Call<respuestaPeliculas> call=peliculasAPI.findAllTopRated();

        call.enqueue(new Callback<respuestaPeliculas>() {
            @Override
            public void onResponse(Call<respuestaPeliculas> call, Response<respuestaPeliculas> response) {
                try {
                    if (response.isSuccessful()) {
                        respuestaPeliculas miRespuesta=response.body();

                        List<peliculas> lista=miRespuesta.getResults();
                        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
                        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                                RecyclerView.HORIZONTAL, false);
                        pruebas.setLayoutManager(mLayoutManager);
                        pruebas.setItemAnimator(new DefaultItemAnimator());
                        pruebas.setAdapter(rvAdapter);

                        rvAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                                miIntent.putExtra("ID", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getId()));
                                miIntent.putExtra("Titulo", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOriginal_title());
                                miIntent.putExtra("Fecha", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getRelease_date());
                                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getVote_count()));
                                miIntent.putExtra("Overview", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOverview());
                                startActivity(miIntent);

                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                        System.out.println(response.raw());
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void onFailure(Call<respuestaPeliculas> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void findAllOther(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peliculasAPI =retrofit.create(PeliculasAPI.class);
        Call<respuestaPeliculas> call=peliculasAPI.findOthers();

        call.enqueue(new Callback<respuestaPeliculas>() {
            @Override
            public void onResponse(Call<respuestaPeliculas> call, Response<respuestaPeliculas> response) {
                try {
                    if (response.isSuccessful()) {
                        respuestaPeliculas miRespuesta=response.body();

                        List<peliculas> lista=miRespuesta.getResults();
                        rvAdapter=new recyclerViewAdapterPOPULAR(lista);
                        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),
                                RecyclerView.HORIZONTAL, false);
                        pruebas2.setLayoutManager(mLayoutManager);
                        pruebas2.setItemAnimator(new DefaultItemAnimator());
                        pruebas2.setAdapter(rvAdapter);

                        rvAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent miIntent=new Intent(getApplicationContext(), videos.class);
                                miIntent.putExtra("ID", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getId()));
                                miIntent.putExtra("Titulo", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOriginal_title());
                                miIntent.putExtra("Fecha", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getRelease_date());
                                miIntent.putExtra("Popularity", String.valueOf(lista.get(rvRecyclerView.getChildAdapterPosition(view)).getVote_count()));
                                miIntent.putExtra("Overview", lista.get(rvRecyclerView.getChildAdapterPosition(view)).getOverview());
                                startActivity(miIntent);

                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                        System.out.println(response.raw());
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void onFailure(Call<respuestaPeliculas> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR AL CONECTAR" , Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });
    }

    private void inicializarComponentes(){
        rvRecyclerView=findViewById(R.id.rvRecyclerViewPopular);
        pruebas=findViewById(R.id.rv2);
        pruebas2=findViewById(R.id.rv3);
    }



}