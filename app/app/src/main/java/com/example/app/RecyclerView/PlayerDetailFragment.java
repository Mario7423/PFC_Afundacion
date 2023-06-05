package com.example.app.RecyclerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageView;
    private TextView name, age, position, number, nickname, nationality, team;

    public PlayerDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerDetailFragment newInstance(String param1, String param2) {
        PlayerDetailFragment fragment = new PlayerDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_player_detail, container, false);

       imageView = view.findViewById(R.id.image);  // Instanciado de los elementos del xml para cambiar su contenido
       name = view.findViewById(R.id.name);
       age = view.findViewById(R.id.age);
       team = view.findViewById(R.id.team);
       nationality = view.findViewById(R.id.nationality);
       position = view.findViewById(R.id.position);
       number = view.findViewById(R.id.number);
       nickname = view.findViewById(R.id.nickname);

       Bundle args = getArguments();  // Recuperación de los datos que vienen de PlayersFragment para poder cargarlos en los elementos.

       if(args != null && args.containsKey("player")){

           Player player = (Player) args.getSerializable("player");

           Picasso.get().load(player.getImage()).into(imageView);  // Librería picasso para cargar una imagen por la url de player.getImage
           name.setText("Nombre: " + player.getName());
           age.setText("Edad: "+String.valueOf(player.getAge()));
           team.setText("Equipo: "+player.getTeam());
           nationality.setText("Nacionalidad: "+player.getNationality());
           position.setText("Posición: "+player.getPosition());
           number.setText("Dorsal: "+String.valueOf(player.getNumber()));
           nickname.setText("Apodo: "+player.getNickname());

       }

       return view;
    }
}