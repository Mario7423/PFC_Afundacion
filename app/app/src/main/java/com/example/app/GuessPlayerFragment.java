package com.example.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuessPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuessPlayerFragment extends Fragment {

    private TextView first, second, third, fourth, fifth;
    private EditText editText;
    private Button validate, reload;
    private final String url = "http://10.0.2.2:8000/";
    private String solution;
    private int tries;
    private RequestQueue queue;
    private JSONArray jsonArray;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuessPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuessPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuessPlayerFragment newInstance(String param1, String param2) {
        GuessPlayerFragment fragment = new GuessPlayerFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess_player, container, false);

        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        third = view.findViewById(R.id.third);
        fourth = view.findViewById(R.id.fourth);
        fifth = view.findViewById(R.id.fifth);
        editText = view.findViewById(R.id.editText);
        validate = view.findViewById(R.id.validate);
        reload = view.findViewById(R.id.reload);

        first.setVisibility(View.GONE);
        second.setVisibility(View.GONE);
        third.setVisibility(View.GONE);
        fourth.setVisibility(View.GONE);
        fifth.setVisibility(View.GONE);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().equalsIgnoreCase(solution)){

                    editText.setText("HAS GANADO!!!");
                    editText.setEnabled(false);

                }else{

                    tries++;
                    switch (tries){

                        case 1:

                            second.setVisibility(View.VISIBLE);
                            break;
                        case 2:

                            third.setVisibility(View.VISIBLE);
                            break;
                        case 3:

                            fourth.setVisibility(View.VISIBLE);
                            break;

                        case 4:
                            fifth.setVisibility(View.VISIBLE);
                            break;

                        case 5:
                            editText.setText("HAS PERDIDO...");
                            editText.setEnabled(false);

                    }

                }

            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDataForGame();

            }
        });

        return view;
    }

    private void getDataForGame(){

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + "v1/getHints",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Random random = new Random();
                        int aleatorio = random.nextInt(response.length());

                        try {

                            JSONObject jsonObject = response.getJSONObject(aleatorio);

                            first.setText(jsonObject.get("hint1").toString());
                            second.setText(jsonObject.get("hint2").toString());
                            third.setText(jsonObject.get("hint3").toString());
                            fourth.setText(jsonObject.get("hint4").toString());
                            fifth.setText(jsonObject.get("hint5").toString());
                            solution = jsonObject.get("solution").toString();

                            tries = 0;
                            editText.setEnabled(true);
                            editText.setText("");

                            first.setVisibility(View.VISIBLE);
                            second.setVisibility(View.GONE);
                            third.setVisibility(View.GONE);
                            fourth.setVisibility(View.GONE);
                            fifth.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
}