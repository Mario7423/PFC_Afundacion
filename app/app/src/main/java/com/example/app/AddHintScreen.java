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

public class AddHintScreen extends AppCompatActivity {

    private EditText hint1, hint2, hint3, hint4, hint5, solution;
    private Button button;
    private RequestQueue queue;
    private final String url = "http://10.0.2.2:8000/";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hint_screen);

        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);
        hint3 = findViewById(R.id.hint3);
        hint4 = findViewById(R.id.hint4);
        hint5 = findViewById(R.id.hint5);
        solution = findViewById(R.id.solution);
        button = findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);  // Instancia de la RequestQueue

        button.setOnClickListener(new View.OnClickListener() {  // Permite la detección de click en el botón
            @Override
            public void onClick(View v) {

                addHint();

            }
        });
    }

    private void addHint(){  // Método que lanza la petición

        JSONObject object = new JSONObject();

        try {   // Llenado del JSONObject para enviar en la petición

            object.put("hint1", hint1.getText().toString());
            object.put("hint2", hint2.getText().toString());
            object.put("hint3", hint3.getText().toString());
            object.put("hint4", hint4.getText().toString());
            object.put("hint5", hint5.getText().toString());
            object.put("solution", solution.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"v1/addHint",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {  // Si se ha completado correctamente, muestra un Toast para conocer el resultado

                        Toast.makeText(context, "Hint añadido con éxito", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  // Muestra detalladamente el error de la petición

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