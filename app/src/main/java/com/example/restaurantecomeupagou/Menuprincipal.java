package com.example.restaurantecomeupagou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.adapter.ProductAdapter;
import com.example.restaurantecomeupagou.data.remote.ProdutoApiClient;
import com.example.restaurantecomeupagou.model.Produto;
import com.example.restaurantecomeupagou.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Menuprincipal extends AppCompatActivity {

    private RecyclerView listaProdutoDestaque;
    SharedPreferences preferences;
    LinearLayout linearButtonLanches;
    LinearLayout linearButtonPorcoes;
    LinearLayout linearButtonBebidas;

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
        listaProdutoDestaque.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        List<Produto> produtos = new ArrayList<>();
//        produtos.add(new Produto("Bife a cavalo", "Descricao do produto 1", 45.00, R.drawable.bac));
//        produtos.add(new Produto("Bife a parmegiana", "Descricao do produto 2", 35.00, R.drawable.bife));
//        produtos.add(new Produto("File de Frango", "Descricao do produto 3", 28.00, R.drawable.fg));

        obterProdutosDestaques();

        linearButtonLanches = findViewById(R.id.category_lanches);
        linearButtonLanches.setOnClickListener(v -> irCatalog("Lanches"));

        linearButtonPorcoes = findViewById(R.id.category_porcoes);
        linearButtonPorcoes.setOnClickListener(v -> irCatalog("Porções"));

        linearButtonBebidas = findViewById(R.id.category_bebidas);
        linearButtonBebidas.setOnClickListener(v -> irCatalog("Bebidas"));
//        ProductAdapter adapter = new ProductAdapter(produtos);
//        listaProdutoDestaque.setAdapter(adapter);

    }

    private void obterProdutosDestaques() {
        ProdutoApiClient.getInstance(this).obterProdutos(new ProdutoApiClient.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                ProductAdapter adapter = new ProductAdapter(produtos, Menuprincipal.this);
                listaProdutoDestaque.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(Menuprincipal.this, "Erro ao listar produtos em destaque: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void irCatalog(String categoria) {
        Intent intent = new Intent(Menuprincipal.this, CatalogActivity.class);
        intent.putExtra(Constants.INTENT_CATEGORIA, categoria);
        startActivity(intent);
    }

//    public void sair (View view) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("estaLogado", false);
//        editor.apply();
//
//        Toast.makeText(Menuprincipal.this, "Logoff com sucesso", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(Menuprincipal.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
}