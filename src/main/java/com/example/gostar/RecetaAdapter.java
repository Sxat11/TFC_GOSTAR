package com.example.gostar;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gostar.R;
import com.example.gostar.Receta;

import java.io.Serializable;
import java.util.ArrayList;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.MyViewHolder> {
    // Puede ser cualquier estructura de datos
    ArrayList<Receta> listaRecetas;
    Context contexto;
    Intent intent;

    int selectedPos=RecyclerView.NO_POSITION;
    public int getSelectedPos() {
        return selectedPos;
    }
    public void setSelectedPos(int nuevaPos) {
        if (nuevaPos == this.selectedPos) {
            return; // si pulsas el mismo item, no hacer nada
        }
        this.selectedPos = nuevaPos;
        notifyDataSetChanged(); // actualiza RecyclerView si quieres marcar selección

        // Abrir la nueva actividad
        Intent intent = new Intent(contexto, Iniciar.class); // Cambia al nombre de tu Activity
        // Pasar datos opcionales, por ejemplo:
        intent.putExtra("posicion", selectedPos);
        // Si tienes datos de la receta:
        // intent.putExtra("nombre", listaRecetas.get(selectedPos).getNombre());

        contexto.startActivity(intent);
    }


    // Constructor
    public RecetaAdapter(ArrayList<Receta> listaRecetas , Context contexto) {
        this.listaRecetas = listaRecetas;
        this.contexto = contexto;
    }

    //  @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta,
                parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return mvh ;
        // return new MyViewHolder(elemento);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels / 4;
        holder.itemView.setLayoutParams(params);

        Receta r = listaRecetas.get(position);

        holder.getNombreReceta().setText(r.getNombre());
        holder.getDuracion().setText(r.getDuracion());
        // holder.getFotoPortada().setBackground(r.getFotoPortada());
        holder.getFotoPortada().setBackgroundResource(r.getFotoPortada());

        holder.itemView.setOnClickListener(v -> {
            setSelectedPos(holder.getAdapterPosition());
        });

    }

    // Número de elementos en la colección de datos. Será el número de elementos
// que se tendrá el RecycleView
    @Override
    public int getItemCount() {
        return this.listaRecetas.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombreReceta, duracion;

        LinearLayout fotoPortada;
        // Constructor:
        public MyViewHolder(View viewElemento) {
            super(viewElemento);
            this.nombreReceta=viewElemento.findViewById(R.id.nombreReceta);
            this.duracion=viewElemento.findViewById(R.id.duraciontxt);
            this.fotoPortada = viewElemento.findViewById(R.id.ll1);


        }
        public TextView getNombreReceta() {
            return nombreReceta;
        }

        public TextView getDuracion() {
            return duracion;
        }

        public LinearLayout getFotoPortada() {
            return fotoPortada;
        }
    }
}
