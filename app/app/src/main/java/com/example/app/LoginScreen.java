package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginScreen extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button button;
    private boolean goodEmail = true, goodPassword = true;
    private Context context = this;
    private RequestQueue requestQueue;
    private final String urlMockapi = "https://64623eb77a9eead6faca2f47.mockapi.io";
    private final String url = "http://127.0.0.1:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.loginButton);

        requestQueue = Volley.newRequestQueue(context);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email.getText().toString().contains("@") || email.getText().toString().isEmpty()){

                    goodEmail = false;
                    email.setError("Email no válido");

                }

                if(password.getText().toString().isEmpty()){

                    goodPassword = false;
                    password.setError("Este campo es obligatorio");

                }

                if(goodPassword && goodEmail){

                    loginUser();

                }

            }
        });
    }

    private void loginUser(){

        JSONObject object = new JSONObject();

        try {

            object.put("email", email.getText().toString());
            object.put("password", password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url + "v1/login",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String receivedToken, receivedEmail;
                        try {
                            receivedEmail = response.getString("email");
                            receivedToken = response.getString("token");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(context, "Token: " + receivedToken, Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("VALID_EMAIL", receivedEmail);
                        editor.putString("VALID_TOKEN", receivedToken);
                        editor.commit();
//                        Intent intent = new Intent(context, MainScreen.class);
//                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse == null) {
                            Toast.makeText(context, "Error al conectar con el servidor",Toast.LENGTH_LONG).show();
                        }
                        else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Estado del servidor: "+serverCode,Toast.LENGTH_LONG).show();
                        }

                    }
                }

        );

        requestQueue.add(request);


    }
}