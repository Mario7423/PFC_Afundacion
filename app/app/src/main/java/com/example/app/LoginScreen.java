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
    private final String url = "http://10.0.2.2:8000/"; //"http://127.0.0.1:8000/";

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

                if(!email.getText().toString().contains("@") || email.getText().toString().isEmpty()){  // Comprobación de email

                    goodEmail = false;
                    email.setError("Email no válido");

                }

                if(password.getText().toString().isEmpty()){  // Comprobación de la contraseña

                    goodPassword = false;
                    password.setError("Este campo es obligatorio");

                }

                if(goodPassword && goodEmail){  // Si ambos son correctos, se lanza la petición

                    loginUser();

                }

            }
        });
    }

    private void loginUser(){ // Método que lanza la petición

        JSONObject object = new JSONObject();

        try {  // Formado de JSONObject con la información introducida por el usuario

            object.put("email", email.getText().toString());
            object.put("password", password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url + "v1/login",
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {  // Si ha ido bien, se recibe el email y el token de sesión

                        String receivedToken, receivedEmail;
                        try {
                            receivedEmail = response.getString("email");
                            receivedToken = response.getString("token");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(context, "Token: " + receivedToken, Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);  // Se cargan las SharedPreferences para guardar el email y el token de sesión
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("VALID_EMAIL", receivedEmail);
                        editor.putString("VALID_TOKEN", receivedToken);
                        editor.commit();
                        Intent intent = new Intent(context, MainScreen.class);  // Se carga la pantalla principal
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  // Detalla el error en la petición

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