package com.example.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.RecyclerView.Player;
import com.example.app.RecyclerView.PlayerAdapter;
import com.example.app.RecyclerView.PlayerDetailFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Player> playersList;
    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;

    private final String url = "http://10.0.2.2:8000/";

    public PlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayersFragment newInstance(String param1, String param2) {
        PlayersFragment fragment = new PlayersFragment();
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

        View view = inflater.inflate(R.layout.fragment_players, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // Instanciamos el RecyclerView

        playersList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playersList); // Instancia del Adapter y se envía la lista de jugadores
        recyclerView.setAdapter(playerAdapter); // Añadimos el adapter como adaptador del RecyclerView

        retrievePlayerData();  // Recuperación de los jugadores

        playerAdapter.setItemClickListener(new PlayerAdapter.OnItemClickListener() { // Detección de clicks para poder cargar el fragment de detalles
            @Override
            public void onItemClick(Player player) {

                PlayerDetailFragment playerDetailFragment = new PlayerDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("player", player);
                playerDetailFragment.setArguments(args);  // Cargado de los datos del jugador en el Bundle para enviarlos a DetailFragment

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager(); // Reemplazado por PlayerDetailFragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, playerDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
    }

    private void retrievePlayerData(){ // Método que lanza una petición

        JsonArrayRequest request = new JsonArrayRequest( // Cargamos el JsonArrayRequest
                Request.Method.GET,
                url+"v1/getPlayers",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject player = response.getJSONObject(i);
                                Player data = new Player(player);  // Instanciamos un nuevo Player y lo introducimos en la lista
                                playersList.add(data);
                            }
                            playerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { //Toast para un posible error
                        if(error.networkResponse == null){

                            Toast.makeText(getContext(), "Error de conexión con el servidor", Toast.LENGTH_LONG).show();

                        }else{

                            int serverResponse = error.networkResponse.statusCode;
                            Toast.makeText(getContext(), "Estado de respuesta: "+ serverResponse, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(request);

    }
}