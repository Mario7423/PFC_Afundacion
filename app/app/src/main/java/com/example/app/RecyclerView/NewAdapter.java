package com.example.app.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {

    private List<New> newsList;
    private NewAdapter.OnItemClickListener itemClickListener;

    public NewAdapter(List<New> newsList){ // Método constructor que recibe una lista de noticias

        this.newsList = newsList;

    }

    @NonNull
    @Override
    public NewAdapter.NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Creada y devuelta instancia del ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list,null,false);
        return  new NewAdapter.NewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapter.NewViewHolder holder, int position) {  // Vincula los datos de cada noticia al ViewHolder, según la posición.

        New news = newsList.get(position);
        holder.bind(news);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //Permite detectar clicks del usuario en el RecyclerView
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(news);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    } // Devuelve el tamaño de la lista de noticias

    public void setItemClickListener(NewAdapter.OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public class NewViewHolder extends RecyclerView.ViewHolder{  // Clase interna tipo ViewHolder que representa cada elemento del Recycler View con referencias a new_list

        TextView textView;
        ImageView imageView;

        public NewViewHolder(View itemView){

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }

        public void bind(New news){

            textView.setText(news.getTitle());
            Picasso.get().load(news.getImage()).into(imageView);

        }

    }

    public interface OnItemClickListener{  // Interfaz usada para la detección de clicks

        void onItemClick(New news);

    }
}