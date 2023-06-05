package com.example.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CalendarView calendarView;
    private ProgressBar progressBar;

    private List<Matches> matchesList;

    private RequestQueue queue;

    private final String url = "http://10.0.2.2:8000/";

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

        queue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        progressBar = view.findViewById(R.id.progressBar);

        matchesList = new ArrayList<>();
        getFootBallMatches();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {  // Detecta qué día ha pulsado el usuario
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                LocalDate calendarDate = LocalDate.of(year, month+1, dayOfMonth);  // Creación de un objeto LocalDate de la fecha que ha pulsado

                Matches match = searchGame(calendarDate);  // Búsqueda de un partido que tenga esa fecha

                if(match != null){  // Si existe un partido, muestra los detalles

                    String message = "Local: " +match.getHome()+ " - Visitante: "+match.getVisiting()+" - "+match.getHour();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                }else{  // Muestra un Toast que indica que no hay partido

                    Toast.makeText(getActivity(), "Hoy no hay partido", Toast.LENGTH_LONG).show();

                }

            }
        });

        return view;
    }


    private void getFootBallMatches(){  // Método que lanza una petición para obtener los partidos

        calendarView.setVisibility(View.GONE);  // Muestra la ProgressBar y oculta el Calendario
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + "v1/getGames",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = response.getJSONObject(i);
                                Matches match = new Matches(object);  // Instancia de Matches para añadirlo en la lista
                                matchesList.add(match);

                            }

                            new Handler().postDelayed(new Runnable() {  // Después de 2 segundos, oculta la ProgressBar y muestra el Calendario
                                @Override
                                public void run() {

                                    progressBar.setVisibility(View.GONE);
                                    calendarView.setVisibility(View.VISIBLE);
                                }
                            }, 2000);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  // Detalla el error de la petición

                        if(error.networkResponse == null){

                            Toast.makeText(getContext(), "Error de conexión con el servidor", Toast.LENGTH_LONG).show();

                        }else{

                            int serverResponse = error.networkResponse.statusCode;
                            Toast.makeText(getContext(), "Estado de respuesta: "+ serverResponse, Toast.LENGTH_SHORT).show();

                        }

                    }
                }

        );

        queue.add(request);

    }

    private Matches searchGame(LocalDate date){  // Método que comprueba que existe un partido en la fecha que se pasa como argumento

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate gameDate;

        for(Matches match : matchesList){

            gameDate = LocalDate.parse(match.getDate(), formatter);  // Crea un objeto LocalDate según la fecha del partido y el formatter

            if(gameDate.isEqual(date)){  // Método que comprueba si la fecha pasada y la del partido son iguales

                return match;

            }

        }

        return null;

    }

}