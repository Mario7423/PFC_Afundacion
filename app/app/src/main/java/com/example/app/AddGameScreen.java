package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddGameScreen extends AppCompatActivity {

    private EditText home, visiting, date, hour;
    private Button button;
    private RequestQueue queue;
    private final String url = "http://10.0.2.2:8000/";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_screen);

        home = findViewById(R.id.home);
        visiting = findViewById(R.id.visiting);
        date = findViewById(R.id.date);
        hour = findViewById(R.id.hour);
        button = findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addHint();

            }
        });
    }

    private void addHint(){

        JSONObject object = new JSONObject();

        try {

            object.put("home", home.getText().toString());
            object.put("visiting", visiting.getText().toString());
            object.put("date", date.getText().toString());
            object.put("hour", hour.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"v1/addGame",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "Game añadido con éxito", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error.networkResponse == null){

                            Toast.makeText(context, "Error de conexión con el servidor", Toast.LENGTH_LONG).show();

                        }else{

                            int serverResponse = error.networkResponse.statusCode;
                            Toast.makeText(context, "Estado de respuesta: "+ serverResponse, Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );

        this.queue.add(request);

    }
}