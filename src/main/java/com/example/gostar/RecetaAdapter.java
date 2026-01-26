package com.example.banda;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gostar.Receta;

import java.io.Serializable;
import java.util.ArrayList;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.MyViewHolder> {
    // Puede ser cualquier estructura de datos
    ArrayList<Receta> listaRecetas;
    Context contexto;

    int selectedPos=RecyclerView.NO_POSITION;
    public int getSelectedPos() {
        return selectedPos;
    }
    public void setSelectedPos(int nuevaPos) {
// Si se pulsa sobre el elemento marcado
        if(nuevaPos ==this.selectedPos){
//            //Intent segunda = new Intent(contexto,Info.class);
//            segunda.putExtra("Info", listaMusicos.get(nuevaPos));
//            contexto.startActivity(segunda);
//            this.selectedPos=RecyclerView.NO_POSITION;
// Se avisa al adaptador para que desmarque esa posición
            notifyItemChanged(nuevaPos);
//        }else{ // El elemento pulsado no está marcado
//            if(this.selectedPos>=0) { // Si ya hay otra posición marcada
//// Se desmarca
//                notifyItemChanged(this.selectedPos);
//            }
//// Se actualiza la nueva posición a la posición pulsada
//            this.selectedPos= nuevaPos;
// Se marca la nueva posición
         //   notifyItemChanged(nuevaPos);
        }
    }


    // Constructor
    public RecetaAdapter(ArrayList<Musico> listaMusicos , Context contexto) {
        this.listaMusicos = listaMusicos;
        this.contexto = contexto;
    }
    // Crea nuevos elementos expandiendo el layout definido en el fichero R.layout.celda
// que usamos para crear el Holder que devolveremos
    //  @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musico,
                parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return mvh ;
        // return new MyViewHolder(elemento);
    }
    // Establece al objeto holder los valores de la colección de datos que
// están en la posición position.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Musico m = listaMusicos.get(position);

        holder.getNombre().setText(m.getNombre());
        holder.getApellido().setText(m.getApellido());
        holder.getBanda().setText(m.getBanda());
        holder.getInstrumento().setText(m.getInstrumento());

        if (m.getFoto() != null) {
            holder.getFoto().setImageURI(m.getFoto());
        } else {
            holder.getFoto().setImageResource(R.drawable.manuel);
        }

        if (selectedPos == position) {
            holder.itemView.setBackgroundResource(R.color.gris);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }

        // 👇 ESTO ES LO QUE FALTABA
        holder.itemView.setOnClickListener(v -> {
            setSelectedPos(holder.getAdapterPosition());
        });

    }

    // Número de elementos en la colección de datos. Será el número de elementos
// que se tendrá el RecycleView
    @Override
    public int getItemCount() {
        return this.listaMusicos.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Elementos que queremos mostrar en cada posición del RecyclerView,
// normalmente se corresponderán con los definidos en el layout
        TextView nombre, apellido, instrumento, banda;

        ImageView foto;
        // Constructor: asocia cada atributo de la clase con su
        // correspondiente componente definido en la layout (ViewElemento)
        public MyViewHolder(View viewElemento) {
            super(viewElemento);
            this.nombre=viewElemento.findViewById(R.id.nombre);
            this.apellido=viewElemento.findViewById(R.id.apellido);
            this.banda= viewElemento.findViewById(R.id.banda);
            this.instrumento = viewElemento.findViewById(R.id.instrumento);
            this.foto=viewElemento.findViewById(R.id.foto);
        }
        public TextView getNombre() {
            return nombre;
        }

        public TextView getApellido() {
            return apellido;
        }

        public TextView getBanda() {
            return banda;
        }

        public TextView getInstrumento() {
            return instrumento;
        }
        public ImageView getFoto() {
            return foto;
        }
    }
}
