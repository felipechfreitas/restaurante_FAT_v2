package com.example.restaurantecomeupagou;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView listaProdutoCatalogo;
    private List<Produto> produtoList;
    private EditText editTextBusca;
    ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        editTextBusca = findViewById(R.id.edit_txt_filter_product);
        listaProdutoCatalogo = findViewById(R.id.recycler);
        listaProdutoCatalogo.setLayoutManager(new LinearLayoutManager(this));

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
                    productAdapter = new ProductAdapter(produtoList);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                    return;
                }

                if (palavraBusca.length() >= 3) {
                    List<Produto> produtosFiltrados = filtrarProdutos(palavraBusca);
                    productAdapter = new ProductAdapter(produtosFiltrados);
                    listaProdutoCatalogo.setAdapter(productAdapter);
                }
            }
        });
    }

    private void obterProdutos() {
        ProdutoApiClient.getInstance(this).obterProdutos(new ProdutoApiClient.ProdutoCallback() {
            @Override
            public void onSuccess(List<Produto> produtos) {
                produtoList = produtos;
                productAdapter = new ProductAdapter(produtoList);
                listaProdutoCatalogo.setAdapter(productAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(CatalogActivity.this, "Erro ao listar produtos: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Produto> filtrarProdutos(String palavraBusca) {
        String termoBusca = palavraBusca.toLowerCase();
        return produtoList.stream()
                .filter(produto -> produto.getNome().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());
    }
}