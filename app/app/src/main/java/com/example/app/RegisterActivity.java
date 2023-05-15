package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText nickname;
    private CheckBox checkBox;
    private TextView textViewFromCV;
    private Button button;
    private Context context = this;
    private RequestQueue requestQueue;
    private final String urlMockapi = "https://64623eb77a9eead6faca2f47.mockapi.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);
        checkBox = findViewById(R.id.checkbox);
        textViewFromCV = findViewById(R.id.textViewFromCheckBox);
        button = findViewById(R.id.button);

        requestQueue = Volley.newRequestQueue(this);

        SpannableString underlinedText = new SpannableString(textViewFromCV.getText().toString());
        underlinedText.setSpan(new UnderlineSpan(), 0, underlinedText.length(), 0);
        textViewFromCV.setText(underlinedText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText = email.getText().toString(), passwordText = password.getText().toString(), nicknameText = nickname.getText().toString();
                boolean goodEmail = true, goodPassword = true, goodNickname = true;

                if(emailText.isEmpty()){

                    goodEmail = false;
                    email.setError("Este campo es obligatorio");

                }else{

                    if(!emailText.contains("@")){

                        goodEmail = false;
                        email.setError("Email no válido");

                    }

                }

                if(passwordText.isEmpty()){

                    goodPassword = false;
                    password.setError("Este campo es obligatorio");

                }

                if(nicknameText.isEmpty()){

                    goodNickname = false;
                    nickname.setError("Este campo es obligatorio");

                }

                if(!checkBox.isChecked()){

                    checkBox.setError("Debes aceptar las políticas para poder avanzar");

                }

                if(goodEmail && goodPassword && goodNickname && checkBox.isChecked()){

                    //Toast.makeText(context, "Registro completado", Toast.LENGTH_LONG).show();
                    registeUser();

                }

            }
        });

        textViewFromCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(context, PoliticsActivity.class);
//                startActivity(intent);

                Toast.makeText(context, "Esto muestra la pantalla de Políticas", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void registeUser(){

        JSONObject requestBody = new JSONObject();

        try{

            requestBody.put("email", email.getText().toString());
            requestBody.put("password", password.getText().toString());
            requestBody.put("nickname", nickname.getText().toString());

        }catch(JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                urlMockapi + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "Registro realizado con éxito", Toast.LENGTH_LONG).show();
//                      Intent intent = new Intent(context, MainScreen.class);
//                      startActivity(intent);

                    }
                }, new Response.ErrorListener() {
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

        requestQueue.add(request);

    }
}