package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        JSONObject requestBody = new JSONObject();

        try {

            requestBody.put("email", email.getText().toString());
            requestBody.put("password", password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {



            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        };

        //JsonObjectRequest request =


    }
}