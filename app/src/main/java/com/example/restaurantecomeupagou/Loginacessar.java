package com.example.restaurantecomeupagou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Loginacessar extends AppCompatActivity {

    private EditText txt_email;
    private EditText txt_senha;
    private Button btn_acessar_tela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginacessar);

        txt_email = findViewById(R.id.txt_email);
        txt_senha = findViewById(R.id.txt_senha);
        btn_acessar_tela = findViewById(R.id.btn_acessar_tela);

        btn_acessar_tela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString().trim();
                String senha = txt_senha.getText().toString().trim();
                View errorFocusView = null;
                boolean withError = false;

                if (email.isEmpty()) {
                    txt_email.setError("Preencha o email");
                    if (errorFocusView == null) {
                        errorFocusView = txt_email;
                    }
                    withError = true;
                } else if (email.length() <5) {
                    txt_email.setError("O email precisa conter no mínimo 5 caracteres");
                    if (errorFocusView == null) {
                        errorFocusView = txt_email;
                    }
                    withError = true;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txt_email.setError("Informe um email válido");
                    if (errorFocusView == null) {
                        errorFocusView = txt_email;
                    }
                    withError = true;
                }

                if (senha.isEmpty()) {
                    txt_senha.setError("Preencha a senha");
                    if (errorFocusView == null) {
                        errorFocusView = txt_senha;
                    }
                    withError = true;
                }

                if (txt_senha.length() <4) {
                    txt_senha.setError("A senha precisa ter no mínimo 4 digitos");
                    if (errorFocusView == null) {
                        errorFocusView = txt_senha;
                    }
                    withError = true;
                }

                if(withError) {
                    errorFocusView.requestFocus();
                    Toast.makeText(Loginacessar.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Loginacessar.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Loginacessar.this, Menuprincipal.class);
                    startActivity(intent);
                }

            }
        });

    }
}