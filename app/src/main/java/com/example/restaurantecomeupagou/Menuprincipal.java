package com.example.restaurantecomeupagou;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.adapter.ProductAdapter;
import com.example.restaurantecomeupagou.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class Menuprincipal extends AppCompatActivity {

    private RecyclerView listaProdutoDestaque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}