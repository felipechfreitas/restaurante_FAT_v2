package com.example.restaurantecomeupagou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Criarconta extends AppCompatActivity {

    private EditText txt_insert_nome;
    private EditText txt_insert_email;
    private EditText txt_insert_endereco;
    private EditText txt_insert_cep;
    private EditText txt_insert_password;
    private EditText txt_insert_confirm_password;
    private Button btn_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criarconta);

        txt_insert_nome = findViewById(R.id.txt_insert_nome);
        txt_insert_email = findViewById(R.id.txt_insert_email);
        txt_insert_endereco = findViewById(R.id.txt_insert_endereco);
        txt_insert_cep = findViewById(R.id.txt_insert_cep);
        txt_insert_password = findViewById(R.id.txt_insert_password);
        txt_insert_confirm_password = findViewById(R.id.txt_insert_confirm_password);
        btn_create_account = findViewById(R.id.btn_create_account);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txt_insert_nome.getText().toString();
                String email = txt_insert_email.getText().toString().trim();
                String endereco = txt_insert_endereco.getText().toString();
                String cep = txt_insert_cep.getText().toString();
                String password = txt_insert_password.getText().toString().trim();
                String confirmPassword = txt_insert_confirm_password.getText().toString().trim();
                View errorCadastroView = null;
                boolean containError = false;

                if (nome.isEmpty()) {
                    txt_insert_nome.setError("Preencha o nome");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_nome;
                    }
                    containError = true;
                }

                if (email.isEmpty()) {
                    txt_insert_email.setError("Preencha o email");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_email;
                    }
                    containError = true;
                } else if (email.length() <5) {
                    txt_insert_email.setError("O email precisa conter no mínimo 5 caracteres");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_email;
                    }
                    containError = true;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txt_insert_email.setError("Informe um email válido");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_email;
                    }
                    containError = true;
                }

                if (endereco.isEmpty()) {
                    txt_insert_endereco.setError("Preencha o nome");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_endereco;
                    }
                    containError = true;
                }

                if (cep.isEmpty()) {
                    txt_insert_cep.setError("Preencha o nome");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_cep;
                    }
                    containError = true;
                }

                if (password.isEmpty()) {
                    txt_insert_password.setError("Preencha a senha");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_password;
                    }
                    containError = true;
                }

                if (txt_insert_password.length() <4) {
                    txt_insert_password.setError("A senha precisa ter no mínimo 4 digitos");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_password;
                    }
                    containError = true;
                }

                if (confirmPassword.isEmpty()) {
                    txt_insert_confirm_password.setError("Preencha a senha");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_confirm_password;
                    }
                    containError = true;
                }

                if (txt_insert_confirm_password.length() <4) {
                    txt_insert_confirm_password.setError("A senha precisa ter no mínimo 4 digitos");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_confirm_password;
                    }
                    containError = true;
                }

                if (txt_insert_confirm_password.length() <4) {
                    txt_insert_confirm_password.setError("A senha precisa ter no mínimo 4 digitos");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_confirm_password;
                    }
                    containError = true;
                }

                if (!password.equals(confirmPassword)) {
                    txt_insert_confirm_password.setError("As senhas não coincidem");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_confirm_password;
                    }
                    containError = true;
                }

                if (containError) {
                    errorCadastroView.requestFocus();
                    Toast.makeText(Criarconta.this, "Revise os campos com erro", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("restaurantecomeupagou.preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("estaLogado", true);
                    editor.apply();

                    Toast.makeText(Criarconta.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Criarconta.this, Menuprincipal.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}