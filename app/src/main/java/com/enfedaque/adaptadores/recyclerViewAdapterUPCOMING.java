package com.enfedaque.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfedaque.R;
import com.enfedaque.domain.peliculas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recyclerViewAdapterUPCOMING extends RecyclerView.Adapter<recyclerViewAdapterUPCOMING.MyViewHolder>
        implements View.OnClickListener{


    private List<peliculas> listadoPeliculas;
    private View.OnClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView fotoUpcoming;
        public TextView tituloUpcoming;
        public TextView overviewUpcoming;

        public MyViewHolder(View view){
            super(view);
            fotoUpcoming =view.findViewById(R.id.portadaUpcoming);
            tituloUpcoming=view.findViewById(R.id.tituloUpcoming);
            overviewUpcoming=view.findViewById(R.id.overviewUpcoming);
        }
    }

    public recyclerViewAdapterUPCOMING(List<peliculas> listadoPeliculas){
        this.listadoPeliculas =listadoPeliculas;
    }

    @NonNull
    @Override
    public recyclerViewAdapterUPCOMING.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming, parent, false);

        itemView.setOnClickListener(this);

        return new recyclerViewAdapterUPCOMING.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapterUPCOMING.MyViewHolder holder, int position) {
        peliculas miPelicula= listadoPeliculas.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + miPelicula.getPoster_path()).into(holder.fotoUpcoming);
        holder.tituloUpcoming.setText(miPelicula.getOriginal_title());
        holder.overviewUpcoming.setText(miPelicula.getOverview());
    }

    @Override
    public int getItemCount() {
        return listadoPeliculas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

}
