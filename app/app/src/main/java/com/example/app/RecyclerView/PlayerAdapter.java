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
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {


    private List<Player> playersList;
    private OnItemClickListener itemClickListener;

    public PlayerAdapter(List<Player> playersList){

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

        Player player = playersList.get(position);
        holder.bind(player);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(player);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public PlayerViewHolder(View itemView){

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }

        public void bind(Player player){

            textView.setText(player.getNickname());
            Picasso.get().load(player.getImage()).into(imageView);

        }

    }

    public interface OnItemClickListener{

        void onItemClick(Player player);

    }
}
