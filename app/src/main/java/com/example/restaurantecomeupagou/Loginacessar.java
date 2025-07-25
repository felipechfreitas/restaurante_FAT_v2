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

import com.example.restaurantecomeupagou.data.remote.UsuarioApiClient;
import com.example.restaurantecomeupagou.model.Usuario;

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
                    autenticarComApi(email, senha);
//                    SharedPreferences preferences = getSharedPreferences("restaurantecomeupagou.preferences", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putBoolean("estaLogado", true);
//                    editor.apply();

//                    Toast.makeText(Loginacessar.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Loginacessar.this, Menuprincipal.class);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
    }

    private void autenticarComApi(String email, String senha) {
        Toast.makeText(Loginacessar.this, "Verificando credenciais...", Toast.LENGTH_SHORT).show();

        UsuarioApiClient.getInstance(this).autenticarUsuario(email, senha, new UsuarioApiClient.LoginCallback() {
            @Override
            public void onSuccess(Usuario usuarioLogado) {
                Toast.makeText(Loginacessar.this, "Login bem-sucedido! Bem-vindo(a), " + usuarioLogado.getNome() + "!", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("restaurantecomeupagou.preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("estaLogado", true);
                editor.putString("emailUsuario", usuarioLogado.getEmail());
                editor.putString("nomeUsuario", usuarioLogado.getNome());
                editor.apply();

                Intent intent = new Intent(Loginacessar.this, Menuprincipal.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(Loginacessar.this, "Erro de comunicação: " + errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCredenciaisInvalidas() {
                Toast.makeText(Loginacessar.this, "E-mail ou senha inválidos.", Toast.LENGTH_LONG).show();
                txt_email.setError("Verifique suas credenciais");
                txt_senha.setError("Verifique suas credenciais");
                txt_email.requestFocus();
            }
        });
    }

    public void irRecuperarConta (View view) {
        Intent intent = new Intent(Loginacessar.this, Recuperarconta.class);
        startActivity(intent);
    }

    public void irCriarConta (View view) {
        Intent intent = new Intent(Loginacessar.this, Criarconta.class);
        startActivity(intent);
    }



}