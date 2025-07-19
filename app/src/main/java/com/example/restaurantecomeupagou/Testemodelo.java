package com.example.restaurantecomeupagou;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurantecomeupagou.model.Pessoa;

public class Testemodelo extends AppCompatActivity {

    EditText etNomePessoa;
    EditText etIdadePessoa;
    EditText etEmailPessoa;
    EditText etTelefonePessoa;
    Button btnCadastrarPessoa;
    TextView tvNomePessoa;
    TextView tvIdadePessoa;
    TextView tvEmailPessoa;
    TextView tvTelefonePessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testemodelo);

        etNomePessoa = findViewById(R.id.etNomePessoa);
        etIdadePessoa = findViewById(R.id.etIdadePessoa);
        etEmailPessoa = findViewById(R.id.etEmailPessoa);
        etTelefonePessoa = findViewById(R.id.etTelefonePessoa);

        btnCadastrarPessoa = findViewById(R.id.btnCadastrarPessoa);

        tvNomePessoa = findViewById(R.id.tvNomePessoa);
        tvIdadePessoa = findViewById(R.id.tvIdadePessoa);
        tvEmailPessoa = findViewById(R.id.tvEmailPessoa);
        tvTelefonePessoa = findViewById(R.id.tvTelefonePessoa);

    }

    public void cadastrar(View view) {
        Pessoa pessoa = new Pessoa (
                etNomePessoa.getText().toString(),
                Integer.parseInt(etIdadePessoa.getText().toString()),
                etEmailPessoa.getText().toString(),
                etTelefonePessoa.getText().toString()
        );
        tvNomePessoa.setText(pessoa.getNome());
        tvIdadePessoa.setText(String.valueOf(pessoa.getIdade()));
        tvEmailPessoa.setText(pessoa.getEmail());
        tvTelefonePessoa.setText(pessoa.getTelefone());

    }
}