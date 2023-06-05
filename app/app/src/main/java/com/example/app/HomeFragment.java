package com.example.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.app.RecyclerView.New;
import com.example.app.RecyclerView.NewAdapter;
import com.example.app.RecyclerView.NewDetailFragment;
import com.example.app.RecyclerView.Player;
import com.example.app.RecyclerView.PlayerAdapter;
import com.example.app.RecyclerView.PlayerDetailFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<New> newsList;
    private RecyclerView recyclerView;
    private NewAdapter newAdapter;

    private final String url = "http://10.0.2.2:8000/";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Instanciamos el RecyclerView

        newsList = new ArrayList<>();
        newAdapter = new NewAdapter(newsList);  // Instancia del Adapter y se envía la lista de noticias
        recyclerView.setAdapter(newAdapter);  // Añadimos el adapter como adaptador del RecyclerView

        retrieveNewsData();  // Recuperación de las noticias

        newAdapter.setItemClickListener(new NewAdapter.OnItemClickListener() { // Detección de clicks para poder cargar el fragment de detalles
            @Override
            public void onItemClick(New news) {

                NewDetailFragment newDetailFragment = new NewDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("news", news);
                newDetailFragment.setArguments(args);  // Cargado de los datos de la noticia en el Bundle para enviarlos a DetailFragment

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();  // Reemplazado por NewDetailFragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, newDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
    }

    private void retrieveNewsData(){ // Método que lanza una petición

        JsonArrayRequest request = new JsonArrayRequest( // Cargamos el JsonArrayRequest
                Request.Method.GET,
                url+"v1/getNews",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject news = response.getJSONObject(i);  // Cargamos las noticas en la lista
                                New data = new New(news);
                                newsList.add(data);
                            }
                            newAdapter.notifyDataSetChanged();
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