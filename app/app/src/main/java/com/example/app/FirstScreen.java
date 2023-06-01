package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app.R;

public class FirstScreen extends AppCompatActivity {

    private Button login;
    private Button register;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        login = findViewById(R.id.login); // Instanciamos los botones para poder trabajar sobre ellos
        register = findViewById(R.id.register);

        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String token = preferences.getString("VALID_TOKEN", "");

        if(!token.isEmpty()){

            Intent intent = new Intent(context, MainScreen.class);
            startActivity(intent);

        }

        login.setOnClickListener(new View.OnClickListener() {  // onClickListener para poder viajar entre pantallas
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, LoginScreen.class); // Inicia la pantalla de inicio de sesión
                startActivity(intent);
                //Toast.makeText(context, "Esto inicia la pantalla de login", Toast.LENGTH_LONG).show();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RegisterScreen.class);
                startActivity(intent);
                //Toast.makeText(context, "Esto inicia la pantalla de registro", Toast.LENGTH_LONG).show();

            }
        });
    }
}