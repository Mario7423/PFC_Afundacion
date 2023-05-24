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

public class AddPlayerScreen extends AppCompatActivity {

    private EditText name, age, number, image, nationality, team, nickname, position;
    private Button button;
    private RequestQueue queue;
    private final String url = "http://10.0.2.2:8000/";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_screen);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        number = findViewById(R.id.number);
        image = findViewById(R.id.image);
        nationality = findViewById(R.id.nationality);
        team = findViewById(R.id.team);
        nickname = findViewById(R.id.nickname);
        position = findViewById(R.id.position);
        button = findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlayer();

            }
        });
    }

    private void addPlayer(){

        JSONObject object = new JSONObject();

        try {

            object.put("name", name.getText().toString());
            object.put("age", age.getText().toString());
            object.put("number", number.getText().toString());
            object.put("image", image.getText().toString());
            object.put("team", team.getText().toString());
            object.put("nationality", nationality.getText().toString());
            object.put("nickname", nickname.getText().toString());
            object.put("position", position.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"v1/add",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "Jugador añadido con éxito", Toast.LENGTH_SHORT).show();

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