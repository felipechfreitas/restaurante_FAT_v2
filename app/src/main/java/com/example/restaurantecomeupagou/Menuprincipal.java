package com.example.restaurantecomeupagou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.adapter.ProductAdapter;
import com.example.restaurantecomeupagou.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class Menuprincipal extends AppCompatActivity {

    private RecyclerView listaProdutoDestaque;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("restaurantecomeupagou.preferences", MODE_PRIVATE);
        boolean estaLogado = preferences.getBoolean("estaLogado", false);

        if (!estaLogado) {
            Intent intent = new Intent(Menuprincipal.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_menuprincipal);

        listaProdutoDestaque = findViewById(R.id.recycler);
        listaProdutoDestaque.setLayoutManager(new LinearLayoutManager(this));

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Bife a cavalo", "Descricao do produto 1", 45.00, R.drawable.bac));
        produtos.add(new Produto("Bife a parmegiana", "Descricao do produto 2", 35.00, R.drawable.bife));
        produtos.add(new Produto("File de Frango", "Descricao do produto 3", 28.00, R.drawable.fg));

        ProductAdapter adapter = new ProductAdapter(produtos);
        listaProdutoDestaque.setAdapter(adapter);

    }

    public void sair (View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("estaLogado", false);
        editor.apply();

        Toast.makeText(Menuprincipal.this, "Logoff com sucesso", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Menuprincipal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}