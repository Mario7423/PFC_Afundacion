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

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {


    ArrayList<Player> playersList;

    public PlayerAdapter(ArrayList<Player> playersList){

        this.playersList = playersList;

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list,null,false);
        return  new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        holder.txt.setText(playersList.get(position).getNickname());
        Picasso.get()
                .load(playersList.get(position).getImage())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        TextView txt;
        ImageView img;

        public PlayerViewHolder(View itemView){

            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.text);
            img = (ImageView) itemView.findViewById(R.id.image);

        }

    }
}
