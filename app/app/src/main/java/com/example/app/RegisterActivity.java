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

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText nickname;
    private CheckBox checkBox;
    private TextView textViewFromCV;
    private Button button;
    private Context context = this;

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

                    Toast.makeText(context, "Registro completado", Toast.LENGTH_LONG).show();

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
}