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
        setupSearchListener();
        setupCategoryListeners();
    }

    private void setupSearchListener() {
        editTextBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String palavraBusca = s.toString().trim();

                if (palavraBusca.isEmpty()) {
                    productAdapter = new ProductAdapter(produtoList, CatalogActivity.this);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                    textViewTitleListProducts.setText("Todos os Produtos");

                } else if (palavraBusca.length() >= 3) {
                    List<Produto> produtosFiltrados = filtrarProdutosNome(palavraBusca);
                    productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                    textViewTitleListProducts.setText("Resultados da Busca");
                }
            }
        });
    }

    private void setupCategoryListeners() {

        linearButtonLanches = findViewById(R.id.category_lanches);
        linearButtonLanches.setOnClickListener(v -> {
            filtrarEExibirProdutos("Lanches", "L");
        });

        linearButtonPorcoes = findViewById(R.id.category_porcoes);
        linearButtonPorcoes.setOnClickListener(v -> {
            filtrarEExibirProdutos("Porções", "P");
        });

        linearButtonBebidas = findViewById(R.id.category_bebidas);
        linearButtonBebidas.setOnClickListener(v -> {
            filtrarEExibirProdutos("Bebidas", "B");
        });

        linearButtonTodos = findViewById(R.id.category_all);
        linearButtonTodos.setOnClickListener(v -> {
            productAdapter = new ProductAdapter(produtoList, CatalogActivity.this);
            listaProdutoCatalogo.setAdapter(productAdapter);
            textViewTitleListProducts.setText("Todos os Produtos");
        });
    }

    private void filtrarEExibirProdutos(String titulo, String categoriaCodigo) {
        List<Produto> produtosFiltrados = filtrarProdutosCategoria(categoriaCodigo);
        productAdapter = new ProductAdapter(produtosFiltrados, CatalogActivity.this);
        listaProdutoCatalogo.setAdapter(productAdapter);
        textViewTitleListProducts.setText(titulo);
    }

    private void obterProdutos() {
        ProdutoApiClient.getInstance(this).obterProdutos(new ProdutoApiClient.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                produtoList = produtos;
                List<Produto> produtosParaExibir = produtos;

                Intent intent = getIntent();
                if (intent != null) {
                    String categoriaPassada = intent.getStringExtra(Constants.INTENT_CATEGORIA);
                    if (categoriaPassada != null && !categoriaPassada.isEmpty()) {
                        String categoriaCodigo = "";
                        String tituloDisplay = "";

                        switch (categoriaPassada) {
                            case "Lanches":
                                categoriaCodigo = "L";
                                tituloDisplay = "Lanches";
                                break;
                            case "Porções":
                                categoriaCodigo = "P";
                                tituloDisplay = "Porções";
                                break;
                            case "Bebidas":
                                categoriaCodigo = "B";
                                tituloDisplay = "Bebidas";
                                break;
                            case "Todos":
                            default:
                                categoriaCodigo = "";
                                tituloDisplay = "Todos os Produtos";
                                break;
                        }


                        if (!categoriaCodigo.isEmpty()) {
                            produtosParaExibir = filtrarProdutosCategoria(categoriaCodigo);
                        }
                        textViewTitleListProducts.setText(tituloDisplay);
                    } else {
                        textViewTitleListProducts.setText("Todos os Produtos");
                    }
                } else {
                    textViewTitleListProducts.setText("Todos os Produtos");
                }

                productAdapter = new ProductAdapter(produtosParaExibir, CatalogActivity.this);
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
                .filter(produto -> produto.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
}