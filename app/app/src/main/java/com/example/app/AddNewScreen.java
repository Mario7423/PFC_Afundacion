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

public class AddNewScreen extends AppCompatActivity {

    private EditText title, image, date, text;
    private Button button;
    private RequestQueue queue;
    private final String url = "http://10.0.2.2:8000/";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_screen);

        title = findViewById(R.id.title);
        image = findViewById(R.id.image);
        date = findViewById(R.id.date);
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);  // Instancia de la RequestQueue

        button.setOnClickListener(new View.OnClickListener() {  // Detección del click en el botón
            @Override
            public void onClick(View v) {

                addNew();

            }
        });
    }

    private void addNew(){  // Método que lanza la petición

        JSONObject object = new JSONObject();

        try {  // Llenado del JSONObject que se va a enviar en la petición

            object.put("title", title.getText().toString());
            object.put("image", image.getText().toString());
            object.put("date", date.getText().toString());
            object.put("text", text.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"v1/addNew",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { // Muestra un Toast para saber que todo a salido como se esperaba

                        Toast.makeText(context, "New añadida con éxito", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  // Detalla el error de la petición

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