package com.example.restaurantecomeupagou;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.adapter.ProductAdapter;
import com.example.restaurantecomeupagou.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView listaProdutoCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        listaProdutoCatalogo = findViewById(R.id.recycler);
        listaProdutoCatalogo.setLayoutManager(new LinearLayoutManager(this));

        List<Produto> produtosCatalogo = new ArrayList<>();
        produtosCatalogo.add(new Produto("Bife a cavalo", "Descricao do produto 1", 45.00, R.drawable.bac));
        produtosCatalogo.add(new Produto("Bife a parmegiana", "Descricao do produto 2", 35.00, R.drawable.bife));
        produtosCatalogo.add(new Produto("File de Frango", "Descricao do produto 3", 28.00, R.drawable.fg));
        produtosCatalogo.add(new Produto("Bolinho de Queijo", "Descricao do produto 4", 18.00, R.drawable.bdq));
        produtosCatalogo.add(new Produto("Risoto", "Descricao do produto 5", 30.00, R.drawable.risoto));
        produtosCatalogo.add(new Produto("File de Peixe", "Descricao do produto 6", 23.00, R.drawable.fg));
        produtosCatalogo.add(new Produto("Sopa de mandioquinha", "Descricao do produto 7", 20.00, R.drawable.fg));
        produtosCatalogo.add(new Produto("Pink Limonade", "Descricao do produto 8", 20.00, R.drawable.fg));
        produtosCatalogo.add(new Produto("Pina Colada", "Descricao do produto 9", 21.00, R.drawable.fg));
        produtosCatalogo.add(new Produto("Caipirinha", "Descricao do produto 10", 19.00, R.drawable.fg));

        ProductAdapter adapter = new ProductAdapter(produtosCatalogo);
        listaProdutoCatalogo.setAdapter(adapter);

    }
}