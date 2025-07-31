package com.example.restaurantecomeupagou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantecomeupagou.adapter.ProductAdapter;
import com.example.restaurantecomeupagou.data.remote.ProdutoApiClient;
import com.example.restaurantecomeupagou.model.Produto;
import com.example.restaurantecomeupagou.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView listaProdutoCatalogo;
    private List<Produto> produtoList;
    private EditText editTextBusca;
    ProductAdapter productAdapter;
    private LinearLayout linearButtonLanches;
    private LinearLayout linearButtonPorcoes;
    private LinearLayout linearButtonBebidas;
    private LinearLayout linearButtonTodos;
    private TextView textViewTitleListProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        editTextBusca = findViewById(R.id.edit_txt_filter_product);
        listaProdutoCatalogo = findViewById(R.id.recycler);
        listaProdutoCatalogo.setLayoutManager(new LinearLayoutManager(this));
        textViewTitleListProducts = findViewById(R.id.text_view_title_list_products);

//        List<Produto> produtosCatalogo = new ArrayList<>();
//        produtosCatalogo.add(new Produto("Bife a cavalo", "Descricao do produto 1", 45.00, R.drawable.bac));
//        produtosCatalogo.add(new Produto("Bife a parmegiana", "Descricao do produto 2", 35.00, R.drawable.bife));
//        produtosCatalogo.add(new Produto("File de Frango", "Descricao do produto 3", 28.00, R.drawable.fg));
//        produtosCatalogo.add(new Produto("Bolinho de Queijo", "Descricao do produto 4", 18.00, R.drawable.bdq));
//        produtosCatalogo.add(new Produto("Risoto", "Descricao do produto 5", 30.00, R.drawable.risoto));
//        produtosCatalogo.add(new Produto("File de Peixe", "Descricao do produto 6", 23.00, R.drawable.fg));
//        produtosCatalogo.add(new Produto("Sopa de mandioquinha", "Descricao do produto 7", 20.00, R.drawable.fg));
//        produtosCatalogo.add(new Produto("Pink Limonade", "Descricao do produto 8", 20.00, R.drawable.fg));
//        produtosCatalogo.add(new Produto("Pina Colada", "Descricao do produto 9", 21.00, R.drawable.fg));
//        produtosCatalogo.add(new Produto("Caipirinha", "Descricao do produto 10", 19.00, R.drawable.fg));
//
//        ProductAdapter adapter = new ProductAdapter(produtosCatalogo);
//        listaProdutoCatalogo.setAdapter(adapter);

        obterProdutos();

        editTextBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String palavraBusca = s.toString();

                if (palavraBusca.isBlank()) {
                    productAdapter = new ProductAdapter(produtoList, CatalogActivity.this);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                    return;
                }

                if (palavraBusca.length() >= 3) {
                    List<Produto> produtosFiltrados = filtrarProdutosNome(palavraBusca);
                    productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                }
            }
        });

        linearButtonLanches = findViewById(R.id.category_lanches);
        linearButtonLanches.setOnClickListener(v -> {
            List<Produto> produtosFiltrados = filtrarProdutosCategoria("L");
            productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
            listaProdutoCatalogo.setAdapter(productAdapter);
            textViewTitleListProducts.setText("Lanches");
        });

        linearButtonPorcoes = findViewById(R.id.category_porcoes);
        linearButtonPorcoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Produto> produtosFiltrados = filtrarProdutosCategoria("P");
                productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
                listaProdutoCatalogo.setAdapter(productAdapter);
                textViewTitleListProducts.setText("Porções");
            }
        });

        linearButtonBebidas = findViewById(R.id.category_bebidas);
        linearButtonBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Produto> produtosFiltrados = filtrarProdutosCategoria("B");
                productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
                listaProdutoCatalogo.setAdapter(productAdapter);
                textViewTitleListProducts.setText("Bebidas");
            }
        });

        linearButtonTodos = findViewById(R.id.category_all);
        linearButtonTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAdapter = new ProductAdapter(produtoList, CatalogActivity.this);
                listaProdutoCatalogo.setAdapter(productAdapter);
                textViewTitleListProducts.setText("Todos os Produtos");
            }
        });
    }

    private void obterProdutos() {
        ProdutoApiClient.getInstance(this).obterProdutos(new ProdutoApiClient.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                produtoList = produtos;
                List<Produto> produtosFiltrados = null;

                Intent intent = getIntent();
                if (intent != null) {
                    String categoria = intent.getStringExtra(Constants.INTENT_CATEGORIA);
                    if (categoria != null) {
                        if (categoria.equals("L")) {
                            textViewTitleListProducts.setText("Lanches");
                        } else if (categoria.equals("P")) {
                            textViewTitleListProducts.setText("Porções");
                        } else if (categoria.equals("B")) {
                            textViewTitleListProducts.setText("Bebidas");
                        }
                        produtosFiltrados = filtrarProdutosCategoria(categoria);
                    }
                }

                if (produtosFiltrados != null) {
                    productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
                } else {
                    productAdapter = new ProductAdapter(produtos, CatalogActivity.this);
                }

                listaProdutoCatalogo.setAdapter(productAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(CatalogActivity.this, "Erro ao listar produtos: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Produto> filtrarProdutosNome(String palavraBusca) {
        String termoBusca = palavraBusca.toLowerCase();
        return produtoList.stream()
                .filter(produto -> produto.getNome().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());
    }

    private List<Produto> filtrarProdutosCategoria(String categoria) {
        return produtoList.stream()
                .filter(produto -> produto.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }
}