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

public class Criarconta extends AppCompatActivity {

    private EditText txt_insert_nome;
    private EditText txt_insert_email;
    private EditText txt_insert_telefone;
    private EditText txt_insert_password;
    private EditText txt_insert_confirm_password;
    private Button btn_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criarconta);

        txt_insert_nome = findViewById(R.id.txt_insert_nome);
        txt_insert_email = findViewById(R.id.txt_insert_email);
        txt_insert_telefone = findViewById(R.id.txt_insert_telefone);
        txt_insert_password = findViewById(R.id.txt_insert_password);
        txt_insert_confirm_password = findViewById(R.id.txt_insert_confirm_password);
        btn_create_account = findViewById(R.id.btn_create_account);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCompleto = txt_insert_nome.getText().toString();
                String email = txt_insert_email.getText().toString().trim();
                String telefone = txt_insert_telefone.getText().toString().trim();
                String password = txt_insert_password.getText().toString().trim();
                String confirmPassword = txt_insert_confirm_password.getText().toString().trim();
                View errorCadastroView = null;
                boolean containError = false;

                if (nomeCompleto.isEmpty()) {
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

                if (telefone.isEmpty()) {
                    txt_insert_telefone.setError("Preencha o telefone");
                    if (errorCadastroView == null) {
                        errorCadastroView = txt_insert_telefone;
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
                    checkEmailandRegister (nomeCompleto,email, telefone, password);
//                    SharedPreferences preferences = getSharedPreferences("restaurantecomeupagou.preferences", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putBoolean("estaLogado", true);
//                    editor.apply();
//
//                    Toast.makeText(Criarconta.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Criarconta.this, Loginacessar.class);
//                    startActivity(intent);
//                    finish();
                }

            }
        });

    }

    private void checkEmailandRegister(final String nomeCompleto, final String email, final String telefone, final String senha) {
        Toast.makeText(this, "Verificando e-mail...", Toast.LENGTH_SHORT).show();

        UsuarioApiClient.getInstance(this).verificarEmailUnico(email, new UsuarioApiClient.EmailCheckCallback() {
            @Override
            public void onSuccess(boolean isUnique) {
                if (isUnique) {
                    cadastrarUsuario(new Usuario(nomeCompleto, email, telefone, senha));
                } else {
                    txt_insert_email.setError("Este e-mail já está cadastrado.");
                    txt_insert_email.requestFocus();
                    Toast.makeText(Criarconta.this, "E-mail já cadastrado.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(Criarconta.this, "Erro ao verificar e-mail: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cadastrarUsuario(Usuario usuario) {
        Toast.makeText(this, "Cadastrando usuário...", Toast.LENGTH_SHORT).show();

        UsuarioApiClient.getInstance(this).cadastrarUsuario(usuario, new UsuarioApiClient.RegisterUserCallback() {
            @Override
            public void onSuccess(Usuario usuarioCadastrado) {
                Toast.makeText(Criarconta.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Criarconta.this, Loginacessar.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(Criarconta.this, "Falha no cadastro: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}